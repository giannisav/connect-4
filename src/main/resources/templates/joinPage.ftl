<html>
<head>
    <title>Join Game</title>
    <link rel="stylesheet" href="./css/joinPage.css"/>

</head>
<body>
<#include "./parts/navbar.ftl">
<div class="container-list">
    <h1>List of available games</h1>
    <#if games??>
    <div>
        <table>
            <tr>
                <th>Host Player</th>
                <th>Connect</th>
            </tr>
            <#list games as game>
            <tr>
                <td>${game.yellowPlayerNickname}</td>
                <td>
                    <form action="/join" method="post">
                        <input type="text" name ="nickname" placeholder="nickname" />
                        <input type="hidden" name ="id" value=${game.id} />
                        <input type="submit" value="Join" />
                    </form>
                </td>
            </tr>
            </#list>
        </table>
    </div>
    <#else>
    </#if>
    <form action="/games" method="get">
        <input class="find" type="submit" value="Find available games" />
    </form>
</div>
</body>
<#include "parts/footer.ftl">
</html>