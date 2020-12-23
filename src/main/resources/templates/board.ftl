
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Board</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            font: 14px/1 'Roboto', sans-serif;
            color: #fff;
            background: #333;
        }

        button,
        input,
        select,
        textarea {
            font-family: inherit;
            font-size: 100%;
            vertical-align: baseline;
            border: 0;
            outline: 0;
            color: #b5c91a;
        }

        textarea {
            overflow: auto;
            vertical-align: top;
            resize: none;
        }

        form {
            width: 150px;
            margin: 50px auto;
        }

        input[type="text"] {
            display: block;
            width: 170px;
            margin: 0 0 20px;
            text-align: center;
            padding: 8px 12px 10px 12px;
            border: 1px solid rgba(0,0,0,.5);
            background: rgba(0,0,0,.25);
        }

        input[type="submit"] {
            display: block;
            width: 170px;
            margin: 0 0 20px;
            padding: 8px 0 10px 0;
            text-align: center;
            border: 1px solid rgba(0,0,0,.5);
            background: rgba(0,0,0,.25);
        }

        .cheat{
            width: 45px;
            color: transparent;
            background-color: transparent !important;
            border: 1px solid transparent !important;
        }

        .cheat:hover {
            width: 45px;
            color: #b5c91a;
        }

        .game {
            position: relative;
            margin-top: 10px;
            margin-bottom: 5px;
            display: flex;
            align-items: flex-start;
            width: 100vw;
            justify-content: center;
        }

        .forms {
            margin-top: 50px;
            margin-left: 20px;
            display: flex;
            justify-content: center;
            flex-direction: column;
            font-family: Lucida Console, Courier New, monospace;
        }

        #board {
            position: relative;
            width: 70vh;
            height: 70vh;
            border-radius: 10px;
            background: linear-gradient(0.25turn, #050505, #171717, #000000);
            display: flex;
            flex-direction: column;
        }

        .row {
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            align-items: center;
            width: 100%;
            height: 100%;
        }

        .circle {
            background-color: #333;
            box-shadow: 3px 3px 3px rgba(105, 105, 105, 0.3);
            width: 8vh;
            height: 8vh;
            border-radius: 100%;
        }

        .yellow {
            background: repeating-radial-gradient(#ffda2d, #cca800 58%);
        }

        .red {
            background: repeating-radial-gradient(#d9344a, #b00049 58%);
        }

        #board-end {
            position: relative;
            width: 70vh;
            height: 70vh;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            background: linear-gradient(55deg, #0d0d0d, ${(game.nextMoveNickname?ends_with(game.yellowPlayerNickname)) ?string('#e3c229', '#b52a3c')});
            background-size: 600% 600%;

            -webkit-animation: end 1s ease infinite;
            -moz-animation: end 10s ease infinite;
            animation: end 10s ease infinite;
        }

        @-webkit-keyframes end {
            0%{background-position:0% 97%}
            50%{background-position:100% 4%}
            100%{background-position:0% 97%}
        }
        @-moz-keyframes end {
            0%{background-position:0% 97%}
            50%{background-position:100% 4%}
            100%{background-position:0% 97%}
        }
        @keyframes end {
            0%{background-position:0% 97%}
            50%{background-position:100% 4%}
            100%{background-position:0% 97%}
        }

        .table {
            margin-top: 15px;
            font-family: Lucida Console, Courier New, monospace;
            border-collapse: collapse;
            table-layout: fixed;
            width: 100%;
            padding-top: 20%;
        }

        td, th {
            border: 1px solid #8c9e10;
            color: #8c9e10;
            text-align: center;
            padding: 8px;
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

<table class="table">
    <tr>
        <th>Game Id</th>
        <th style="color:#ffda2d">Yellow Player</th>
        <th>Next turn</th>
        <th style="color:#d9344a">Red Player</th>
        <th>Game State</th>
    </tr>
    <tr>
        <td>${game.id}</td>
        <td style="color:#ffda2d">${game.yellowPlayerNickname}</td>
        <td>${game.nextMoveNickname}</td>
        <td style="color:#d9344a">${game.redPlayerNickname}</td>
        <td>${game.gameState}</td>
    </tr>
</table>
<section class="game">
    <div id="${state(game.gameState)}">
        <#list game.board as row>
        <div class="row">
            <div class="${board(row[0])}"></div>
            <div class="${board(row[1])}"></div>
            <div class="${board(row[2])}"></div>
            <div class="${board(row[3])}"></div>
            <div class="${board(row[4])}"></div>
            <div class="${board(row[5])}"></div>
            <div class="${board(row[6])}"></div>
        </div>
        </#list>
    </div>
    <div class="forms">
        <form action="/play" method="post">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="token" value=${(nickname == game.yellowPlayerNickname) ? then (game.yellowUuid, game.redUuid)}>
            <input type="hidden" name="id" value=${game.id}>
            <input type="text" name="column" placeholder="choose column">
            <input type="submit" value="Play" />
        </form>
        <form action="/board" method="get">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="id" value=${game.id}>
            <input type="submit" value="See opponent's move">
        </form>
        <form action="/cheat" method="post">
            <input type="hidden" name="nickname" value=${nickname}>
            <input type="hidden" name="token" value=${(nickname == game.yellowPlayerNickname) ? string(game.yellowUuid, game.redUuid)}>
            <input type="hidden" name="id" value=${game.id}>
            <input type="submit" class="cheat" value="Cheat">
        </form>
    </div>
    <#include "./parts/detectiveInfo.ftl">
</section>
</body>
<#include "./parts/footer.ftl">
</html>