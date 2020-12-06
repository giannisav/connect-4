
<html>
<head>
    <title>Join Game</title>
    <link rel="stylesheet" href="./css/form.css"/>
</head>
<body>
<#include "parts/navbar.ftl">
<form action="/join" method="post">
    <input type="text" name ="nickname" placeholder="nickname" />
    <input type="text" name ="id" placeholder="game_id" />
    <input type="submit" value="Join" />
</form>
</body>
<#include "parts/footer.ftl">
</html>