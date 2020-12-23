<!DOCTYPE html>
<html lang="en">
<head>
    <title>Connect 4</title>
</head>
<style>

    .welcome {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 100px auto;
        padding: 10px;
        background-color: #333;
    }

    h1{
        font-size: 70px;
        background: -webkit-linear-gradient(35deg, #dec71b, #b5c91a, #8c9e10);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        transform: skewY(-15deg);
        text-align: center;
        font-family: Lucida Console, Courier New, monospace;
    }

</style>
<body>
<#include "parts/navbar.ftl">
<div class="welcome">
    <h1>Lets play Connect 4</h1>
</div>
</body>
<#include "parts/footer.ftl">
</html>