package com.alex.chatserver.tests;

import com.alex.chatserver.Queue;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

class QueueTest {

    private static int[] array;
    private static Queue<Integer> queue;
    private static Field queueField;

    @BeforeClass
    static void init() throws NoSuchFieldException {
        Class queueClass = Queue.class;
        queueField = queueClass.getDeclaredField("queue");

        queue = new Queue<>();
        array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Arrays.stream(array).forEach(queue::push);
    }

    @Test
    void pushTest() throws NoSuchFieldException, IllegalAccessException {

        LinkedList<Integer> list = (LinkedList<Integer>) queueField.get(queue);

        boolean test = IntStream.iterate(0, i -> i + 1).limit(array.length)
                .mapToObj(i -> list.get(i).equals(array[i]))
                .reduce(true, (acc, value) -> acc = acc && value);

        assertTrue(test);
    }

    @Test
    void popTest() throws IllegalAccessException {
        LinkedList<Integer> list = (LinkedList<Integer>) queueField.get(queue);

        boolean test = IntStream.iterate(0, i -> i + 1).limit(array.length)
                .mapToObj(i -> list.get(i).equals(array[i]))
                .reduce(true, (acc, value) -> acc = acc && value);

        assertTrue(test);
    }
}
