<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>


                <table id="free_rooms_list_table">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.class" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.room.number" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timein" bundle="${rb}"/></td>
                        <td><fmt:message key="find_free_room_list_jsp.label.date.timeout" bundle="${rb}"/></td>
                    </tr>
                    </thead>
                    <c:set var="k" value="0"/>
                    <c:forEach var="wish" items="${requestWishList}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td>${k}</td>
                            <td>${wish.roomClass}</td>
                            <td>${wish.number_of_beds}</td>
                            <td>${wish.time_in}</td>
                            <td>${wish.time_out}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="plan-offer">
                                    <input type="hidden" name="wishId" value="${wish.id}">
                                    <input type="submit" value="Offer">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </form>
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
</html>