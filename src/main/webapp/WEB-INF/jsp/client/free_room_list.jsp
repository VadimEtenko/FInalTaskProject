<%@ include file="../../jspf/header.jspf" %>
<c:set var="title" value="Free rooms"/>
<%@ include file="../../jspf/head.jspf" %>

<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="main-container">
    <tr>
        <td class="content">
            <%-- CONTENT --%>
            <%-- Filter switcher --%>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="list-free-rooms"/>
                <input type="hidden" name="type-filter" value="cost">
                <input type="hidden" name="time_in" value="${time_in}">
                <input type="hidden" name="time_out" value="${time_out}">
                <input type="submit" value='Cost'/>
            </form>

            <form action="controller" method="post">
                <input type="hidden" name="command" value="list-free-rooms"/>
                <input type="hidden" name="type-filter" value="beds">
                <input type="hidden" name="time_in" value="${time_in}">
                <input type="hidden" name="time_out" value="${time_out}">
                <input type="submit" value='beds'/>
            </form>

            <form action="controller" method="post">
                <input type="hidden" name="command" value="list-free-rooms"/>
                <input type="hidden" name="type-filter" value="class">
                <input type="hidden" name="time_in" value="${time_in}">
                <input type="hidden" name="time_out" value="${time_out}">
                <input type="submit" value='class'/>
            </form>
            <%-- /Filter switcher --%>


            <form id="make_order" action="controller" method="post">
                <input type="hidden" name="command" value="create-request"/>
                <input type="hidden" name="time_in" value="${time_in}"/>
                <input type="hidden" name="time_out" value="${time_out}"/>

                <input type="submit"
                       value='<fmt:message key="find_free_room_list_jsp.button.send.apply" bundle="${rb}"/>'/>
                <br/>
                <table id="free_rooms_list_table">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.beds" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.cost" bundle="${rb}"/></td>
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
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
