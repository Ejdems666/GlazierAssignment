import app.model.entity.Order;
import app.model.facade.OrderFacade;
import app.model.repository.Repository;
import hyggedb.HyggeDb;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by Ejdems on 27/11/2016.
 */
public class CalculationTestCase extends TestCase {
    public void testCalculation() throws Exception {
        HyggeDb hyggeDb = new HyggeDb();
        Repository orderRepository = hyggeDb.getRepository("order");
        Repository frameRepository = hyggeDb.getRepository("frame");
        OrderFacade orderFacade = new OrderFacade(orderRepository,frameRepository);
        HashMap<String,String> parameters = new HashMap();
        parameters.put("width","100");
        parameters.put("height","160");
        parameters.put("unit","CM");
        parameters.put("amount","1");
        parameters.put("frame","1");
        Order order = orderFacade.createOrder(parameters);
        assertEquals(1000, ((int) order.getPrice()));
    }
}
