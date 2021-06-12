<%@ page pageEncoding="UTF-8" %>
<%@ include file="../../jspf/directive/page.jspf" %>
<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf"%>
<html>

<c:set var="title" value="Menu" scope="page"/>

<script>

</script>


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
                    </tr>
                    </thead>
                    <% int id = 0; %>
                    <c:set var="k" value="0"/>
                    <c:forEach var="booking" items="${bookedRoomsList}">
                        <c:set var="k" value="${k+1}"/>
                        <% id++;%>
                        <c:set var="checkedStatus" value="${booking.status}"/>
                            <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${booking.roomNumber}</td>
                            <td>${booking.userLogin}</td>
                            <td>${booking.status}</td>
                                <td>${booking.statusId == 1}</td>
                            <td>
                                <select name="status${k}">
                                    <option value="0" <c:if test="${booking.statusId==0}"><c:out value="selected"/></c:if>>
                                    <fmt:message key="booked_room_list_jsp.select.room.status.free" bundle="${rb}"/>'</option>
                                    <option value="1" <c:if test="${booking.statusId==1}"><c:out value="selected"/></c:if>>
                                        <fmt:message key="booked_room_list_jsp.select.room.status.booked" bundle="${rb}"/>'</option>
                                    <option value="2" <c:if test="${booking.statusId==2}"><c:out value="selected"/></c:if>>
                                        <fmt:message key="booked_room_list_jsp.select.room.status.occupied" bundle="${rb}"/>'</option>
                                    <option value="3" <c:if test="${booking.statusId==3}"><c:out value="select"/></c:if>>
                                        <fmt:message key="booked_room_list_jsp.select.room.status.notavaliable" bundle="${rb}"/>'</option>
                                </select>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/controller?command=update-booked&booked-id=${booking.id}&status=<%= request.getParameter("status" + id)%>">
                                    <fmt:message key="booked_room_list_jsp.button.edit" bundle="${rb}"/>_<%="status" + id%>
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
