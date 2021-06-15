<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf"%>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_order" action="controller" method="post">
                <input type="hidden" name="command" value="makeRequest"/>
                <input type="submit" value='<fmt:message key="free_room_list_jsp.button.send.apply" bundle="${rb}"/>'/>

                <table id="free_rooms_list_table">
                    <thead>
                        <tr>
                            <td>#</td>
                            <td><fmt:message key="free_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                            <td><fmt:message key="free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                            <td><fmt:message key="free_room_list_jsp.label.room.beds" bundle="${rb}"/></td>
                            <td><fmt:message key="free_room_list_jsp.label.room.cost" bundle="${rb}"/></td>
                        </tr>
                    </thead>

                    <c:set var="k" value="0"/>
                    <c:forEach var="room" items="${freeRoomsList}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${room.number}</td>
                            <td>${room.roomClass}</td>
                            <td>${room.numberOfBeds}</td>
                            <td>${room.cost}</td>
                            <td><input type="checkbox" name="roomId" value="${room.id}"/></td>
                        </tr>
                    </c:forEach>
                </table>

            </form>
            <h5>${createdSuccessfulMessageLabel}</h5>
            <h5>${createdUnsuccessfulMessageLabel}</h5>
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
