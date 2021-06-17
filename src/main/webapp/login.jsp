<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>
<c:set var="title" value="Login"/>
<%@ include file="WEB-INF/jspf/header.jspf" %>

<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<body>
<table id="main-container">
    <tr>
        <td class="content center">

            <%-- CONTENT --%>
            <form id="login_form" action="controller?command=login" method="post">
                <fieldset>
                    <fmt:message key="login_jsp.label.nikname" bundle="${rb}"/>
                    <input name="login"/><br/>
                </fieldset>
                <br/>

                <fieldset>
                    <fmt:message key="login_jsp.label.password" bundle="${rb}"/>
                    <input type="password" name="password"/>
                </fieldset>
                <br/>

                <input type="submit" value='<fmt:message key="login_jsp.button.login" bundle="${rb}"/>!'>
            </form>

            <form action="registration.jsp">
                <button><fmt:message key="login_jsp.button.registration" bundle="${rb}"/></button>
            </form>
            <%-- CONTENT --%>

        </td>
    </tr>

</table>
</body>
</html>