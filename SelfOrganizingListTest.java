import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelfOrganizingListTest {
    DoubleLinkedList<String> list;

    @Before
    public void setUp()  {
        list = new SelfOrganizingList<String>();
    }

    public void populateList() {
        list.add("Will");
        list.add("Dustin");
        list.add("Lucas");
        list.add("Mike");
        list.add("Eleven");
        list.add("Eleven");
    }

    @Test
    public void add() throws Exception {
        populateList();
        assertEquals("Eleven", list.remove(1));
    }

    @Test
    public void contains() throws Exception {
        populateList();
        assertTrue(list.contains("Eleven"));
    }

    @Test
    public void contains1() throws Exception {
        populateList();
        assertFalse(list.contains("Hello"));
    }

    @Test
    public void contains2() throws Exception {
        populateList();
        list.contains("Eleven");
        assertEquals("Eleven", list.remove(1));
    }

    @Test
    public void getEntry() throws Exception {
        populateList();
        list.contains("Eleven");
        assertEquals("Eleven", list.getEntry(1));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void add1() throws Exception {
        populateList();
        list.contains("Eleven");
        list.add(3, "John");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void replace() throws Exception {
        populateList();
        list.contains("Eleven");
        list.replace(3, "John");
    }

}