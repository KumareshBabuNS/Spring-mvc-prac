<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lab5</title>
    </head>
    <body>
        <h1>Lab 5</h1>
        <form action="login.htm" method="post">
            <p>Username : <input type="text" name='user' /></p>
            <p>Password : <input type="password" name="password" /></p>           
            <input type="submit" value="Login"/> &nbsp;&nbsp;<a href ="login.htm?action=signUp">SignUp</a>
            <c:if test="${!empty requestScope.error}">
                <p style="color:red">Invalid credentials</p>
            </c:if>
            <input type="hidden" name="action" value="login">            
        </form>     
    </body>
</html>
