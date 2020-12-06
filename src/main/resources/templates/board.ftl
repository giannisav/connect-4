
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
            width: 400px;
            margin: 50px auto;
        }

        input[type="text"] {
            display: block;
            width: 400px;
            margin: 0 0 20px;
            padding: 8px 12px 10px 12px;
            border: 1px solid rgba(0,0,0,.5);
            background: rgba(0,0,0,.25);
        }

        input[type="submit"] {
            display: block;
            width: 150px;
            margin: 0 0 20px;
            padding: 8px 0 10px 0;
            text-align: center;
            border: 1px solid rgba(0,0,0,.5);
            background: rgba(0,0,0,.25);
        }

        .cheat{
            visibility: visible;
        }

        .cheat:hover {
            visibility: visible;
        }

        .game {
            display: flex;
            align-items: flex-start;
            width: 100vw;
            justify-content: space-between;
        }

        #board {
            position: relative;
            width: 800px;
            height: 800px;
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
            box-shadow: 5px 5px 5px rgba(105, 105, 105, 0.3);
            width: 90px;
            height: 90px;
            border-radius: 100%;
        }

        .yellow {
            background: repeating-radial-gradient(#d7fc03, #acc902 58%);
        }

        .red {
            background: repeating-radial-gradient(#fc036b, #b00049 58%);
        }

        #gameEndBoard {
            position: relative;
            width: 800px;
            height: 800px;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            animation: animateBg 2s infinite linear;
            background-size: 300% 100%;
            background: linear-gradient(0.25turn, #da3287, #ffde5e, #da3287);
        }

        .table {
            font-family: arial, sans-serif;
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

        .tooltip:hover {
            content: "Somewhere here there is one hidden cheat button that plays for you the best move. If you find it, dont spam clicks, it needs some time to think it";
            display: block;
            position: relative;
            top: -16px;
            right: -16px;
            width: 100px;
        }
    </style>
    <title>Connect 4</title>
</head>
<body>
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
        <#return "gameEndBoard"/>
    <#else>
        <#return "board"/>
    </#if>
</#function>
<#include "./parts/navbar.ftl">
<table class="table">
    <tr>
        <th>Yellow Player</th>
        <th>Red Player</th>
        <th>Game State</th>
        <th>Next turn</th>
    </tr>
    <tr>
        <td>${game.yellowPlayerNickname}</td>
        <td>${game.redPlayerNickname}</td>
        <td>${game.gameState}</td>
        <td>${game.nextMoveNickname}</td>
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
    <form action="/play" method="post">
        <input type="hidden" name="nickname" value=${nickname}>
        <input type="hidden" name="id" value=${game.id}>
        <input type="text" name="column" placeholder="choose column">
        <input type="submit" value="Play" />
    </form>
    <form action="/board/${nickname}/${game.id}" method="get">
        <input type="submit" value="Get opponent's move">
    </form>
    <form action="/cheat" method="post">
        <input type="hidden" name="nickname" value=${nickname}>
        <input type="hidden" name="id" value=${game.id}>
        <input type="submit" class="cheat" value="Cheat">
    </form>
</section>
</body>
<#include "./parts/footer.ftl">
</html>