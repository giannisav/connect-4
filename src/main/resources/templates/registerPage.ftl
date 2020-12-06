
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="./css/form.css"/>
</head>
<body>
<#include "parts/navbar.ftl">
<form action="/register" method="post">
  <input type="text" name ="nickname" placeholder="nickname" />
    <input type="submit" value="Register" />
</form>
</body>
<#include "parts/footer.ftl">
 </html>