<%@ page pageEncoding="UTF-8" %>
<%@ include file="../../jspf/directive/page.jspf" %>
<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf"%>
<html>

<c:set var="title" value="Menu" scope="page"/>

<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>
            <form id="make_order" action="controller">
                <input type="hidden" name="command" value="verify-application"/>
                <input type="submit" value='<fmt:message key="requested_list_jsp.button.send.apply" bundle="${rb}"/>'/>

                <table id="list_menu_table">
                    <thead>
                        <tr>
                            <td>#</td>
                            <td><fmt:message key="requested_list_jsp.label.room.number" bundle="${rb}"/></td>
                            <td><fmt:message key="requested_list_jsp.label.user" bundle="${rb}"/></td>
                            <td><fmt:message key="requested_list_jsp.label.ok" bundle="${rb}"/></td>
                        </tr>
                    </thead>

                    <c:set var="k" value="0"/>
                    <c:forEach var="item" items="${requestedRoomsList}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${item.roomNumber}</td>
                            <td>${item.userLogin}</td>
                            <td><input type="checkbox" name="roomId" value="${item.roomId}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
            <%-- CONTENT --%>
        </td>
    </tr>

<customTagFile:myTagFooter/>

</table>
</body>
