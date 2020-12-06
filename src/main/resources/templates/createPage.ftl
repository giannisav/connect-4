
<html>
<head>
    <title>Create Game</title>
    <link rel="stylesheet" href="./css/form.css"/>
</head>

<body>
<#include "parts/navbar.ftl">
<form action="/create" method="post">
    <input type="text" name="nickname" placeholder="nickname" />
    <input type="submit" value="Create" />
</form>
</body>
<#include "parts/footer.ftl">
</html>