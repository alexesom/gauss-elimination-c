package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.MapInterface;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class RbtMapTest {
    @Test
    public void setValueTest_WhenNotExist() {
        try {
            MapInterface<String, String> rbtMap = new RbtMap<>();
            rbtMap.setValue("some-key", "some-value");
            Field tree = RbtMap.class.getDeclaredField("tree");
            tree.setAccessible(true);

            RedBlackTree<String, String> redBlackTree = (RedBlackTree<String, String>) tree.get(rbtMap);
            String valueByKey = redBlackTree.get("some-key");
            assertNotNull(valueByKey);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getValueTest_WhenNotExist() {
        MapInterface<String, String> rbtMap =  new RbtMap<>();
        rbtMap.setValue("some-key", "some-value");
        String value = rbtMap.getValue("some-key");
        String incorrectValueIn = rbtMap.getValue("some-incorrect-key");
        assertEquals("some-value", value);
        assertNull(incorrectValueIn);
    }
}
