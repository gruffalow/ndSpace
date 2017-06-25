package gruffalow.nds.factories;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Created by pete on 25/06/2017.
 */
public class NDSEngineFactoryTest {

    @Test
    public void testEngineDelivered() {
        assertNotNull(NDSEngineFactory.create());
    }

}
