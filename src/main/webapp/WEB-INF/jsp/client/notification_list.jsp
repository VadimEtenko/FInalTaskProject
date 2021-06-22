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

            <table id="notification_list_table">
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
                            <%-- If the notification of approval - add a button to pay  --%>

                        <td>

                            <form id="notification-form" action="controller">
                                <input type="hidden" name="command" value="delete-notification"/>
                                <input type="submit"
                                <c:choose>
                                    <c:when test="${item.bookedId == null}">
                                           value='<fmt:message key="user_notification_list_jsp.button.pay" bundle="${rb}"/>'
                                    </c:when>
                                    <c:when test="${item.bookedId != null}">
                                        value='<fmt:message key="user_notification_list_jsp.button.delete" bundle="${rb}"/>'
                                    </c:when>
                                </c:choose>
                                />
                                <input type="hidden" name="notificationId" value="${item.id}"/>
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
</html>
