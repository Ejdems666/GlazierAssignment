package app.servlet;

import hyggedb.HyggeDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class Servlet extends HttpServlet {
    protected void renderTemplate(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        renderTemplate(this.getServletName().toLowerCase(),request,response);
    }

    protected void renderTemplate(String template, HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("template",template);
        if(request.getAttribute("title") == null) {
            request.setAttribute("title",template);
        }
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }

    protected HashMap<String,String> processParameters(HttpServletRequest request) {
        HashMap<String,String> parameters = new HashMap<>();
        for (String parameterName : request.getParameterMap().keySet()) {
            parameters.put(parameterName,request.getParameter(parameterName));
        }
        return parameters;
    }

    protected HyggeDb createDbConnection() {
        try {
            return new HyggeDb();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
