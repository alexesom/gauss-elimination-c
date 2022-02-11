package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RbtTreeTest {

    @Test
    public void should_InsertCorrectly_WhenNotExist() {
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        rbt.put("soooos", "yeap");
        rbt.put("uberEats", "no");

        String apples = rbt.get("soooos");
        assertEquals("yeap", apples);

        String potatoes = rbt.get("uberEats");
        assertEquals("no", potatoes);
    }


    @Test
    public void getPreOrderTest_WhenFilled() {
        RedBlackTree<String, String> rbt = getFilledRbt(30);
        System.out.println("Pre-order: \n");
        System.out.println(rbt.getPreOrder() + "\n");
    }

    @Test
    public void getInOrderTest_WhenFilled() {
        RedBlackTree<String, String> rbt = getFilledRbt(30);
        System.out.println("In-order: \n");
        System.out.println(rbt.getInOrder() + "\n");
    }

    @Test
    public void getPostOrderTest_WhenFilled() {
        RedBlackTree<String, String> rbt = getFilledRbt(30);
        System.out.println("Post-order: \n");
        System.out.println(rbt.getPostOrder() + "\n");
    }

    @Test
    public void shoudl_returnCorrectValueAfterDeleteMax_WhenFilled() {
        RedBlackTree<String, String> rbt = getFilledRbt(30);

        for (int i = 0; i < 30; i++) {
            System.out.println(rbt.getInOrder());

            String[] elems = rbt.getInOrder().split(" ");
            String[] str = elems[elems.length - 1].split(":");
            String maxKey = str[str.length > 1 ? 1 : 0].strip();

            rbt.deleteMax();
            String s = rbt.get(maxKey);

            assertNull(s);
        }
        System.out.println("\n");
    }

    private RedBlackTree<String, String> getFilledRbt(int size) {
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        for (int i = 1; i < size+1; i++)
            rbt.put("key" + i, "value" + i);

        return rbt;
    }
}