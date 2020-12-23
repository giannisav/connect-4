
<html>
<head>
    <title>Join Game</title>
    <link rel="stylesheet" href="./css/form.css"/>
    <style>

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        h1 {
            width: 400px;
            background: -webkit-linear-gradient(35deg, #dec71b, #b5c91a, #8c9e10);
            margin-top: 50px;
            text-align: center;
            font-family: Lucida Console, Courier New, monospace;
        }

    </style>
</head>
<body>
<#include "parts/navbar.ftl">
<div class="container">
    <h1>Join Game</h1>
    <form action="/join" method="post">
        <input type="text" name ="nickname" placeholder="nickname" />
        <input type="text" name ="id" placeholder="game_id" />
        <input type="submit" value="Join" />
    </form>
</div>
</body>
<#include "parts/footer.ftl">
</html>