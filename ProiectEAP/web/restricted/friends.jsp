<%@ page import="java.util.List" %>
<%@ page import="com.tvseries.containers.FriendsInfoContainer" %>
<%@ page import="com.tvseries.containers.BingeWatchingContainer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<FriendsInfoContainer> friends_list = (List<FriendsInfoContainer>)request.getAttribute("friends_list");
    List<FriendsInfoContainer> pending_list = (List<FriendsInfoContainer>)request.getAttribute("pending_list");
    List<FriendsInfoContainer> requests_list = (List<FriendsInfoContainer>)request.getAttribute("requests_list");
    List<BingeWatchingContainer> binge_watching_list = (List<BingeWatchingContainer>)request.getAttribute("binge_watching_list");

    String error_message = (String)request.getAttribute("error_message");
    error_message = error_message == null ? "" : error_message;
%>

<!DOCTYPE html>
<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "<%=request.getContextPath() + "/styles/general.css"%>">
    <title>Friends</title>
</head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <!-- An auxiliary form needed for buttons -->
                <form id = "auxform2" name = "auxform2" style = "display: none"></form>

                <form method = "post" action = "add_friend">
                    <input type = "text" name = "friend_username" placeholder = "Username">
                    <input type = "submit" name = "add_friend" value = "Add friend">
                </form>
                <p><%=error_message%></p>

                <h1>Friends</h1>
                <table class = "ltable">
                <%
                    for(FriendsInfoContainer f : friends_list)
                    {
                %>
                        <tr>
                            <td>
                                <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                    <img src = "<%=request.getContextPath() + f.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40">
                                </a>
                            </td>
                            <td>
                                <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                    <%=f.getFriend_display_name()%>
                                </a>
                            </td>
                        </tr>
                <%
                    }
                %>
                </table>

                <h1>Sent requests</h1>
                <table class = "ltable">
                    <%
                        for(FriendsInfoContainer f : pending_list)
                        {
                    %>
                    <tr>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <img src = "<%=request.getContextPath() + f.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40">
                            </a>
                        </td>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <%=f.getFriend_display_name()%>
                            </a>
                        </td>
                        <td>
                            <input type = "hidden" form = "auxform2" name = "friend_username" value = <%=f.getFriend_username()%>>
                            <input type = "submit" form = "auxform2" formmethod = "post" value = "Remove" formaction = <%=request.getContextPath()%>/restricted/remove_friend>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>

                <h1>Received requests</h1>
                <table class = "ltable">
                    <%
                        for(FriendsInfoContainer f : requests_list)
                        {
                    %>
                    <tr>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <img src = "<%=request.getContextPath() + f.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40">
                            </a>
                        </td>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + f.getFriend_username()%>>
                                <%=f.getFriend_display_name()%>
                            </a>
                        </td>
                        <td>
                            <input type = "hidden" form = "auxform2" name = "friend_username" value = <%=f.getFriend_username()%>>
                            <input type = "submit" form = "auxform2" formmethod = "post" value = "Accept" formaction = <%=request.getContextPath()%>/restricted/accept_friend>
                        </td>
                        <td>
                            <input type = "hidden" form = "auxform2" name = "friend_username" value = <%=f.getFriend_username()%>>
                            <input type = "submit" form = "auxform2" formmethod = "post" value = "Decline" formaction = <%=request.getContextPath()%>/restricted/remove_friend>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>

                <h1>Binge challenges</h1>
                <table class = "ltable">
                    <%
                        for(BingeWatchingContainer bw : binge_watching_list)
                        {
                    %>
                    <tr>
                        <td>
                            <a href = <%=request.getContextPath() + "/season_info?season_id=" + bw.getSeason_info().getFirst()%>>
                                <img src = "<%=request.getContextPath() + bw.getSeason_info().getThird()%>" alt = "poster picture" width = "40" height = "40">
                            </a>
                        </td>
                        <td>
                            <a href = <%=request.getContextPath() + "/season_info?season_id=" + bw.getSeason_info().getFirst()%>>
                                <%=bw.getSeason_info().getSecond()%>
                            </a>
                        </td>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + bw.getFriend_username()%>>
                                <img src = "<%=request.getContextPath() + bw.getFriend_picture()%>" alt = "profile picture" width = "40" height = "40">
                            </a>
                        </td>
                        <td>
                            <a href = <%=request.getContextPath() + "/user_info?username=" + bw.getFriend_username()%>>
                                <%=bw.getFriend_display_name()%>
                            </a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>
