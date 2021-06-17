<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <table id="wish_table">
                <thead>
                <tr>
                    <td><fmt:message key="find_free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                    <td><fmt:message key="find_free_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                    <td><fmt:message key="find_free_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                    <td><fmt:message key="find_free_room_list_jsp.label.date.timeout" bundle="${rb}"/></td>
                </tr>
                </thead>
                <tr>
                    <td>${wish.roomClass}</td>
                    <td>${wish.number_of_beds}</td>
                    <td>${wish.time_in}</td>
                    <td>${wish.time_out}</td>
                <tr>
                </tr>
            </table>

            <br>

            <form action="controller" method="post">
                <input type="hidden" name="command" value="create-offer">
                <input type="hidden" name="userId" value="${wish.user_id}"/>
                <input type="submit" value="Offer"/>
                <table id="free_rooms_offer_list_table">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timeout" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <c:set var="k" value="0"/>
                    <c:forEach var="room" items="${suitableRooms}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td>${k}</td>
                            <td>${room.number}</td>
                            <td>${room.roomClass}</td>
                            <td>${room.numberOfBeds}</td>
                            <td>${room.cost}</td>
                            <td><input type="checkbox" name="suitableRoomId" value="${room.id}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>

