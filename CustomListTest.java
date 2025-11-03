import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CustomListTest {

    private CSArrayList<Integer> list;
    private CSArrayList<String> stringList;

    @BeforeEach
    void setUp() {
        list = new CSArrayList<>();
        stringList = new CSArrayList<>();
    }

    @Nested
    @DisplayName("Edge Index Cases")
    class EdgeIndexTests {

        @BeforeEach
        void setup() {
            list.add(10);
            list.add(20);
            list.add(30);
        }

        @Test
        @DisplayName("Get element at index 0")
        void getAtIndexZero() {
            assertEquals(10, list.getFirst()); // Element at index 0 should be 10
        }

        @Test
        @DisplayName("Get element at last index (size-1)")
        void getAtLastIndex() {
            int lastIndex = list.size() - 1;
            assertEquals(30, list.get(lastIndex)); // Element at last index should be 30
        }

        @Test
        @DisplayName("Attempt to get element at index 'size' should throw IndexOutOfBoundsException")
        void getAtIndexSize() {
            int size = list.size();
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(size)); // Getting element at index 'size' should throw IndexOutOfBoundsException
        }

        @Test
        @DisplayName("Attempt to get element at negative index should throw IndexOutOfBoundsException")
        void getAtNegativeIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1)); // Getting element at negative index should throw IndexOutOfBoundsException
        }
    }

    @Test
    @DisplayName("Multiple resizes after appending 1e4 items")
    void multipleResizes() {
        int numItems = 10000;
        for (int i = 0; i < numItems; i++) {
            list.add(i);
        }

        assertEquals(numItems, list.size()); //Getting element at negative index should throw IndexOutOfBoundsException
        assertEquals(0, list.getFirst()); // Element at index 0 should be 0
        assertEquals(5000, list.get(5000)); // Element at index 5000 should be 5000
        assertEquals(9999, list.get(numItems - 1)); // Element at last index should be 9999
    }

    @Nested
    @DisplayName("Searches with Duplicates and Nulls")
    class SearchTests {
        @BeforeEach
        void setup() {
            stringList.add("numberOne");
            stringList.add("numberTwo");
            stringList.add("numberOne"); // Duplicate
            stringList.add(null);   // Null
            stringList.add("numberThree");
            stringList.add(null);   // Duplicate null
        }

        @Test
        @DisplayName("Search for a present duplicate item")
        void searchForDuplicatePresent() {
            assertTrue(stringList.contains("numberOne")); // List should contain 'numberOne'
            // You could also test an indexOf method if available, to check the first/last index
        }

        @Test
        @DisplayName("Search for a present null value")
        void searchForPresentNull() {
            assertTrue(stringList.contains(null)); // List should contain null
        }

        @Test
        @DisplayName("Search for an absent item")
        void searchForAbsentItem() {
            assertFalse(stringList.contains("numberFour")); // List shouldn't contain 'numberFour'
        }

        @Test
        @DisplayName("Search for an absent null value in a list with no nulls")
        void searchForAbsentNullInNoNullList() {
            CSArrayList<String> noNullList = new CSArrayList<>();
            noNullList.add("only strings");
            assertFalse(noNullList.contains(null), "List without nulls should not contain null");
        }
    }

    @Nested
    @DisplayName("remove(Object) behavior")
    class RemoveObjectTests {
        @BeforeEach
        void setup() {
            stringList.add("first");
            stringList.add("second");
            stringList.add("third");
        }

        @Test
        @DisplayName("Remove a present object")
        void removePresentObject() {
            assertTrue(stringList.contains("second")); // Item should be present before removal
            assertTrue(stringList.remove("second")); // remove should return true when object is present
            assertEquals(2, stringList.size()); // List size should decrease after removal
            assertFalse(stringList.contains("second")); // Item should be absent after removal
            assertEquals("third", stringList.get(1)); // Subsequent elements should shift left
        }

        @Test
        @DisplayName("Remove an absent object")
        void removeAbsentObject() {
            assertFalse(stringList.contains("absent")); // Item should be absent
            assertFalse(stringList.remove("absent")); // remove should return false when object is absent
            assertEquals(3, stringList.size()); // List size should remain unchanged
        }
    }

    @Test
    @DisplayName("Fail-fast iterator checks")
    void failFastIteratorChecks() {
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();


        list.add(4);


        assertThrows(ConcurrentModificationException.class, iterator::next); // Iterator should throw ConcurrentModificationException after list modification
    }
}
