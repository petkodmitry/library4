<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD><TITLE>Список пользователей</TITLE></HEAD>
<BODY><H3>Таблица: пользователи</H3>
<HR>

<table border="1" frame="void">
    <tbody>
    <tr>
        <td>ID</td>
        <td>Имя</td>
        <td>Фамилия</td>
        <td>Логин</td>
        <td>Пароль</td>
        <td>Является админом</td>
        <td>Заблокирован</td>
    </tr>
    <c:forEach items="${userSet}" var="user">
        <tr>
            <td><c:out value="${user.getUserId()}"/></td>
            <td><c:out value="${user.getFirstName()}"/></td>
            <td><c:out value="${user.getLastName()}"/></td>
            <td><c:out value="${user.getLogin()}"/></td>
            <td><c:out value="${user.getPassword()}"/></td>
            <td><c:out value="${user.isAdmin()}"/></td>
            <td><c:out value="${user.isBlocked()}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<HR>
${errorMessage}<br><br>
<a href="controller?cmd=login">На главную</a>
</BODY>
</HTML>
