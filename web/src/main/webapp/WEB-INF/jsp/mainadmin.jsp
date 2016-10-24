<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
Вы вошли в систему как АДМИНИСТРАТОР<BR>
${errorMessage}<BR><BR>
<a href="controller?cmd=login">На главную</a><BR>
<a href="controller?cmd=waitingorders">Ожидающие заказы(не работает)</a><BR>
<a href="controller?cmd=expiredorders">Просроченная задолженность(не работает)</a><BR>
<a href="controller?cmd=searchbookadmin">Поиск книги(не работает)</a><BR>
<a href="controller?cmd=blacklist">Черный список(не работает)</a><BR>
<BR><a href="controller?cmd=logout">Выход</a><BR>
</body>
</html>
