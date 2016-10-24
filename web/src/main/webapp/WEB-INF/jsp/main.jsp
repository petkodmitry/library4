<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
Вы вошли в систему как обычный пользователь<BR><BR>
${errorMessage}<BR><BR>
<a href="controller?cmd=login">На главную</a><BR>
<a href="controller?cmd=mybooks">Мои книги(не работает)</a><BR>
<a href="controller?cmd=searchbook">Поиск книги(не работает)</a><BR>
<a href="controller?cmd=myorders">Мои заказы в очереди(не работает)</a><BR>
<BR><a href="controller?cmd=logout">Выход</a><BR>
</body>
</html>
