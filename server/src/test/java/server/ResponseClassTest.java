package server;

import org.junit.Assert;
import org.junit.Test;
import server.envy.me.response.ResponseClass;

/**
 * Unit test for ResponseClass - Class that creates a response object.
 */
public class ResponseClassTest {
    /**
     * Testing constructor.
     */
    @Test
    public void testingConstructor() {
        ResponseClass newResponse = new ResponseClass("payload");
        Assert.assertNotEquals(newResponse, new ResponseClass("payload 2"));
    }

    /**
     * Getter testing positive.
     */
    @Test
    public void testingGetterPositive() {
        ResponseClass newResponse = new ResponseClass("payload");
        Assert.assertEquals(newResponse.getPayload(), "payload");
    }

    /**
     * Getter testing Negative.
     */
    @Test
    public void testingGetterNegative() {
        ResponseClass newResponse = new ResponseClass("payload");
        Assert.assertNotEquals(newResponse.getPayload(), "not equal");
    }

    /**
     * Positive setter testing.
     */
    @Test
    public void testingSetterPositive() {
        ResponseClass newResponse = new ResponseClass("payload");
        newResponse.setPayload("New Payload");
        Assert.assertEquals(newResponse.getPayload(), "New Payload");
    }

    /**
     * Negative setter testing.
     */
    @Test
    public void testingSetterNegative() {
        ResponseClass newResponse = new ResponseClass("payload");
        newResponse.setPayload("New Payload");
        Assert.assertNotEquals(newResponse.getPayload(), "payload");
    }
}
