<%
    String context_path = request.getContextPath();
    String username = (String)session.getAttribute("username");
    String display_name = (String)session.getAttribute("display_name");
    String picture_path = context_path + (String)session.getAttribute("picture_path");

    String log = context_path + "/log";
    String home = context_path + "index.jsp";
    String login = context_path + "/restricted/login.jsp";
    String register = context_path + "/register.jsp";
    String completed = context_path + "/restricted/list?status=completed";
    String watching = context_path + "/restricted/list?status=watching";
    String plan = context_path + "/restricted/list?status=plan";
    String dropped = context_path + "/restricted/list?status=dropped";
    String friends = context_path + "/restricted/show_friends";
    String profile = context_path + "/restricted/profile.jsp";
%>
    <!-- An auxiliary form needed for buttons -->
<form id = "auxform1" name = "auxform1" style = "display: none"></form>
<jsp:include page = "searchbar.jsp"></jsp:include>
<div class = "sidenav">

    <%
        if(username == null)
        {
    %>
            <div class = "drop-list clickable">
                <a class = "dropbtn" href = <%=login%>>Login</a>
                <form method = "post" action = <%=log%>>
                    <input type = "text" id = "username" name = "username" placeholder = "Username" autocomplete = "off">
                    <input type = "password" id = "password" name = "password" placeholder = "Password" autocomplete = "off">
                    <input type = "submit" value = "Login" name = "login">
                </form>
            </div>

            <div class = "drop-list clickable">
                <a class = "dropbtn" href = <%=register%>>Register</a>
                <form action = "<%=request.getContextPath() + "/register"%>" method = "post">
                    <!-- First name -->
                    <input type = "text" name = "fname" placeholder = "First name" maxlength = "30" autofocus autocomplete = "off">

                    <!-- Last name -->
                    <input type = "text" name = "lname" placeholder = "Last name" maxlength = "30" autocomplete = "off">

                    <!-- Email -->
                    <input type = "email" name = "email" placeholder = "Email" maxlength = "40" autocomplete = "off">

                    <!-- Birth date -->
                    <input type = "date" name = "bdate" autocomplete = "off">

                    <!-- Username -->
                    <input type = "text" name = "username" placeholder = "Username" maxlength = "25" autocomplete = "off">

                    <!-- Display name -->
                    <input type = "text" name = "display_name" placeholder = "Display name" maxlength = "25" autocomplete = "off">

                    <!-- Password -->
                    <input type = "password" name = "pass" placeholder = "Password" maxlength = "40" autocomplete = "new-password">

                    <!-- Confirm password -->
                    <input type = "password" name = "cpass" placeholder = "Confirm password" maxlength = "40" autocomplete = "new-password">

                    <!-- Register button -->
                    <input type = "submit" value = "Register" name = "register">
                </form>
            </div>
    <%
        }
        else
        {
    %>
            <div class = "drop-list">
                <a class = "dropbtn" href = <%=profile%>><%=display_name%>
                    <img class = "dropbtn" src = "<%=picture_path%>" alt = "profile picture" style = "max-width: 40px; max-height: 40px; padding: 0px;">
                </a>
                <button type = "submit" form = "auxform1" formmethod = "post" formaction = <%=log%>>Logout</button>
            </div
    <%
        }
    %>

    <a href = <%=home%>>Home</a>

    <div class = "drop-list">
        <input class = "searchbar dropbtn" type = "text" id = "search_input" placeholder = "Search..." autocomplete = "off" oninput = "onSearch()" onfocusout = "this.value = ''; onSearch()">
        <div id = "search_output">
        <!-- search list updated by XMLHttpRequest -->
        </div>
    </div>

    <div class = "drop-list">
        <a class = "dropbtn" href = <%=watching%>>MyList</a>
        <a href = <%=completed%>>Completed</a>
        <a href = <%=watching%>>Watching</a>
        <a href = <%=plan%>>Plan to watch</a>
        <a href = <%=dropped%>>Dropped</a>
    </div>

    <a href = <%=profile%>>Profile</a>

    <a href = <%=friends%>>Friends</a>
</div>
