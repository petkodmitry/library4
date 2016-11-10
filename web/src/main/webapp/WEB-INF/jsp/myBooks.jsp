<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page errorPage="error.jsp" %>
<html>
<head>
    <%--<script src="js/jquery-3.1.1.js"></script>--%>
    <%--<script src="js/prolongOrder.js"></script>--%>

    <title>Мои книги</title>
</head>
<body><H3>Список книг у меня на руках</H3>
<HR>

<form>
    <table border="1">
        <c:if test="${myBooksList != null && !myBooksList.isEmpty()}">
            <tr>
                <td>ID книги</td>
                <td>Наименование</td>
                <td>Автор</td>
                <td>Место выдачи</td>
                <td>Дата заказа</td>
                <td>Дата возврата</td>
                <td>Продление на новый срок</td>
            </tr>
        </c:if>
        <c:forEach items="${myBooksList}" var="order">
            <tr>
                <td>${order.getBookId()}</td>
                <td>${order.getTitle()}</td>
                <td>${order.getAuthor()}</td>
                <td>${order.getPlace().toString()}</td>
                <td>${order.getStartDate()}</td>
                <td>${order.getEndDate()}</td>
                <td>
                    <c:if test="${order.getPlace().toString().equals('Абонемент')}">
                        <a href="controller?cmd=prolongOrder&orderId=${order.getOrderId()}">Продлить</a>
                        <%--<a href="javascript:prolongOrder(${order.getOrderId()})">Продлить</a>--%>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

<BR><a href="controller?cmd=login">На главную</a>
<BR><BR><c:if test="${requestScope['errorMessage'] != null}">
    Ошибка: ${errorMessage}
</c:if>
</body>
</html>
