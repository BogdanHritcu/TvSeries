<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Management</title>
</head>
<body>
    <h1>Add series</h1>
    <form method = "post" action = "<%=request.getContextPath()%>/administrative/upload_series" enctype = "multipart/form-data">
        <label>Title:
            <input type = "text" name = "series_title"><br>
        </label>

        <label>Rating:
            <input type = "text" name = "rating"><br>
        </label>

        <label>Poster:
            <input type = "file" name = "series_poster" accept="image/*"><br>
        </label>

        <input type = "submit" value = "Submit"><br>
    </form>

    <h1>Add season</h1>
    <form method = "post" action = "<%=request.getContextPath()%>/administrative/upload_season" enctype = "multipart/form-data">
        <label>Title:
            <input type = "text" name = "season_title"><br>
        </label>

        <label>Release date:
            <input type = "date" name = "release_date"><br>
        </label>

        <label>End date:
            <input type = "date" name = "end_date"><br>
        </label>

        <label>Season number:
            <input type = "number" name = "season_no"><br>
        </label>

        <label>Prequel id:
            <input type = "number" name = "prequel_id"><br>
        </label>

        <label>Sequel id:
            <input type = "number" name = "sequel_id"><br>
        </label>

        <label>Series id:
            <input type = "number" name = "series_id"><br>
        </label>

        <label>Poster:
            <input type = "file" name = "poster" accept = "image/*"><br>
        </label>

        <input type = "submit" value = "Submit"><br>
    </form>

</body>
</html>
