<%@ page import="com.tvseries.containers.UserInfoContainer" %>
<%@ page import="com.tvseries.containers.FriendsInfoContainer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserInfoContainer user_info = (UserInfoContainer) request.getAttribute("user_info");
    List<FriendsInfoContainer> friends = user_info.getFriends();
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "styles/general.css">
        <title><%=user_info.getDisplay_name()%></title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <p><%=user_info.getDisplay_name()%></p>
                <img src = "<%=request.getContextPath() + user_info.getPicture_path()%>" alt = "profile picture" width="100" height="100">
                <p>First name:<%=user_info.getFirst_name()%></p>
                <p>Last name:<%=user_info.getLast_name()%></p>
                <p>Birth date:<%=user_info.getBirth_date()%></p>

                <table class = "ltable">
                    <%
                        for(FriendsInfoContainer f : friends)
                        {
                    %>
                    <tr>
                        <td><a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>><img src = "<%=request.getContextPath() + f.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40"></a></td>
                        <td><a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>><%=f.getFriend_display_name()%></a></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>
