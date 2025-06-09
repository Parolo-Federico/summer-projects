package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class QueueTest {
    Queue<Integer> queue;
    @BeforeEach
    void setUp() {
        queue = new Queue<Integer>(10);
    }
    @Test
    void isFullTest(){
        for (int i = 0; i < 10; i++) {
            queue.enqueue(1);
        }
        Assertions.assertFalse(queue.enqueue(1),"not working full");
    }
    @Test
    void normalEnqueueTest(){
        Assertions.assertTrue(queue.enqueue(1),"enqueue not working");
    }
    @Test
    void normalDequeueTest(){
        queue.enqueue(1);
        Integer element = queue.dequeue();
        Assertions.assertEquals(1,element);
    }
    @Test
    void emptyDequeueTest(){
        Assertions.assertNull(queue.dequeue());
    }
    @Test
    void multipleOperationsTest(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                Assertions.assertTrue(queue.enqueue(1));
            }
            for (int j = 0; j < 10; j++) {
                Integer value = queue.dequeue();
                Assertions.assertEquals(1,value);

            }
        }
    }
}