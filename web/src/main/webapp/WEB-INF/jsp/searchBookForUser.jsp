<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>Поиск книг</title>
</head>
<body><H3>Поиск книги для заказа</H3>
<HR>
<form method="post" action="controller">
    <INPUT type="hidden" name="cmd" value="searchbook">
    <table>
        <tr>
            <td><input name="searchTextInBook" title="Введите слово или часть слова для поиска книги" type="text" value=""></td>
            <td>
                <INPUT type="submit" title="Поиск по названию или автору" value="Найти">
            </td>
        </tr>
    </table>
</form>
<BR><BR>
<form>
    <table border="1">
        <c:if test="${searchBookForUser != null && !searchBookForUser.isEmpty()}">
            <tr>
                <td>Наименование</td>
                <td>Автор</td>
                <td>Наличие</td>
                <td>Заказ на абонемент</td>
                <td>Заказ в ЧЗ</td>
            </tr>
        </c:if>
        <c:forEach items="${searchBookForUser}" var="book">
            <tr>
                <td>${book.getTitle()}</td>
                <td>${book.getAuthor()}</td>
                <td>
                    <c:if test="${book.isBusy() == false}">Available</c:if>
                    <c:if test="${book.isBusy() == true}">N/A</c:if>
                </td>
                <td>
                    <c:if test="${book.isBusy() == false}">
                        <a href="controller?cmd=orderToHome&bookId=${book.getBookId()}" >Заказать</a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${book.isBusy() == false}">
                        <a href="controller?cmd=orderToReadingRoom&bookId=${book.getBookId()}">Заказать</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
<BR><a href="controller?cmd=login">На главную</a>
<c:if test="${requestScope['errorMessage'] != null}">
    Ошибка: ${errorMessage}
</c:if>
</body>
</html>
