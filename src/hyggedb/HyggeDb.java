package hyggedb;

import app.model.repository.FrameRepository;
import app.model.repository.OrderRepository;
import app.model.repository.Repository;
import hyggedb.exception.UnknownRepositoryException;
import hyggedb.insert.QueryExecutor;
import hyggedb.select.SelectionExecutor;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class HyggeDb {
    private final Connector dbConnector;

    public HyggeDb() throws Exception {
        dbConnector = new MySQLConnector();
    }

    public void close() {
        dbConnector.close();
    }

    public SelectionExecutor getSelectionExecutor() {
        return new SelectionExecutor(dbConnector.getConnection());
    }
    public QueryExecutor getInsertionExecutor() {
        return new QueryExecutor(dbConnector.getConnection());
    }

    public Repository getRepository(String repository) throws UnknownRepositoryException {
        switch (repository.toLowerCase()) {
            case "frame":
                return new FrameRepository(getSelectionExecutor(),getInsertionExecutor());
            case "order":
                return new OrderRepository(getSelectionExecutor(),getInsertionExecutor());
            default:
                throw new UnknownRepositoryException();
        }
    }
}
