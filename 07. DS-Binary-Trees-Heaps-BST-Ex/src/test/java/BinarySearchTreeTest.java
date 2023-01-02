
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @Before
    public void beforeEach() {
        bst = new BinarySearchTree<>(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(1);
        bst.insert(17);
    }

    @Test
    public void testCreate() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }

    @Test
    public void testEachInOrder() {
        List<Integer> elements = new ArrayList<>();
        bst.eachInOrder(e -> elements.add(e));

        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());
    }

    @Test
    public void testGetRoot() {
    }

    @Test
    public void testInsert() {
        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(1), bst.getRoot().getLeft().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());
        assertEquals(Integer.valueOf(17), bst.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void testContainsTrue() {
        assertTrue(bst.contains(6));
    }

    @Test
    public void testContainsFalse() {
        assertFalse(bst.contains(8));

    }

    @Test
    public void testSearchTrue() {
        BinarySearchTree<Integer> search = bst.search(7);
        bst.insert(8);

        assertEquals(Integer.valueOf(7), search.getRoot().getValue());
        assertEquals(Integer.valueOf(6), search.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(17), search.getRoot().getRight().getValue());

        assertTrue(bst.contains(8));
        assertFalse(search.contains(8));

    }

    @Test
    public void testSearchFalse() {
        assertNull(bst.search(8));

    }


    @Test
    public void testRange() {
        List<Integer> range = bst.range(3, 7);
        List<Integer> expected = Arrays.asList(3, 5, 6, 7);

        assertEquals(4, range.size());

        for (Integer value : range) {
            assertTrue(expected.contains(value));
        }
    }

    @Test
    public void testDeleteMin() {
        assertTrue(bst.contains(1));
        bst.deleteMin();
        assertFalse(bst.contains(1));
    }

    @Test
    public void testDeleteMax() {
        assertTrue(bst.contains(17));
        bst.deleteMax();
        assertFalse(bst.contains(17));
    }

    @Test
    public void testCount() {
        assertEquals(6, bst.count());
    }

    @Test
    public void testCountAfterInsert() {
        bst.insert(11);
        assertEquals(7, bst.count());
    }

    @Test
    public void testCountAfterDeleteMin() {
        bst.deleteMax();
        assertEquals(5, bst.count());
    }

    @Test
    public void testCountAfterDeleteMax() {
        bst.deleteMax();
        assertEquals(5, bst.count());
    }

    @Test
    public void testRank() {
        assertEquals(4, bst.rank(7));
    }

    @Test
    public void testRankMinElement() {
        assertEquals(0, bst.rank(-1));
    }

    @Test
    public void testRankEmptyTree() {
        assertEquals(0, new BinarySearchTree<Integer>().rank(1));
    }

    @Test
    public void testCeil() {
        assertEquals(Integer.valueOf(17), bst.ceil(7));
    }

    @Test
    public void testEmptyCeil() {
        assertNull(bst.ceil(20));
    }

    @Test
    public void testFloor() {
        assertEquals(Integer.valueOf(6), bst.floor(7));
    }

    @Test
    public void testEmptyFloor() {
        assertNull(bst.floor(-1));
    }

    @Test
    public void testDeleteMax1_shouldWorkCorrectly() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(1);
        bst.insert(8);
        bst.insert(18);
        bst.insert(23);

        bst.deleteMax();

        BinarySearchTree.Node<Integer> right_node = bst.getRoot().getRight().getRight();
        Assert.assertEquals(null, right_node);

        BinarySearchTree.Node<Integer> left_node = bst.getRoot().getRight().getLeft();
        Assert.assertEquals(Integer.valueOf(18), left_node.getValue());
    }

    @Test
    public void testDeleteMax2_shouldWorkCorrectly() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(1);
        bst.insert(8);
        bst.insert(18);

        bst.deleteMax();

        BinarySearchTree.Node<Integer> right_node = bst.getRoot().getRight();
        Assert.assertEquals(Integer.valueOf(18), right_node.getValue());
    }

    @Test
    public void testCeil4_shouldWorkCorrectly() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Integer ceil = bst.ceil(9);
        Assert.assertNull(ceil);
        bst.insert(9);
        Assert.assertEquals(1, bst.count());
    }
    @Test
    public void testFloor1_shouldWorkCorrectly() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(1);
        bst.insert(8);
        bst.insert(18);
        bst.insert(23);

        Assert.assertEquals(7, bst.count());

        Integer floor = bst.floor(0);
        Assert.assertNull(floor);
    }
    @Test
    public void testDelete_deleteRoot_setInorderSuccessor() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(1);
        bst.insert(8);
        bst.insert(18);
        bst.insert(17);
        bst.insert(16);
        bst.insert(23);

        BinarySearchTree.Node<Integer> root = bst.getRoot();
        Assert.assertEquals(Integer.valueOf(12), root.getValue());
        Assert.assertEquals(9, bst.count());
    }
    @Test
    public void testRank_withValue_lowerThanLowest_returnZero() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(8);
        bst.insert(1);
        bst.insert(18);
        bst.insert(23);

        Assert.assertEquals(7, bst.count());

        int rank = bst.rank(-1);
        Assert.assertEquals(0, rank);
    }
}