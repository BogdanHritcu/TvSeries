<%@ page import="com.tvseries.utils.Triplet" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tvseries.containers.SearchListContainer" %>
<!-- A list containing the search results -->
<%
    SearchListContainer search_list = (SearchListContainer)request.getAttribute("search_list");
    List<Triplet<Integer, String, String>> series = search_list.getSeries();
    List<Triplet<Integer, String, String>> seasons = search_list.getSeasons();
%>

<%
        for(Triplet<Integer, String, String> s : seasons)
        {
%>
            <a href = "<%=request.getContextPath() + "/season_info?season_id=" + s.getFirst()%>">
                <%=s.getSecond()%>
                <!-- TODO: Add poster -->
            </a>
<%
        }

        for(Triplet<Integer, String, String> s : series)
        {
%>
            <a href = "<%=request.getContextPath() + "/series_info?series_id=" + s.getFirst()%>">
                <%=s.getSecond()%>
                <!-- TODO: Add poster -->
            </a>
<%
        }
%>

