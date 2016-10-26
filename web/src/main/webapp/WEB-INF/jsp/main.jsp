<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<H3>Добро пожаловать, ${user}!</H3><HR>
Вы вошли в систему как обычный пользователь<BR><BR>
<a href="controller?cmd=login">На главную</a><BR>
<a href="controller?cmd=mybooks">Мои книги(не работает)</a><BR>
<a href="controller?cmd=searchbook">Поиск книги(не работает)</a><BR>
<a href="controller?cmd=myorders">Мои заказы в очереди(не работает)</a><BR>
<BR><a href="controller?cmd=logout">Выход</a><BR>
<c:if test="${requestScope['errorMessage'] != null}">
    <BR>Ошибка: ${errorMessage}
</c:if>
</body>
</html>
