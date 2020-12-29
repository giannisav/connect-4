<html>
<head>
    <title>Join Game</title>
    <style>
        button,
        input,
        textarea {
            font-family: inherit;
            font-size: 100%;
            vertical-align: baseline;
            text-align: center;
            border: 0;
            outline: 0;
            color: #b5c91a;
        }

        form {
            font: 14px/1 'Roboto', sans-serif;
            display: flex;
            flex-direction: column;
            margin-top: 2px;
            margin-bottom: 2px;
            justify-content: center;
            align-items: center;
        }

        input[type="text"] {
            border: 1px solid rgba(0,0,0,.5);
            width: available;
            background: rgba(0,0,0,.25);
        }

        input[type="submit"] {
            margin-top: 5px;
            border: 1px solid rgba(0,0,0,.5);
            width: available;
            background: rgba(0,0,0,.25);
        }

        .find {
            margin-top: 60px;
            border: 1px solid rgba(0,0,0,.5);
            width: 170px !important;
            height: 40px;
            text-align: center !important;
            background: rgba(0,0,0,.25);
        }

        .container-list {
            margin: 3% auto;
            border: 3px solid #8c9e10;
            background-color: #333;
            width: 50%;
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
            text-align: center;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #3b3b3b;
        }

    </style>
</head>
<body>
<#include "parts/navbar.ftl">
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