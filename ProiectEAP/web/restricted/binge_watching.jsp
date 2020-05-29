<%@ page import="com.tvseries.containers.FriendsInfoContainer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<FriendsInfoContainer> friends_list = (List<FriendsInfoContainer>)request.getAttribute("friends_list");
    String season_id = request.getParameter("season_id");
%>

<!DOCTYPE html>
<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "<%=request.getContextPath() + "/styles/general.css"%>">
    <title>Binge Watching</title>
</head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">

                <form method = "post" action = <%=request.getContextPath() + "/restricted/binge_watching"%>>
                    <input type = "hidden" name = "season_id" value = <%=season_id%>>
                    <%
                        for(FriendsInfoContainer f : friends_list)
                        {
                    %>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <img src = "<%=request.getContextPath() + f.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40">
                            </a>

                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <%=f.getFriend_display_name()%>
                            </a>
                            <input type="checkbox" name = "friend" value = <%=f.getFriend_username()%>>
                            <br>
                    <%
                        }
                    %>
                    <input type = "submit" name = "challenge" value = "Challenge">
                </form>
            </div>
        </div>
    </body>
</html>
