<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>
<c:set var="title" value="Registration"/>
<%@ include file="WEB-INF/jspf/header.jspf" %>

<html>

<body>
<table id="main-container">
    <tr>
        <td class="content center">

            <%-- CONTENT --%>
            <form id="login_form" action="controller?command=registration" method="post">
                <fieldset>
                    <fmt:message key="registration_jsp.label.name" bundle="${rb}"/>
                    <input type="text" name="name" required pattern="[А-Яа-яЁё]{3,}"
                           title="Must contain at least 2 lat. letters"/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.surname" bundle="${rb}"/>
                    <input name="surname" required pattern="[A-Za-zА-Яа-яЁё]{2,}"
                           title="Must contain at least 2 lat. letters"/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.login" bundle="${rb}"/>
                    <input name="login" required pattern="[A-Za-z0-9]{3,}"
                           title="Must contain at least 4 lat. letters"/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.password" bundle="${rb}"/>
                    <input type="password" name="password" autocomplete="off" required minlength="5"
                           <%--pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])"--%>
                           title="Must contain at least one number and one uppercase and lowercase
                           letter, and at least 8 or more characters"/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.second.password" bundle="${rb}"/>
                    <input type="password" autocomplete="off" required minlength="5"
                    <%--pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])"--%>
                           title="Must contain at least one number and one uppercase and lowercase
                           letter, and at least 8 or more characters"/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.email" bundle="${rb}"/>
                    <input type="email" name="email" required/>
                </fieldset>

                <fieldset>
                    <fmt:message key="registration_jsp.label.country" bundle="${rb}"/>
                    <p>
                        <select size="1" name="local">
                            <option selected value="ru">Russian</option>
                            <option value="en">England</option>
                        </select>
                    </p>

                </fieldset>

                <input type="submit" value="<fmt:message key="registration_jsp.button.registration" bundle="${rb}"/>"/>
            </form>
            <%-- CONTENT --%>
        </td>
    </tr>

</table>
</body>
</html>
