<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "styles/general.css">
        <title>Login</title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <form action = "<%=request.getContextPath() + "/log"%>" method = "post">
                    <table>
                        <!-- Username -->
                        <tr>
                            <td><input type = "text" id = "username" name = "username" placeholder = "Username" autocomplete = "off"></td>
                        </tr>

                        <!-- Password -->
                        <tr>
                            <td><input type = "password" id = "password" name = "password" placeholder = "Password" autocomplete = "off"></td>
                        </tr>

                        <!-- Login button -->
                        <tr>
                            <td><input type = "submit" value = "Login" name = "login"></td>
                        </tr>

                        <!-- Login message -->
                        <tr>
                            <td><%=request.getAttribute("login_message") == null ?"": (String)request.getAttribute("login_message")%></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
