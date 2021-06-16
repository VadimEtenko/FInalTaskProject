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
                    <td>#</td>
                    <td><fmt:message key="free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                    <td><fmt:message key="free_room_list_jsp.label.room.beds" bundle="${rb}"/></td>
                    <td>TIME IN</td>
                    <td>TIME OUT</td>
                </tr>
                </thead>
                <c:set var="k" value="0"/>
                <c:forEach var="wish" items="${userWishesList}">
                    <c:set var="k" value="${k+1}"/>
                    <tr>
                        <td>${k}</td>
                        <td>${wish.roomClass}</td>
                        <td>${wish.number_of_beds}</td>
                        <td>${wish.time_in}</td>
                        <td>${wish.time_out}</td>
                    <tr/>
                </c:forEach>
            </table>

            <br>

            <table id="free_rooms_offer_list_table">
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
                <c:forEach var="room" items="${offeredRoomsList}">
                    <c:set var="k" value="${k+1}"/>
                    <tr>
                        <td>${k}</td>
                        <td>${room.number}</td>
                        <td>${room.roomClass}</td>
                        <td>${room.numberOfBeds}</td>
                        <td>${room.cost}</td>
                        <td>
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="apply-offer">
                                <input type="hidden" name="userId" value="${user.id}"/>
                                <input type="hidden" name="offeredRoomId" value="${room.id}"/>
                                <input type="submit" value="Agree"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </td>
    </tr>
</table>
</body>
</html>
