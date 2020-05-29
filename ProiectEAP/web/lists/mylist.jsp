<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tvseries.containers.SeasonsListContainer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tvseries.containers.SearchListContainer" %>
<!-- A list displayed in the main section of the list.jsp -->

<table class = "ltable">
    <tr>
        <th>No</th>
        <th>Title</th>
        <th>Score</th>
        <th>Challenge</th>
    </tr>
    <%
        List<SeasonsListContainer> seasons_list = (List<SeasonsListContainer>)request.getAttribute("seasons_list");
    %>
    <%
        int index = 1;
        for(SeasonsListContainer s : seasons_list)
        {
    %>
            <tr>
                <td><%=index++%></td>
                <td>
                    <a href = "<%=request.getContextPath() + "/season_info?season_id=" + s.getSeason_id()%>">
                        <%=s.getSeason_title()%>
                    </a>
                </td>
                <td><%=s.getScore()%></td>
                <td>
                    <a href = "<%=request.getContextPath() + "/restricted/binge_watching?season_id=" + s.getSeason_id()%>">
                        Challenge
                    </a>
                </td>
            </tr>
    <%
        }
        if(seasons_list.isEmpty())
        {
    %>
            <tr>
               <td colspan="4">The list is empty</td>
            </tr>
    <%
        }
    %>
</table>