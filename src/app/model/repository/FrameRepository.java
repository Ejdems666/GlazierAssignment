package app.model.repository;

import app.model.entity.Frame;
import app.model.entity.IEntity;
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
public class FrameRepository implements Repository{
    private final SelectionExecutor selector;
    private final QueryExecutor insertor;
    private ArrayList<Frame> identityMap = new ArrayList<>();
    private ArrayList<Frame> persistedEntities = new ArrayList<>();

    public FrameRepository(SelectionExecutor selector, QueryExecutor insertor) {
        this.selector = selector;
        this.insertor = insertor;
    }

    @Override
    public Frame getById(int id) {
        Selection selection = getBaseSelection();
        selection.where("id=?",id);
        return mapFrame(selector.getResult(selection));
    }

    private Selection getBaseSelection() {
        return new Selection("frame");
    }

    @Override
    public Frame getBy(Condition condition) {
        Selection selection = getBaseSelection();
        selection.setWhere(condition);
        return mapFrame(selector.getResult(selection));
    }

    @Override
    public Frame[] findBy(Condition condition) {
        Selection selection = getBaseSelection();
        selection.setWhere(condition);
        return mapFrames(selector.getResult(selection));
    }

    @Override
    public Frame[] findAll() {
        Selection selection = getBaseSelection();
        return mapFrames(selector.getResult(selection));
    }

    private Frame mapFrame(ResultSet resultSet) {
        try {
            if(resultSet.next()) {
                Frame frame = new Frame();
                frame.setId(resultSet.getInt("id"));
                frame.setName(resultSet.getString("name"));
                frame.setPrice(resultSet.getDouble("price"));
                identityMap.add(frame);
                return frame;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Frame[] mapFrames(ResultSet frameData) {
        ArrayList<Frame> frames = new ArrayList<>();
        Frame frame = mapFrame(frameData);
        while (frame != null){
            frames.add(frame);
            frame = mapFrame(frameData);
        }
        return frames.toArray(new Frame[frames.size()]);
    }

    @Override
    public void persist(IEntity entity) {
        persistedEntities.add(((Frame) entity));
    }

    @Override
    public void flush() {
        Object[] objects = new Object[3];
        String sql;
        for (Frame persistedEntity : persistedEntities) {
            if(!identityMap.contains(persistedEntity)) {
                sql = "INSERT INTO frame(id,name,price) VALUES(?,?,?)";
                objects[0] = persistedEntity.getId();
                objects[1] = persistedEntity.getName();
                objects[2] = persistedEntity.getPrice();
            } else {
                sql = "UPDATE frame SET name=?,price=? WHERE id=?";
                objects[0] = persistedEntity.getName();
                objects[1] = persistedEntity.getPrice();
                objects[2] = persistedEntity.getId();
            }
            insertor.insert(sql,objects);
        }
    }

    @Override
    public void persistAndFlush(IEntity entity) {
        persist(entity);
        flush();
    }
}
