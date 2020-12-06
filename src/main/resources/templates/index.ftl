<!DOCTYPE html>
<html lang="en">
<head>
    <title>Connect 4</title>
</head>
<style>

    .welcome {
        align-content: center;
        margin-top: 100px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 100px;
        width: 35%;
        padding: 10px;
        background-color: #333;
        transform: skewY(-15deg);
    }

    h1{
        font-size: 100px;
        background: -webkit-linear-gradient(35deg, #dec71b, #b5c91a, #8c9e10);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

</style>
<body>
<#include "parts/navbar.ftl">
<div class="welcome">
    <h1>Lets play Connect 4</h1>
</div>
<#include "./parts/detectiveInfo.ftl">
</body>
<#include "parts/footer.ftl">
</html>