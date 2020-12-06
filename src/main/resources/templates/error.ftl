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
        align-content: center;
        margin-top: 20%;
        margin-left: auto;
        margin-right: auto;
        width: 40%;
        padding: 20px;
        background-color: #2b2b2b;
    }

    h1,
    h2{
        background-color: #2b2b2b;
        color: #fc036b;
    }

    .closebtn {
        background-color: #2b2b2b;
        margin-left: 15px;
        color: #fc036b;
        font-weight: bold;
        float: right;
        font-size: 20px;
        line-height: 20px;
        cursor: pointer;
        transition: 0.3s;
    }

    .closebtn:hover {
        color: black;
    }
</style>
<body>
<div class="alert">
    <span class="closebtn" onclick="history.back()">&larr;</span>
    <h1>Error page</h1>
    <h2>Error message: ${message!"Unpredicted error"}</h2>
    <h2>Failed URL: ${url!"Error on retrieving the url"}</h2>
</div>

</body>
</html>
