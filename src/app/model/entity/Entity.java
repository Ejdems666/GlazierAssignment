package app.model.entity;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class Entity implements IEntity{
    protected int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
