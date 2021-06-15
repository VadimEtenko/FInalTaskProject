<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf"%>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="notification-main">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="nitification-form" action="controller">
                <input type="hidden" name="command" value="delete-notification"/>
                <input type="submit" value='<fmt:message key="user_notification_list_jsp.button.delete" bundle="${rb}"/>'/>

                <table id="nitification_list_table">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="user_notification_list_jsp.label.text" bundle="${rb}"/></td>
                    </tr>
                    </thead>

                    <c:set var="k" value="0"/>
                    <c:forEach var="item" items="${notificationList}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td><c:out value="${k}"/></td>
                            <td>${item.text}</td>
                            <td><input type="checkbox" name="notificationId" value="${item.id}"/></td>
                        </tr>
                    </c:forEach>
                </table>

            </form>
            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
