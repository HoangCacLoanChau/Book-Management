<%-- 
    Document   : createNewAccount
    Created on : Jun 29, 2021, 8:07:10 AM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
    </head>
    <body>
        <h1>Create new Account</h1>
        <form action="createNewAccountServlet" method="POST">
            <c:set var="errors" value ="${requestScope.CREATE_ERROR}"/>
            
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}" />(6-20 chars)<br/>
            <c:if test ="${not empty errors.usernameLengthErr}">
                <font color ="green">
                ${errors.usernameLengthErr}
                </font></br>
            </c:if>   
                
            <c:if test ="${not empty errors.usernameIsExisted}">
                <font color ="red">
                ${errors.usernameIsExisted}
                </font></br>
            </c:if>     
                
            Password* <input type="password" name="txtPassword" value="" />(6-20 chars)<br/>
            <c:if test ="${not empty errors.passwordLengthErr}">
                <font color ="red">
                ${errors.passwordLengthErr}
                </font></br>
            </c:if>    
                
                Confirm* <input type="password" name="txtConfirm" value="" /></br>
                
            <c:if test ="${not empty errors.confirmNotMatched}">
                <font color ="red">
                ${errors.confirmNotMatched}
                </font></br>
            </c:if>   
                
            Full name* <input type="text" name="txtFullname" value="${param.txtFullname}" />(2-50 chars)</br>
            <c:if test ="${not empty errors.fullnameLengthErr}">
                <font color ="red">
                ${errors.fullnameLengthErr}
                </font></br>
            </c:if>
           
            <input type="submit" value="Create new account" name="btAction" />
                     <input type="reset" value="Reset" />
        </form>
    </body>
</html>
