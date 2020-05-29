<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String display_name = (String)session.getAttribute("display_name");
    String picture_path = (String)session.getAttribute("picture_path");

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel = "stylesheet" type = "text/css" href = "../styles/general.css">
        <title>Title</title>
    </head>
    <body>
        <div class="grid-container">
            <jsp:include page = "/home/sidenav.jsp"></jsp:include>
            <jsp:include page = "/home/header.jsp"></jsp:include>
            <div class = "content">
                <h1><%=display_name%></h1>
                <img src = "<%=request.getContextPath() + picture_path%>" alt = "profile picture" width = "400" height = "400">

                <form method = "post" action = "<%=request.getContextPath()%>/upload_profile_picture" enctype = "multipart/form-data">
                    <input type = "file" name = "profile_picture" accept = "image/*" value = "Change picture">
                    <input type = "submit" value = "Update">
                </form>
            </div>
        </div>
    </body>
</html>
