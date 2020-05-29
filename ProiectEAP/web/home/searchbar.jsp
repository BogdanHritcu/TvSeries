<script id = "search-script">

    function debounce(fn, duration) {
        var timer;
        return function(){
            clearTimeout(timer);
            timer = setTimeout(fn, duration);
        }
    }

    function search()
    {
        let search_input = document.getElementById("search_input");
        let search_output = document.getElementById("search_output");

        var http = new XMLHttpRequest();
        http.onreadystatechange = function()
        {
            if (this.readyState === 4 && this.status === 200) {
                search_output.innerHTML = this.responseText;
            }
        };

        http.open("GET", "<%=request.getContextPath() + "/search_list?search_key="%>" + search_input.value, true);
        http.send();
    }

    var onSearch = debounce(search, 500);

</script>
