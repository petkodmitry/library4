<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<H3>Добро пожаловать, ${user}!</H3><HR>
Вы вошли в систему как АДМИНИСТРАТОР<BR><BR>
<a href="controller?cmd=login">На главную</a><BR>
<a href="controller?cmd=showusers">Список пользователей</a><BR>
<a href="controller?cmd=adduser">Добавить пользователя(не работает)</a><BR>
<a href="controller?cmd=waitingorders">Ожидающие заказы(не работает)</a><BR>
<a href="controller?cmd=expiredorders">Просроченная задолженность(не работает)</a><BR>
<a href="controller?cmd=searchbookadmin">Поиск книги(не работает)</a><BR>
<a href="controller?cmd=blacklist">Черный список(не работает)</a><BR>
<BR><a href="controller?cmd=logout">Выход</a><BR>
<c:if test="${requestScope['errorMessage'] != null}">
    <BR>Ошибка: ${errorMessage}
</c:if>
</body>
</html>
