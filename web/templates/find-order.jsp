<h1>Find order</h1>
<form method="post">
    <label for="orderId">Order id:</label>
    <input id="orderId" class="form-control" type="number" name="orderId" required>
    <button>Find</button>
</form>
<% if(request.getAttribute("order") != null){ %>
    <jsp:include page="order-data.jsp" />
<% }%>