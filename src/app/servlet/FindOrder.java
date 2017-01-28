package app.servlet;

import app.component.BootstrapAlert;
import app.model.repository.Repository;
import hyggedb.HyggeDb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ejdems on 26/11/2016.
 */
@WebServlet(name = "FindOrder")
public class FindOrder extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderTemplate("find-order",request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        if (request.getParameter("find") != null) {
            processSearch(request);
        }
        renderTemplate("find-order",request,response);
    }

    private void processSearch(HttpServletRequest request) {
        HyggeDb hyggeDb = createDbConnection();
        Repository orderRepository = hyggeDb.getRepository("order");
        Repository frameRepository = hyggeDb.getRepository("frame");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        app.model.entity.Order order = (app.model.entity.Order) orderRepository.getById(orderId);
        if (order == null) {
            request.setAttribute("alert",new BootstrapAlert("danger","No order with such id"));
        } else {
            request.setAttribute("order", order);
            request.setAttribute("frame", frameRepository.getById(order.getFrame()));
        }
    }
}
