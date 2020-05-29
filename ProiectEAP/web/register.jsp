<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%Map<String, String> messages = (Map<String, String>)request.getAttribute("messages");%>
<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "styles/general.css">
        <title>Register</title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <form action = "<%=request.getContextPath() + "register"%>" method = "post">
                    <table>
                        <!-- First name -->
                        <tr>
                            <td><input type = "text" name = "fname" placeholder = "First name" maxlength = "30" autofocus autocomplete = "off"></td>
                        </tr>

                        <!-- Last name -->
                        <tr>
                            <td><input type = "text" name = "lname" placeholder = "Last name" maxlength = "30" autocomplete = "off"></td>
                        </tr>

                        <!-- Email -->
                        <tr>
                            <td><input type = "email" name = "email" placeholder = "Email" maxlength = "40" autocomplete = "off"></td>
                            <td><span><%= messages != null ? messages.get("email") != null ? messages.get("email") : "" : ""%></span></td>
                        </tr>

                        <!-- Birth date -->
                        <tr>
                            <td><input type = "date" name = "bdate" autocomplete = "off"></td>
                        </tr>

                        <!-- Username -->
                        <tr>
                            <td><input type = "text" name = "username" placeholder = "Username" maxlength = "25" autocomplete = "off"></td>
                            <td><span><%= messages != null ? messages.get("username") != null ? messages.get("username") : "" : ""%></span></td>
                        </tr>

                        <!-- Display name -->
                        <tr>
                            <td><input type = "text" name = "display_name" placeholder = "Display name" maxlength = "25" autocomplete = "off"></td>
                            <td><span><%= messages != null ? messages.get("display") != null ? messages.get("display") : "" : ""%></span></td>
                        </tr>

                        <!-- Password -->
                        <tr>
                            <td><input type = "password" name = "pass" placeholder = "Password" maxlength = "40" autocomplete = "new-password"></td>
                            <td><span><%= messages != null ? messages.get("password") != null ? messages.get("password") : "" : ""%></span></td>
                        </tr>

                        <!-- Confirm password -->
                        <tr>
                            <td><input type = "password" name = "cpass" placeholder = "Confirm password" maxlength = "40" autocomplete = "new-password"></td>
                            <td><span><%= messages != null ? messages.get("password") != null ? messages.get("password") : "" : ""%></span></td>
                        </tr>

                        <!-- Register button -->
                        <tr>
                            <td><input type = "submit" value = "Register" name = "register"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
