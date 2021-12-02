<%-- 
    Document   : viewcart
    Created on : Jun 17, 2021, 7:30:24 AM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Market</title>
    </head>
    <body>
        <h1>Your Items</h1>
        <c:set var="itemlist" value="${sessionScope.LIST_ITEM_IN_CART}"/>
        <c:if test="${not empty itemlist}">
            <c:set var="result" value="${itemlist.orderList}"/>
            <c:if test="${not empty result}">
                <form action="removeItemFromCartServlet">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Click to remove</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            
                        
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.key.nameproduct}</td>
                            <td>${dto.value}</td>
                            <td>${dto.key.price}</td>
                            <td>
                                <c:set var="total" value="${dto.key.price*dto.value}" />
                                ${total}
                            </td>
                            <td>
                                <input type="checkbox" name="chkItem" value="${dto.key.nameproduct}"/>
                            </td>
                        </tr>
                        
                        </c:forEach>                  
                    </tbody>
                </table> 
                    <a href="shoppingJSP">Add More Product To Cart</a></br>
                    <input style="float: left; width: 225px " type="submit" value="Remove item" name="btAction" />
                </form>
                <form action="checkOutServlet">
                    <input style="float: left; width: 225px " type="submit" name="btAction" value="Check Out"/>
                </form>
            </c:if>
            <c:if test="${empty result}">
                 <h3 style="color: red">No record</h3><br>
                 <a href="shoppingJSP">Add More Product To Cart</a>
            </c:if>   
        </c:if>
         <c:if test="${empty itemlist}">
            <h3 style="color: red">No record</h3><br>
            <a href="shoppingJSP">Add More Product To Cart</a>

        </c:if>
    </body>
</html>
