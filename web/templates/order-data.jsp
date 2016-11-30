<%@ page import="app.model.entity.Order" %>
<%@ page import="app.model.entity.Frame" %>
<% Order order = ((Order) request.getAttribute("order")); %>
<ul>
    <li>Order id: <%=order.getId()%></li>
    <li>Height: <%=order.getHeight()+" "+order.getUnit()%></li>
    <li>Width: <%=order.getWidth()+" "+order.getUnit()%></li>
    <li>Frame type: <%=((Frame) request.getAttribute("frame")).getName()%></li>
    <li>Amount: <%=order.getAmount()%></li>
    <li>Final price: <%=order.getPrice()%></li>
</ul>