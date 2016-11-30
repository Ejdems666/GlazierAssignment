package app.model.repository;

import app.model.entity.IEntity;
import app.model.entity.Order;
import hyggedb.insert.QueryExecutor;
import hyggedb.select.Condition;
import hyggedb.select.Selection;
import hyggedb.select.SelectionExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class OrderRepository implements Repository {
    private final SelectionExecutor selector;
    private final QueryExecutor insertor;
    private ArrayList<Order> identityMap = new ArrayList<>();
    private ArrayList<Order> persistedEntities = new ArrayList<>();

    public OrderRepository(SelectionExecutor selector, QueryExecutor insertor) {
        this.selector = selector;
        this.insertor = insertor;
    }

    @Override
    public Order getById(int id) {
        Selection selection = getBaseSelection();
        selection.where("id=?",id);
        return mapOrder(selector.getResult(selection));
    }

    private Selection getBaseSelection() {
        return new Selection("order");
    }

    @Override
    public Order getBy(Condition condition) {
        Selection selection = getBaseSelection();
        selection.setWhere(condition);
        return mapOrder(selector.getResult(selection));
    }

    @Override
    public Order[] findBy(Condition condition) {
        Selection selection = getBaseSelection();
        selection.setWhere(condition);
        return mapOrders(selector.getResult(selection));
    }

    @Override
    public IEntity[] findAll() {
        Selection selection = getBaseSelection();
        return mapOrders(selector.getResult(selection));
    }

    private Order mapOrder(ResultSet resultSet) {
        try {
            if(resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setWidth(resultSet.getFloat("width"));
                order.setHeight(resultSet.getFloat("height"));
                order.setAmount(resultSet.getInt("amount"));
                order.setUnit(resultSet.getString("unit"));
                order.setPrice(resultSet.getDouble("price"));
                order.setCreatedAt(resultSet.getDate("created_at"));
                order.setFrame(resultSet.getInt("frame_id"));
                identityMap.add(order);
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order[] mapOrders(ResultSet orderData) {
        ArrayList<Order> orders = new ArrayList<>();
        Order order = mapOrder(orderData);
        while (order != null){
            orders.add(order);
            order = mapOrder(orderData);
        }
        return orders.toArray(new Order[orders.size()]);
    }

    @Override
    public void persist(IEntity entity) {
        persistedEntities.add(((Order) entity));
    }

    @Override
    public void flush() {
        Object[] objects;
        String sql;
        int id;
        for (Order persistedEntity : persistedEntities) {
            if(!identityMap.contains(persistedEntity)) {
                sql = "INSERT INTO `order`(`width`, `height`, `amount`, `unit`, `price`, `created_at`, `frame_id`)" +
                        " VALUES (?,?,?,?,?,?,?)";
                objects = new Object[7];
                objects[0] = persistedEntity.getWidth();
                objects[1] = persistedEntity.getHeight();
                objects[2] = persistedEntity.getAmount();
                objects[3] = persistedEntity.getUnit();
                objects[4] = persistedEntity.getPrice();
                objects[5] = persistedEntity.getCreatedAt();
                objects[6] = persistedEntity.getFrame();
                id = insertor.insert(sql,objects);
                persistedEntity.setId(id);
            } else {
                sql = "UPDATE order SET width=?,height=?,amount=?,unit=?,price=?,created_at=?,frame_id=? WHERE id=?";
                objects = new Object[8];
                objects[0] = persistedEntity.getWidth();
                objects[1] = persistedEntity.getHeight();
                objects[2] = persistedEntity.getAmount();
                objects[3] = persistedEntity.getUnit();
                objects[4] = persistedEntity.getPrice();
                objects[5] = persistedEntity.getCreatedAt();
                objects[6] = persistedEntity.getFrame();
                objects[7] = persistedEntity.getId();
                insertor.update(sql,objects);
            }
        }
    }

    @Override
    public void persistAndFlush(IEntity entity) {
        persist(entity);
        flush();
    }
}