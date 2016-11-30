package app.servlet;

import app.component.BootstrapAlert;
import app.exception.ParameterException;
import app.model.facade.OrderFacade;
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
@WebServlet(name = "Order")
public class Order extends Servlet {
    Repository frameRepository;
    HyggeDb hyggeDb;

    public void init() {
        hyggeDb = createDbConnection();
        frameRepository = hyggeDb.getRepository("frame");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository orderRepository = hyggeDb.getRepository("order");
        OrderFacade orderFacade = new OrderFacade(orderRepository,frameRepository);
        try {
            app.model.entity.Order order = orderFacade.createOrder(processParameters(request));
            request.setAttribute("order",order);
            request.setAttribute("frame",frameRepository.getById(order.getFrame()));
            request.setAttribute("title","Order "+order.getId()+" processed");
            renderTemplate("order-processed",request,response);
        } catch (ParameterException e) {
            request.setAttribute("alert",new BootstrapAlert("error",e.getMessage()));
            renderTemplate(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("frames",frameRepository.findAll());
        renderTemplate(request,response);
    }
}
