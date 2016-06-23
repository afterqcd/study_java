<#assign base=request.contextPath />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="${base}/resources/css/bootstrap.min.css">
</head>
<body>
<h1>User List</h1>
<table class="table table-hover">
    <tr>
        <th>Name</th>
        <th>Age</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.name}</td>
            <td>${user.age}</td>
        </tr>
    </#list>
</table>
</body>
</html>