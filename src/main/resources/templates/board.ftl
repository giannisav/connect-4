<!DOCTYPE html>
<html lang="en">
<head>
    <title>Board</title>
    <link rel="stylesheet" href="./css/board.css"/>
    <style>
        #board-end {
            <#function winner char>
                <#if char?ends_with(game.yellowPlayerNickname)>
                    <#return '#e3c229'/>
                <#elseif char?ends_with(game.redPlayerNickname)>
                    <#return '#b52a3c'/>
                <#else>
                    <#return '#333333'/>
                </#if>
            </#function>

            position: relative;
            width: 70vh;
            height: 70vh;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            background: linear-gradient(55deg, #0d0d0d, ${winner(game.nextMoveNickname)});
            background-size: 600% 600%;

            -webkit-animation: end 1s ease infinite;
            -moz-animation: end 10s ease infinite;
            animation: end 10s ease infinite;
        }
    </style>
    <title>Connect 4</title>
</head>
<body>

<#include "parts/navbar.ftl">

<#function board char>
        <#if char == "R">
            <#return "circle red"/>
        <#elseif char == "Y">
            <#return "circle yellow"/>
        <#else>
            <#return "circle"/>
        </#if>
</#function>
<#function state state>
    <#if state == "FINISHED">
        <#return "board-end"/>
    <#else>
        <#return "board"/>
    </#if>
</#function>
<#function moves board>
    <#local numOfMoves = 0>
    <#list board as row>
        <#list row as col>
            <#if (col == 'Y' || col == 'R')>
                <#local numOfMoves += 1>
            </#if>
        </#list>
    </#list>
    <#return numOfMoves/>

</#function>

<table class="table">
    <tr>
        <th style="color:#ffda2d">Yellow Player</th>
        <th>Next turn</th>
        <th>Game State</th>
        <th style="color:#d9344a">Red Player</th>
    </tr>
    <tr>
        <td style="color:#ffda2d">${game.yellowPlayerNickname}</td>
        <td id="nextMove">${game.nextMoveNickname}</td>
        <td>${game.gameState?lower_case}</td>
        <td style="color:#d9344a">${game.redPlayerNickname}</td>
    </tr>
</table>
<section class="game">
    <div id="${state(game.gameState)}">
        <#list game.board as row>
        <div class="row">
            <div class="${board(row[0])} 1"></div>
            <div class="${board(row[1])} 2"></div>
            <div class="${board(row[2])} 3"></div>
            <div class="${board(row[3])} 4"></div>
            <div class="${board(row[4])} 5"></div>
            <div class="${board(row[5])} 6"></div>
            <div class="${board(row[6])} 7"></div>
        </div>
        </#list>
    </div>
    <div id="numOfMoves" style="display: none">${moves(game.board)}</div>
    <div id="nick" style="display: none">${nickname}</div>
    <div class="forms">
        <form id="play" action="/play" method="post">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="uuid" value=${game.uuid}>
            <input type="hidden" name="id" value=${game.id}>
            <input  id="column" type="hidden" name="column">
        </form>
        <form id="playVsAi" action="/playAI" method="post">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="uuid" value=${game.uuid}>
            <input type="hidden" name="id" value=${game.id}>
            <input  id="column2" type="hidden" name="column">
        </form>
        <form id ="getStateForm" action="/board" method="get">
            <input type="hidden" name="nickname" value=${nickname}>
            <input id="uuid" type="hidden" name="uuid" value=${game.uuid}>
            <input id="gameId" type="hidden" name="id" value=${game.id}>
        </form>
        <form id="cheat" style="margin-top: 20vh" method="post" onsubmit="set_action(this, '/cheatAI', '/cheat')">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="uuid" value=${game.uuid}>
            <input type="hidden" name="id" value=${game.id}>
            <input type="submit" class="cheat" value="Cheat">
        </form>
        <div id="isVsAi" style="display: none">${game.isVsAi?string('true', 'false')}</div>
    </div>
    <#include "./parts/detectiveInfo.ftl">
</section>
</body>
<#include "./parts/footer.ftl">
<script>

    let nextMove = document.getElementById("nextMove").innerHTML;
    let nickname = document.getElementById("nick").innerHTML;
    let numOfMoves = document.getElementById("numOfMoves").innerHTML;
    let gameId = document.getElementById("gameId").value;

    document.getElementById("board").addEventListener("click", (e) => {
        if (e.target.classList.contains("circle") && e.target.classList[1].length === 1) {
            document.getElementById("column").value = e.target.classList[1];
            if (document.getElementById("isVsAi").innerHTML === 'true') {
                document.getElementById("column2").value = e.target.classList[1];
                document.getElementById("playVsAi").submit();
            } else {
                document.getElementById("column").value = e.target.classList[1];
                document.getElementById("play").submit();
            }
        }
    });

    function set_action(form, aiAction, playerAction) {
        if (document.getElementById("isVsAi").innerHTML === 'true') {
            form.action = aiAction;
        } else {
            form.action = playerAction;
        }
    }

    function getNextMove(){
        const url = window.location.protocol + "//" + window.location.host + "/api/games/needsUpdate/" + gameId + '/' + numOfMoves ;
        fetch(url, { credentials: 'include'})
            .then(res => res.json())
            .then(data => {
                if (data === true) {
                    setTimeout(function() {
                        document.getElementById("getStateForm").submit();
                    }, 6000);
                }
            });
    }

    async function checkForNextMove() {
        await getNextMove();
        setTimeout(checkForNextMove,5000);
    }


    if (nextMove !== nickname) {
        checkForNextMove();
    }
</script>
</html>