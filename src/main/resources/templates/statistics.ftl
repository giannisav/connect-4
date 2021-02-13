<!DOCTYPE html>
<html lang="en">

<head>
    <title>Statistics</title>
</head>
<style>
    body {
        background-color: #333;
    }

    .container {
        margin: 3% auto;
        border: 3px solid #8c9e10;
        background-color: #333;
        width: 80%;
        padding: 14px;
        overflow: auto;
    }

    h1 {
        text-align: center;
        color: #8c9e10;
        font-size: 40px;
        font-family: Arial, Helvetica, sans-serif;
    }

    h2 {
        text-align: center;
        color: #8c9e10;
        font-size: 20px;
        font-family: Arial, Helvetica, sans-serif;

    }

    table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        table-layout: fixed;
        width: 100%;
        padding-top: 20%;
    }

    td, th {
        border: 1px solid #8c9e10;
        color: #8c9e10;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #3b3b3b;
    }

    .floated {
        color: #8c9e10;
        float: left;
        margin-right: 1px;
        display: flex;
        border: 1px solid rgba(0,0,0,.5);
        background: rgba(0,0,0,.25);
        padding: 4px 0 5px 0;
    }
    .resultContainer {
        margin: 3% auto;
        border: 3px solid #8c9e10;
        background-color: #333;
        width: 80%;
        padding: 14px;
        overflow: auto;
        align-content: center;
        align-items: center;
        text-align: center;
        display: flex;
    }
    .minContainer {
        margin: auto;
        display: flex;
        justify-content: center;
    }
    .resultNav {
        min-width: 150px;
        padding: 8px 0 10px 0;
        text-align: center;
        border: 1px solid rgba(0,0,0,.5);
        background: rgba(0,0,0,.25);
        font-family: inherit;
        font-size: 100%;
        outline: 0;
        color: #b5c91a;

    }
    .number {
        text-align: center;
        color: #8c9e10;
        font-size: 20px;
        padding-left:5%;
        padding-right:5%;
        font-family: Arial, Helvetica, sans-serif;
        line-height: 35px;
    }
    .inactive {
        min-width: 150px;
        padding: 8px 0 10px 0;
        text-align: center;
        border: 1px solid rgba(0,0,0,.5);
        opacity: 0;
    }

</style>

<body>
<#include "./parts/navbar.ftl">
<div class="container">
    <h1>Statistics</h1>
    <h2>See the rank of the players. Sort the list by the corresponding arrows</h2>
        <div>
            <table>
                <thead>
                <tr>
                    <th>Nickname
                    </th>
                    <th>Points
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="points"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="points"/>
                            <input type="hidden" name="sortingOrder" value="desc"/>
                            <input class="floated" type="submit" value="&#x25BC;"/>
                        </form>
                    </th>
                    <th>Games Played
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="games_played"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="games_played"/>
                            <input type="hidden" name="sortingOrder" value="desc"/>
                            <input class="floated" type="submit" value="&#x25BC;"/>
                        </form>
                    </th>
                    <th>Wins
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="wins"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="wins"/>
                            <input type="hidden" name="sortingOrder" value="desc"/>
                            <input class="floated" type="submit" value="&#x25BC;"/>
                        </form>
                    </th>
                    <th>Loses
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="loses"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="loses"/>
                            <input type="hidden" name="sortingOrder" value="desc"/>
                            <input class="floated" type="submit" value="&#x25BC;"/>
                        </form>
                    </th>
                    <th>Draws
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="draws"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="draws"/>
                            <input type="hidden" name="sortingOrder" value="desc"/>
                            <input class="floated" type="submit" value="&#x25BC;"/>
                        </form>
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list slice.content as player>
                    <tr>
                        <td>${player.nickname}</td>
                        <td>${player.points}</td>
                        <td>${player.gamesPlayed}</td>
                        <td>${player.wins}</td>
                        <td>${player.loses}</td>
                        <td>${player.draws}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="resultContainer">
        <div class="minContainer">
            <#if slice.hasPrevious()>
                <form method="GET" action="/statistics">
                    <input type="hidden" name="sortingType" value="${type}"/>
                    <input type="hidden" name="sortingOrder" value="${order?lower_case}"/>
                    <input type="hidden" name="page" value=${slice.number - 1}>
                    <input class="resultNav" type="submit" value="&#8249;"/>
                </form>
            <#else>
                <button class="inactive"></button>
            </#if>
            <div class="number">
                ${slice.number + 1}
            </div>
            <#if slice.hasNext()>
                <form method="GET" action="/statistics">
                    <input type="hidden" name="sortingType" value="${type}"/>
                    <input type="hidden" name="sortingOrder" value="${order?lower_case}"/>
                    <input type="hidden" name="page" value=${slice.number + 1}>
                    <input class="resultNav" type="submit" value="&#8250;"/>
                </form>
            <#else>
                <button class="inactive"></button>
            </#if>
        </div>
    </div>
</body>
<#include "./parts/footer.ftl">
</html>