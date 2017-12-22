import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DoubleLinkedListTest {
    DoubleLinkedList<String> list;

    @Before
    public void setUp()  {
        list = new DoubleLinkedList<>();
    }

    public void populateList() {
        list.add("Will");
        list.add("Dustin");
        list.add("Lucas");
        list.add("Mike");
        list.add("Eleven");
    }

    @Test
    // Test add to an empty list
    public final void testAddT0() {
        list.add("Barb");
        assertNotNull(list.getLast());;
    }

    @Test
    // Test add to an empty list
    public final void testAddT1() {
        list.add("Barb");
        assertTrue(list.contains("Barb") && list.getLength()==1);
    }

    @Test
    // Test add to end of non-empty list
    public final void testAddT2() {
        populateList();
        list.add("Barb");
        assertTrue(list.contains("Barb") && list.getLength()==6);
    }

    @Test
    // Test add to end of non-empty list
    public final void testAddT3() {
        populateList();
        list.add("Barb");
        assertNotNull(list.getNodeAt(6).getPreviousNode());
    }

    @Test
    // Test add to end of non-empty list
    public final void testAddT4() {
        populateList();
        list.add("Barb");
        assertTrue(list.getLast()==list.getNodeAt(6));
    }

    @Test
    // Add to first position of an empty list
    public final void testAddIntT1a() {
        list.add(1, "Barb");
        assertTrue(list.contains("Barb"));
    }

    @Test
    // Add to first position of an empty list
    public final void testAddIntT1b() {
        list.add(1, "Barb");
        assertTrue(list.getFirst()==list.getNodeAt(1));
    }

    @Test
    // Add to first position of an empty list
    public final void testAddIntT1c() {
        list.add(1, "Barb");
        assertNotNull(list.getLast());
    }

    @Test
    // Add to first position of an non-empty list
    public final void testAddIntT2a() {
        populateList();
        list.add(1, "Barb");
        assertTrue(list.getNodeAt(2).getPreviousNode() != null);
    }

    @Test
    // Add to first position of an non-empty list
    public final void testAddIntT2b() {
        populateList();
        list.add(1, "Barb");
        assertTrue(list.getNodeAt(1) == list.getFirst());
    }

    @Test
    // Add to third position of an non-empty list
    public final void testAddIntT3a() {
        populateList();
        list.add(3, "Barb");
        assertNotNull(list.getNodeAt(3).getPreviousNode());
    }

    @Test
    // Add to third position of an non-empty list
    public final void testAddIntT3b() {
        populateList();
        list.add(3, "Barb");
        assertEquals(list.getNodeAt(4).getPreviousNode(), list.getNodeAt(3));

    }

    @Test
    // Add to last position of an non-empty list
    public final void testAddIntT4() {
        populateList();
        list.add(6, "Barb");
        assertNull(list.getLast().getNextNode());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Add to illegal position of an empty list
    public final void testAddIntT5() {
        populateList();
        list.add(0, "Barb");
        assertNull(list.getLast().getNextNode());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Add to illegal position of an non-empty list
    public final void testAddIntT6() {
        populateList();
        list.add(10, "Barb");
        assertNull(list.getLast().getNextNode());
    }

    @Test
    // Remove from front
    public final void testRemove1a() {
        populateList();
        assertEquals("Will", list.remove(1));
    }

    @Test
    // Remove from front
    public final void testRemove1b() {
        populateList();
        list.remove(1);
        assertNull(list.getFirst().getPreviousNode());
    }

    @Test
    // Remove last node
    public final void testRemove2() {
        populateList();
        assertEquals("Eleven", list.remove(5));
    }

    @Test
    // Remove from the middle
    public final void testRemove3a() {
        populateList();
        assertEquals("Lucas", list.remove(3));
    }

    @Test
    // Remove from the middle
    public final void testRemove3b() {
        populateList();
        list.remove(3);
        assertEquals("Dustin", ((list.getNodeAt(3)).getPreviousNode()).getData());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Remove too small index
    public final void testRemove4() {
        populateList();
        assertEquals("Will", list.remove(0));
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Remove too large index
    public final void testRemove5() {
        populateList();
        assertEquals("Will", list.remove(8));
    }

    @Test
    public final void testClear() {
        populateList();
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public final void testReplace1() {
        populateList();
        list.add("Barb");
        list.replace(6, "Nothing");
        assertEquals(list.getEntry(6), "Nothing");
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public final void testReplace2() {
        list.add("Barb");
        list.replace(6, "Nothing");
        assertEquals(list.getEntry(6), "Nothing");
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public final void testReplace3() {
        list.replace(1, "Nothing");
        assertEquals(list.getEntry(1), "Nothing");
    }

    @Test
    // Position is close to front
    public final void testGetEntry1() {
        populateList();
        assertEquals(list.getEntry(2), "Dustin");
    }

    @Test
    // Position is close to back
    public final void testGetEntry2() {
        populateList();
        assertEquals(list.getEntry(5), "Eleven");
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Position is too large
    public final void testGetEntry3() {
        populateList();
        assertEquals(list.getEntry(6), "Eleven");
    }

    @Test (expected=IndexOutOfBoundsException.class)
    // Position is too small
    public final void testGetEntry4() {
        populateList();
        assertEquals(list.getEntry(0), "Dustin");
    }

    @Test
    // Empty list
    public final void testToArray1() {
        String[] names = new String[0];
        assertArrayEquals(names, list.toArray());
    }

    @Test
    // Populated list
    public final void testToArray2() {
        populateList();
        String[] names = {"Will", "Dustin", "Lucas", "Mike", "Eleven"};
        assertArrayEquals(names, list.toArray());
    }

    @Test
    // Empty list
    public final void testContains1() {
        assertFalse(list.contains("Eleven"));
    }
    @Test
    // Populated list
    public final void testContains2() {
        populateList();
        assertTrue(list.contains("Eleven"));
    }
    @Test
    // Added to list
    public final void testContains3() {
        populateList();
        list.add("Barb");
        assertTrue(list.contains("Barb"));
    }
    @Test
    // Not added to list
    public final void testContains4() {
        populateList();
        assertFalse(list.contains("Barb"));
    }

    @Test
    // Empty list
    public final void testGetLength1() {
        assertEquals(0, list.getLength());
    }

    @Test
    // After adding one node
    public final void testGetLength2() {
        list.add("Barb");
        assertEquals(1, list.getLength());
    }

    @Test
    // After adding several nodes
    public final void testGetLength3() {
        populateList();
        assertEquals(5, list.getLength());
    }

    @Test
    // After adding into a position
    public final void testGetLength4() {
        populateList();
        list.add(4, "Jonathan");
        assertEquals(6, list.getLength());
    }

    @Test
    // Test before add anything
    public final void testIsEmpty1() {
        assertTrue(list.isEmpty());
    }

    @Test
    // Test after adding one node to empty list
    public final void testIsEmpty2() {
        list.add("Barb");
        assertFalse(list.isEmpty());
    }

    @Test
    // Test after adding nodes to list
    public final void testIsEmpty3() {
        populateList();
        assertFalse(list.isEmpty());
    }

    @Test
    // Test after deleting a only one
    public final void testIsEmpty4() {
        list.add("Barb");
        list.remove(1);
        assertTrue(list.isEmpty());
    }

    @Test
    // Test after a call to clear
    public final void testIsEmpty5() {
        populateList();
        list.clear();
        assertTrue(list.isEmpty());
    }

}

