<%-- 
    Document   : signup
    Created on : Feb 19, 2017, 4:28:01 PM
    Author     : richa
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
    </head>
    <body>
        <h1>Please enter the following details to sign up</h1>
        <form action="login.htm" method="post">
            <p> Name : <input type="text" name='user' required/></p>
            <p>Password : <input type="password" name="password" required/></p>   
            <p>Email : <input type="email" name="email" /></p> 
            <input type="submit" value="Register"/> 
            <c:if test="${!empty requestScope.error}">
                <p style="color:red">Not registered</p>
            </c:if>
                <c:if test="${!empty requestScope.success}">
                <p style="color:green">Registered Successfully</p>
            </c:if>
            <input type="hidden" name="action" value="signupsubmit">            
        </form>
    </body>
</html>
