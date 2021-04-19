<!DOCTYPE html>
<html lang="en">

<head>
    <title>Statistics</title>
    <link rel="stylesheet" href="./css/statistics.css"/>
</head>

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
                            <input type="hidden" name="sortingType" value="gamesPlayed"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="gamesPlayed"/>
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
                    <th>Wins Vs AI
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="winsVsAi"/>
                            <input type="hidden" name="sortingOrder" value="asc"/>
                            <input class="floated" type="submit" value="&#x25B2;"/>
                        </form>
                        <form method="GET" action="/statistics">
                            <input type="hidden" name="sortingType" value="winsVsAi"/>
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
                        <td>${player.winsVsAi}</td>
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