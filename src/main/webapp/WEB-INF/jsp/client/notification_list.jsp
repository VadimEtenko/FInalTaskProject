<%@ include file="../../jspf/directive/taglib.jspf" %>
<%@ include file="../../jspf/header.jspf" %>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<body>
<table id="notification-main">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

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
                        <td>
                            <form id="nitification-form" action="controller">
                                <input type="hidden" name="command" value="delete-notification"/>
                                <input type="submit"
                                       value='<fmt:message key="user_notification_list_jsp.button.pay" bundle="${rb}"/>'/>
                                <input type="hidden" name="notificationId" value="${item.id}"/>
                                <input type="hidden" name="bookedId" value="${item.bookedId}"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <%-- CONTENT --%>
        </td>
    </tr>
</table>
</body>
