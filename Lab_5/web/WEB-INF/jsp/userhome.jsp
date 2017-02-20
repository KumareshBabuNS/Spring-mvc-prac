<%-- 
    Document   : userhome
    Created on : Feb 9, 2017, 6:30:21 PM
    Author     : Aashri
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inbox</title>
    </head>
    <body>
        <jsp:include page="menu.jsp" />  
        <h1>Welcome <c:out value="${sessionScope.userName}" /></h1>
        <table>            
            <thead>
                <tr>
                    <th>From</th>
                    <th>Message</th>
                    <th>Date</th>
                    <th></th>
                </tr>                
            </thead>
            <tbody>
          <c:forEach items="${sessionScope.messageList}" var="message">
                <tr>
                    <td>${message.fromUser}</td>
                    <td>${message.message}</td>
                    <td>${message.messageDate}</td>
                    <td><a href="reply.htm?action=reply&to=${message.fromUser}">Reply</a></td>
                </tr>                
            </c:forEach> 
            </tbody>
            
        </table>
        
    </body>
</html>
