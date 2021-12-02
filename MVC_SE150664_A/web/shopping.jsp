<%-- 
    Document   : shopping
    Created on : Jul 11, 2021, 3:26:01 PM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SHOPPING</title>
    </head>
    <body>
        <h1>SHOPPING</h1>
        <form action="addItemToCartServlet">
            <select name="cboBox" style="float: left; width: 100px ">
                <c:set var="result" value="${sessionScope.LIST_PRODUCT}"/>
                <c:if test="${not empty result}">
                    <c:forEach var="dto" items="${result}">


                        <option 
                            <c:if test="${dto.quantity lt 1}">
                                disabled=""
                            </c:if>
                            >
                            ${dto.nameproduct}
                        </option>
                    </c:forEach>
          
        </c:if>
        <c:if test="${empty result}">
            <h2 style="color: red"> No Product in Shop </h2>
        </c:if>
    </select>
                </br>            
           <input style="float: left; width: 225px " type="submit" name="btAction" value="Add Item to Your Cart"/>  
        </form>
    <form action="viewcartJSP">
        <input style="float: left; width: 225px " type="submit" name="btAction" value="View Your Cart"/>
    </form>


</body>
</html>
