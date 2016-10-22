<%@ page import="com.petko.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ErrorPage</title>
</head>
<body>
Ошибка!<br><br>
<%=request.getAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE)%><br>
</body>
</html>
