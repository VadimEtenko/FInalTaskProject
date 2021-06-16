<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_order" action="controller" method="post">
                <input type="hidden" name="command" value="create-plan-request"/>
                <input type="submit" value='<fmt:message key="find_free_room_list_jsp.button.send.apply" bundle="${rb}"/>'/>

                <table id="free_rooms_list_table">
                    <thead>
                    <tr>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.beds" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timeout" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <tr>
                        <td>
                            <select name="classId" required>
                                <option value="1" selected>Junior Suite</option>
                                <option value="2">Suite</option>
                                <option value="3">De Luxe</option>
                                <option value="4">Duplex</option>
                            </select>
                        </td>
                        <td><input type="number" name="numberOfBeds" required min="1" max="4"></td>
                        <td><input type="date" required name="time_in" min="<%= LocalDate.now() %>"></td>
                        <td><input type="date" required name="time_out" min="<%= LocalDate.now().plusDays(1) %>"></td>
                    </tr>
                </table>

            </form>
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>