<!DOCTYPE html>
<html lang="en">

<head>
    <title>Error Page</title>
</head>
<style>
    * {
        background-color: #333;
    }
    .alert {
        margin-top: 20%;
        margin-left: auto;
        margin-right: auto;
        width: 40%;
        padding: 20px;
        background-color: #2b2b2b;
    }

    h1,
    h2{
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #2b2b2b;
        color: #d9344a;
    }

    .closebtn {
        background-color: #2b2b2b;
        margin-left: 15px;
        color: #d9344a;
        font-weight: bold;
        float: right;
        font-size: 20px;
        line-height: 20px;
        cursor: pointer;
        transition: 0.3s;
        text-align: center;
    }

    .closebtn:hover {
        color: black;
    }
</style>
<body>
<div class="alert">
    <span class="closebtn" onclick="history.back()">&larr;</span>
    <h1>Error page</h1>
    <h2>${(message?has_content) ? then (message, "Unpredicted error")}</h2>
</div>

</body>
</html>
