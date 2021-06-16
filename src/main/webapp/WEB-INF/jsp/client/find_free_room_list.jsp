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
                <input type="hidden" name="command" value="list-free-rooms"/>
                <input type="submit" value='<fmt:message key="find_free_room_list_jsp.label.send.find" bundle="${rb}"/>'/>
                <br>
                <table id="date_table">
                    <thead>
                    <tr>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timeout" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <tr>
                        <td><input type="date" name="time_in" min="<%= LocalDate.now() %>" required></td>
                        <td><input type="date" name="time_out" min="<%= LocalDate.now().plusDays(1) %>" required></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>