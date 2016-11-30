package app.model.facade;


import app.exception.NumberParameterException;
import app.exception.ParameterException;
import app.exception.SelectionParameterException;
import app.model.entity.Frame;
import app.model.entity.Order;
import app.model.repository.Repository;

import java.sql.Date;
import java.util.HashMap;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class OrderFacade {
    private final double GLASS_PRICE = 0.03;
    private HashMap<String,String> parameters;
    private final Repository orderRepository;
    private final Repository frameRepository;

    public OrderFacade(Repository orderRepository, Repository frameRepository) {
        this.orderRepository = orderRepository;
        this.frameRepository = frameRepository;
    }

    public Order createOrder(HashMap<String,String> parameters) throws ParameterException {
        this.parameters = parameters;
        int width = parseNumberParameter("width");
        int height = parseNumberParameter("height");
        int amount = parseNumberParameter("amount");
        double glassSize = getGlassSizeInSquareMeters(parameters, width, height);
        double frameLength = getFrameLengthInMeters(width, height);
        Frame frame = ((Frame) frameRepository.getById(parseNumberParameter("frame")));
        double price = glassSize * GLASS_PRICE + frameLength*(frame.getPrice()/100);
        price = price*amount;

        Order order = new Order();
        order.setWidth(width);
        order.setHeight(height);
        order.setAmount(amount);
        order.setUnit(parameters.get("unit"));
        order.setPrice(price);
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setFrame(parseNumberParameter("frame"));
        orderRepository.persistAndFlush(order);
        return order;
    }
    private double getGlassSizeInSquareMeters(HashMap<String, String> parameters, int width, int height)
            throws SelectionParameterException {
        switch (Unit.valueOf(parameters.get("unit"))) {
            case CM:
                return (width*height);
            case INCH:
                return ((width*height)*2.54);
            default:
                throw new SelectionParameterException("unit");
        }
    }
    private double getFrameLengthInMeters(int width, int height) throws SelectionParameterException {
        switch (Unit.valueOf(parameters.get("unit"))) {
            case CM:
                return (width+height)*2;
            case INCH:
                return (width+height)*2.54;
            default:
                throw new SelectionParameterException("unit");
        }
    }


    private int parseNumberParameter(String name) throws NumberParameterException {
        try {
            return Integer.parseInt(parameters.get(name));
        } catch (NumberFormatException e) {
            throw new NumberParameterException(name);
        }
    }

    private enum Unit{
        CM,INCH;
    }
}
