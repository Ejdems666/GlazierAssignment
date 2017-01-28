<h1>Find order</h1>
<form method="get">
    <label for="orderId">Order id:</label>
    <input id="orderId" class="form-control" type="number" name="orderId" required>
    <button name="find">Find</button>
</form>
<% if(request.getAttribute("order") != null){ %>
    <jsp:include page="order-data.jsp" />
<% }%>