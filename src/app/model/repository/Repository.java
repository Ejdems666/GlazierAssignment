package app.model.repository;

import app.model.entity.IEntity;
import hyggedb.select.Condition;

/**
 * Created by Ejdems on 26/11/2016.
 */
public interface Repository {
    public IEntity getById(int id);

    public IEntity getBy(Condition condition);

    public IEntity[] findBy(Condition condition);

    public IEntity[] findAll();

    public void persist(IEntity entity);

    public void flush();

    public void persistAndFlush(IEntity entity);
}
