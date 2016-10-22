<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD><TITLE>Library login page</TITLE></HEAD>
<BODY><H3>Введите логин и пароль для входа в библиотеку:</H3>
<HR>
<FORM name="loginForm"
      method="POST"
      action= "/control">
    <INPUT type="hidden" name="cmd" value="login">
    Логин:<BR>
    <INPUT type="text"
           name="login"
           value=""><BR>
    Пароль:<BR>
    <INPUT type="password"
           name="password"
           value=""><BR>
    <INPUT type="submit" value="Enter">
</FORM>
<HR>
</BODY>
</HTML>
