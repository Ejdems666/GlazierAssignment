<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>${title}</title>
        <link rel="stylesheet" href="assets/css/style.css">
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Glazier assignment</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li <% if (request.getAttribute("template").equals("homepage")) {out.print("class='active'");}%>>
                        <a href="/">Homepage</a>
                    </li>
                    <li <% if (request.getAttribute("template").equals("order")) {out.print("class='active'");}%>>
                        <a href="/order">New order</a>
                    </li>
                    <li <% if (request.getAttribute("template").equals("find-order")) {out.print("class='active'");}%>>
                        <a href="/find-order">Find your order</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <% if (request.getAttribute("alert") != null ) {%>
        ${alert}
    <% }%>
    <div class="container">
        <jsp:include page="templates/${template}.jsp" />
    </div>
    </body>
</html>
