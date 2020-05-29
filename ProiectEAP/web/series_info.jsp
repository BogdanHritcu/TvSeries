<%@ page import="com.tvseries.containers.SeriesInfoContainer" %>
<%@ page import="com.tvseries.utils.Triplet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    SeriesInfoContainer series_info = (SeriesInfoContainer)request.getAttribute("series_info");
    List<Triplet<Integer, String, String>> seasons = series_info.getSeasons();
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "styles/general.css">
            <title><%=series_info.getSeries_title()%></title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">

                <p><%=series_info.getSeries_title()%></p><br>
                <img src = "<%=request.getContextPath() + series_info.getSeries_poster()%>" alt = "poster" width = "400" height = "400"><br>

                <%
                    for(Triplet<Integer, String, String> s : seasons)
                    {
                %>
                        <a href = "<%=request.getContextPath() + "/season_info?season_id=" + s.getFirst()%>">
                            <%=s.getSecond()%>
                        </a>
                        <img src = "<%=request.getContextPath() + s.getThird()%>" alt = "poster" width = "40" height = "40"><br>
                <%
                    }
                %>
            </div>
        </div>
    </body>
</html>
