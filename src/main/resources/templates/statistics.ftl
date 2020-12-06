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
        margin-top: 3%;
        margin-right: auto;
        margin-left: auto;
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

    input[type="hidden"] {
        display: flex;
        width: 400px;
        margin: 0 0 20px;
        padding: 8px 12px 10px 12px;
        border: 1px solid rgba(0,0,0,.5);
        background: rgba(0,0,0,.25);
    }

</style>

<body>
<#include "./parts/navbar.ftl">
<div class="container">
    <h1>Statistics</h1>
    <h2>See the rank of the players. Sort the list by the corresponding arrows</h2>
    <#if players??>
        <div>
            <table>
                <thead>
                    <tr>
                    <th class="th-di" >Nickname
                    </th>
                    <th class="th-di">Points
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
                    <th class="th-di">Games Played
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
                    <th class="th-di">Wins
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
                    <th class="th-di">Loses
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
                    <th class="th-di">Draws
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
                    <#list players as player>
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
    <#else>
        <h2>Failed to retrieve the players</h2>
    </#if>
</div>
</body>
<#include "./parts/footer.ftl">
</html>
