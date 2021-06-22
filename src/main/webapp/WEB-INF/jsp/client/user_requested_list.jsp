<%@ include file="../../jspf/header.jspf" %>
<c:set var="title" value="Your requestes"/>
<%@ include file="../../jspf/head.jspf" %>

<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>
            <form id="make_order" action="controller">
                <input type="hidden" name="command" value="delete-reservation"/>
                <input type="submit" value='<fmt:message key="requested_list_jsp.button.delete" bundle="${rb}"/>'/>

                <table>
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="user_requested_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="user_requested_list_jsp.label.checkbox.delete" bundle="${rb}"/></td>
                    </tr>
                    </thead>

                    <c:set var="k" value="0"/>
                    <c:forEach var="item" items="${userRequestedRoomsList}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${item.roomNumber}</td>
                            <td><input type="checkbox" name="bookedId" value="${item.id}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
