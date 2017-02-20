<%-- 
    Document   : reply
    Created on : Feb 9, 2017, 7:08:01 PM
    Author     : Aashri
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compose Message</title>
    </head>
    <body>
        <jsp:include page="menu.jsp" />
        <h1> Welcome <c:out value="${sessionScope.userName}"/> </h1>

        <form action='sendmessage.htm' method="post">
            <p><b> From : </b> <c:out value='${sessionScope.userName}' /></p>
            <p><b> To : </b><c:out value="${requestScope.to}" /></p>
            <p>Message : </p>
            <textarea name='message' rows='6' cols="40"></textarea><br />
            <input type='submit' value='send' />
            <input type='hidden' value ='${requestScope.to}' name='to'/>
            <input type='hidden' value ='${requestScope.toEmail}' name='toEmail'/>
            <input type='hidden' value ='sent' name='action'/>
        </form>



    </body>
</html>
