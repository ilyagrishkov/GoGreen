package oop;

import oop.connection.HttpData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpDataTest {


    @Test
    public void test_HttpData() {
        HttpData httpData = new HttpData(200, "ok");
        assertEquals(httpData.getResponseCode(), 200);
        assertEquals(httpData.getData(), "ok");
    }
}
