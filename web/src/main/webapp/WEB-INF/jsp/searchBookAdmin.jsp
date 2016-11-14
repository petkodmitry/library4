<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>Управление книгами</title>
</head>
<body><H3>Управление книгами администратором</H3>
<HR>
<BR><a href="controller?cmd=addBook">Добавить книгу в базу</a><BR><BR>
<BR>Поиск книг
<form method="post" action="controller">
    <INPUT type="hidden" name="cmd" value="searchBookAdmin">
    <table>
        <tr>
            <td><input name="searchTextInBook" title="Введите слово или часть слова для поиска книги" type="text"
                       value=""></td>
            <td>
                <INPUT type="submit" title="Поиск по названию или автору" value="Найти">
            </td>
        </tr>
    </table>
</form>

<c:if test="${requestScope['info'] != null}">
    ${info}<BR>
</c:if>

<BR>

<c:if test="${searchBookAdmin != null && !searchBookAdmin.isEmpty()}">
    <form>
        <table border="1">
            <tr>
                <td>ID книги</td>
                <td>Наименование</td>
                <td>Автор</td>
                <td>Свободна/На руках</td>
                <td>Удалить из базы</td>
                <%--<td>Заказ в ЧЗ</td>--%>
            </tr>
            <c:forEach items="${searchBookAdmin}" var="book">
                <tr>
                    <td>${book.getBookId()}</td>
                    <td>${book.getTitle()}</td>
                    <td>${book.getAuthor()}</td>
                    <td>
                        <c:if test="${book.isBusy() == false}">Available</c:if>
                        <c:if test="${book.isBusy() == true}">On Hand</c:if>
                    </td>
                    <td>
                        <c:if test="${book.isBusy() == false}">
                            <a href="controller?cmd=deleteBook&bookId=${book.getBookId()}">Удалить</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</c:if>

<a href="controller?cmd=login">На главную</a>
<BR><BR><c:if test="${requestScope['errorMessage'] != null}">
    Ошибка: ${errorMessage}
</c:if>
</body>
</html>