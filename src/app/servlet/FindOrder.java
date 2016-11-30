package app.servlet;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderTemplate("find-order",request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        HyggeDb hyggeDb = createDbConnection();
        Repository orderRepository = hyggeDb.getRepository("order");
        Repository frameRepository = hyggeDb.getRepository("frame");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        app.model.entity.Order order = (app.model.entity.Order) orderRepository.getById(orderId);
        request.setAttribute("order",order);
        request.setAttribute("frame",frameRepository.getById(order.getFrame()));
        renderTemplate("find-order",request,response);
    }
}
