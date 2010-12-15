package org.apache.tiles.request.collection;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tiles.request.attribute.HasKeys;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link ReadOnlyEnumerationMap#entrySet()}
 *
 * @version $Rev$ $Date$
 */
public class ReadOnlyEnumerationMapEntrySetTest {
    private HasKeys<Integer> extractor;

    private ReadOnlyEnumerationMap<Integer> map;

    private Set<Map.Entry<String, Integer>> entrySet;

    /**
     * Sets up the test.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        extractor = createMock(HasKeys.class);
        map = new ReadOnlyEnumerationMap<Integer>(extractor);
        entrySet = map.entrySet();
    }

    /**
     * Tests {@link Set#add(Object)}.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testAdd() {
        entrySet.add(null);
    }

    /**
     * Tests {@link Set#addAll(Object)}.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testAddAll() {
        entrySet.addAll(null);
    }

    /**
     * Tests {@link Set#clear(Object)}.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testClear() {
        entrySet.clear();
    }

    /**
     * Tests {@link Set#contains(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContains() {
        Map.Entry<String, Integer> entry = createMock(Map.Entry.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(entry.getKey()).andReturn("two");
        expect(entry.getValue()).andReturn(2);

        expect(extractor.getValue("two")).andReturn(2);

        replay(extractor, entry, values2);
        assertTrue(entrySet.contains(entry));
        verify(extractor, entry, values2);
    }

    /**
     * Tests {@link Set#containsAll(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        Map.Entry<String, Integer> entry1 = createMock(Map.Entry.class);
        Map.Entry<String, Integer> entry2 = createMock(Map.Entry.class);

        expect(entry1.getKey()).andReturn("one");
        expect(entry1.getValue()).andReturn(1);
        expect(entry2.getKey()).andReturn("two");
        expect(entry2.getValue()).andReturn(2);

        expect(extractor.getValue("one")).andReturn(1);
        expect(extractor.getValue("two")).andReturn(2);

        replay(extractor, entry1, entry2);
        List<Map.Entry<String, Integer>> coll = new ArrayList<Map.Entry<String, Integer>>();
        coll.add(entry1);
        coll.add(entry2);
        assertTrue(entrySet.containsAll(coll));
        verify(extractor, entry1, entry2);
    }

    /**
     * Tests {@link Set#containsAll(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAllFalse() {
        Map.Entry<String, String> entry1 = createMock(Map.Entry.class);

        expect(entry1.getKey()).andReturn("one");
        expect(entry1.getValue()).andReturn("value4");

        expect(extractor.getValue("one")).andReturn(1);

        replay(extractor, entry1);
        List<Map.Entry<String, String>> coll = new ArrayList<Map.Entry<String,String>>();
        coll.add(entry1);
        assertFalse(entrySet.containsAll(coll));
        verify(extractor, entry1);
    }

    /**
     * Test method for {@link Set#isEmpty()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsEmpty() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);

        replay(extractor, keys);
        assertFalse(entrySet.isEmpty());
        verify(extractor, keys);
    }

    /**
     * Test method for {@link Set#iterator()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIterator() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");

        expect(extractor.getValue("two")).andReturn(2);

        replay(extractor, keys);
        Iterator<Map.Entry<String, Integer>> entryIt = entrySet.iterator();
        assertTrue(entryIt.hasNext());
        MapEntry<String, Integer> entry = new MapEntry<String, Integer>(
                "two", 2, false);
        assertEquals(entry, entryIt.next());
        verify(extractor, keys);
    }

    /**
     * Test method for {@link Set#iterator()}.
     */
    @SuppressWarnings("unchecked")
    @Test(expected=UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);

        try {
            replay(extractor, keys);
            entrySet.iterator().remove();
        } finally {
            verify(extractor, keys);
        }
    }

    /**
     * Tests {@link Set#remove(Object)}.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRemove() {
        entrySet.remove(null);
    }

    /**
     * Tests {@link Set#removeAll(java.util.Collection)}
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRemoveAll() {
        entrySet.removeAll(null);
    }

    /**
     * Tests {@link Set#retainAll(java.util.Collection)}
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRetainAll() {
        entrySet.retainAll(null);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.HeaderValuesMap#size()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSize() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        replay(extractor, keys);
        assertEquals(2, entrySet.size());
        verify(extractor, keys);
    }

    /**
     * Test method for {@link Set#toArray()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToArray() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValue("one")).andReturn(1);
        expect(extractor.getValue("two")).andReturn(2);

        MapEntry<String, Integer>[] entryArray = new MapEntry[2];
        entryArray[0] = new MapEntry<String, Integer>("one", 1, false);
        entryArray[1] = new MapEntry<String, Integer>("two", 2, false);

        replay(extractor, keys, values1, values2);
        assertArrayEquals(entryArray, entrySet.toArray());
        verify(extractor, keys, values1, values2);
    }

    /**
     * Test method for {@link Set#toArray(Object[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToArrayTArray() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValue("one")).andReturn(1);
        expect(extractor.getValue("two")).andReturn(2);

        MapEntry<String, Integer>[] entryArray = new MapEntry[2];
        entryArray[0] = new MapEntry<String, Integer>("one", 1, false);
        entryArray[1] = new MapEntry<String, Integer>("two", 2, false);

        replay(extractor, keys, values1, values2);
        MapEntry<String, String>[] realArray = new MapEntry[2];
        assertArrayEquals(entryArray, entrySet.toArray(realArray));
        verify(extractor, keys, values1, values2);
    }
}
