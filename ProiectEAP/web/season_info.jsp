<%@ page import="com.tvseries.containers.SeasonInfoContainer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tvseries.utils.Triplet" %>
<%@ page import="javafx.util.Pair" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    SeasonInfoContainer season_info = (SeasonInfoContainer)request.getAttribute("season_info");
    Triplet<Integer, String, String> prequel = season_info.getPrequel();
    Triplet<Integer, String, String> sequel = season_info.getSequel();
    Triplet<Integer, String, String> series = season_info.getSeries();
    List<Triplet<Integer, String, Boolean>> episodes = season_info.getEpisodes();

    List<Pair<String, Integer>> time_spent = (List<Pair<String, Integer>>)request.getAttribute("time_spent");
    List<String> watch_status = new ArrayList<>(); //list for displaying watch status
    watch_status.add("Watching");
    watch_status.add("Completed");
    watch_status.add("Dropped");
    watch_status.add("Plan to watch");
    watch_status.add("Not watching");

    List<Integer> scores = new ArrayList<>(); //list for displaying scores
    for(int i = 0; i <= 10; i++)
    {
        scores.add(i);
    }

    String current_ws = season_info.getWatch_status();
    String selected_ws = current_ws == null ? "Not watching" : current_ws;

    Integer current_score = season_info.getScore();
    Integer selected_score = current_score == null ? 0 : current_score;

%>

<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "styles/general.css">
        <title><%=season_info.getSeason_title()%></title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <h1><%=season_info.getSeason_title()%></h1>
                <img src = "<%=request.getContextPath() + season_info.getSeason_poster()%>" alt = "poster" width = "400" height = "400"><br>

                <%
                    if(time_spent != null)
                    {
                %>
                        <h3>Time spent per month</h3>
                <%
                        for(Pair<String, Integer> e : time_spent)
                        {
                %>
                            <p><%=e.getKey() + ": " + e.getValue() + "minutes"%></p>
                <%
                        }
                    }
                %>
                <h3>Series</h3>
                <p>
                    <a href = "<%=request.getContextPath() + "/series_info?series_id=" + series.getFirst()%>"><%=series.getSecond()%></a>
                    <img alt = "series poster" src = "<%=request.getContextPath() + series.getThird()%>" width = "80" height = "100">
                </p>

                <h3>Season number</h3>
                <p><%=season_info.getSeason_no()%></p>

                <h3>Prequel</h3>
                <p>
                    <%=prequel.getFirst() == null ? "No prequel" : "<a href = " + request.getContextPath() + "/season_info?season_id=" + prequel.getFirst() + ">" + prequel.getSecond() + "</a>"%>
                    <img alt = "series poster" src = "<%=request.getContextPath() + series.getThird()%>>">
                </p>

                <h3>Sequel</h3>
                <a>
                    <%=sequel.getFirst() == null ? "No sequel" : "<a href = " + request.getContextPath() + "/season_info?season_id=" + sequel.getFirst() + ">" + sequel.getSecond() + "</a>"%>
                </a>

                <form method = "post" action = "update_list">
                    <!-- additional parameters for updates on the list -->
                    <input type = "hidden" name = "season_id" value = "<%=season_info.getSeason_id()%>">
                    <input type = "hidden" name = "current_watch_status" value = "<%=season_info.getWatch_status()%>">

                    <label>
                        Status:
                        <select name = "watch_status">
                            <%
                                for(String ws : watch_status)
                                {
                            %>
                                    <option value = "<%=ws%>" <%=ws.equals(selected_ws) ? "selected" : ""%>><%=ws%></option>
                            <%
                                }
                            %>
                        </select>
                    </label>

                    <label>
                        Score:
                        <select name = "score">
                            <%
                                for(Integer score : scores)
                                {
                            %>
                            <option value = "<%=score%>" <%=score.equals(selected_score) ? "selected" : ""%>><%=score%></option>
                            <%
                                }
                            %>
                        </select>
                    </label>

                    <br> <!-- TODO: remove <br> -->
                    <%
                        for(Triplet<Integer, String, Boolean> e : episodes)
                        {
                    %>
                            <span><%=e.getSecond()%></span>
                            <input type = "checkbox" name = "episode" value = "<%=e.getFirst()%>" <%=e.getThird()?"checked" : ""%>><br>
                    <%
                        }
                    %>
                    <input type = "submit" value = "Update">
                </form>
            </div>
        </div>
    </body>
</html>