<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "../styles/general.css">
    <link rel = "stylesheet" type = "text/css" href = "../styles/table_style.css">
    <title>MyList</title>
</head>
<body>
    <div class="grid-container">
        <jsp:include page = "/home/sidenav.jsp"></jsp:include>
        <jsp:include page = "/home/header.jsp"></jsp:include>
        <div class = "content">
            <jsp:include page = "../lists/mylist.jsp"></jsp:include>
        </div>
    </div>
</body>
</html>
