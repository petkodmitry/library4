<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ErrorPage</title>
</head>
<body>
Ошибка!<br><br>
<%=request.getAttribute("errorMessage")%><br>
</body>
</html>
