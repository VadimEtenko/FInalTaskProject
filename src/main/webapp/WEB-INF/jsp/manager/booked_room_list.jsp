<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="title" value="Menu" scope="page"/>


<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_order" action="controller">

                <table id="list_menu_table">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="booked_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="booked_room_list_jsp.label.user" bundle="${rb}"/></td>
                        <td><fmt:message key="booked_room_list_jsp.label.status" bundle="${rb}"/></td>
                        <td><fmt:message key="booked_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                        <td><fmt:message key="booked_room_list_jsp.label.date.timout" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <c:set var="k" value="0"/>
                    <c:forEach var="booking" items="${bookedRoomsList}">
                        <c:set var="k" value="${k+1}"/>
                        <c:set var="checkedStatus" value="${booking.status}"/>
                        <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${booking.roomNumber}</td>
                            <td>${booking.userLogin}</td>
                            <td><fmt:message key="booked_room_list_jsp.select.room.status.${booking.status}"
                                             bundle="${rb}"/></td>
                            <td>
                                <a href="controller?command=update-booked-list&booked-id=${booking.id}">
                                    <fmt:message key="booked_room_list_jsp.button.edit" bundle="${rb}"/>
                                </a>
                            </td>
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
