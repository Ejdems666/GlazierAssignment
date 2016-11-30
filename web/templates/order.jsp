<%@ page import="app.model.entity.Frame" %>
<h1>New order</h1>
<form method="post">
    <div class="row">
        <div class="col-sm-4">
            <label for="width">Width:</label>
            <input id="width" class="form-control" type="number" name="width" required>
        </div>
        <div class="col-sm-4">
            <label for="height">Height:</label>
            <input id="height" class="form-control" type="number" name="height" required>
        </div>
        <div class="col-sm-4">
            <label for="unit">Frame type:</label>
            <select id="unit" class="form-control" type="unit" name="unit" required>
                <option value="CM">cm</option>
                <option value="INCH">inches</option>
            </select>
        </div>
        <div class="col-sm-12">
            <label for="frame">Unit:</label>
            <select id="frame" class="form-control" type="frame" name="frame" required>
                <% for(Frame frame : ((Frame[]) request.getAttribute("frames"))) { %>
                    <option value="<%=frame.getId()%>"><%=frame.getName()%>, <%=frame.getPrice()%> DKK</option>
                <% } %>
            </select>

            <label for="amount">Amount:</label>
            <input id="amount" class="form-control" type="number" name="amount" value="1" required>
        </div>
    </div>
    <button>Order</button>
</form>