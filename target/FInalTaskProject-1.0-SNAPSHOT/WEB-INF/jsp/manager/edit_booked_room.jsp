<%@ include file="../../jspf/header.jspf" %>
<c:set var="title" value="Booked rooms"/>
<%@ include file="../../jspf/head.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>



<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>
            <form id="edit_form" action="controller">
                <input type="hidden" name="command" value="update-booked"/>
                <table id="list_menu_table">
                    <thead>
                    <tr>
                        <td><fmt:message key="booked_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="booked_room_list_jsp.label.user" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <tr>

                        <td>${bookedRooms.roomNumber}</td>
                        <td>${bookedRooms.userLogin}</td>
                        <td>
                            <select name="status-id">
                                <option value="0" <c:if test="${bookedRooms.statusId==0}"><c:out value="selected"/></c:if>>
                                    <fmt:message key="booked_room_list_jsp.select.room.status.free"
                                                 bundle="${rb}"/></option>

                                <option value="1" <c:if test="${bookedRooms.statusId==1}"><c:out value="selected"/></c:if>>
                                    <fmt:message key="booked_room_list_jsp.select.room.status.booked"
                                                 bundle="${rb}"/></option>
                         
                                <option value="2" <c:if test="${bookedRooms.statusId==2}"><c:out value="selected"/></c:if>>
                                    <fmt:message key="booked_room_list_jsp.select.room.status.occupied"
                                                 bundle="${rb}"/></option>

                                <option value="3" <c:if test="${bookedRooms.statusId==3}"><c:out value="select"/></c:if>>
                                    <fmt:message key="booked_room_list_jsp.select.room.status.notavailable"
                                                 bundle="${rb}"/></option>
                            </select>
                        </td>
                        <td>
                            <input type="hidden" name="booked-id" value="${bookedRooms.id}"/>
                            <input type="submit"
                                   value="<fmt:message key="booked_room_list_jsp.button.edit" bundle="${rb}"/>">
                        </td>
                    </tr>
                </table>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <customTagFile:myTagFooter/>

</table>
</body>
