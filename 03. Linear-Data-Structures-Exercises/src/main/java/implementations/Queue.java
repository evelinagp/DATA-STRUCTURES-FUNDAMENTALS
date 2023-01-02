package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;

    private Node<E> tail;

    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        Node<E> toInsert = new Node<>(element);
        if (this.isEmpty()) {
            this.head = this.tail = toInsert;
        } else {
            /*constant time complexity-> O(1) */
            this.tail.next = toInsert;
            this.tail = toInsert;

            /*linear time -> n */
         /*   Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;*/
        }
        size++;
    }


    @Override
    public E poll() {
        ensureNonEmpty();

        Node<E> first = this.head;
        this.head = first.next;
        if (this.size() == 1) {
            this.head = this.tail = null;
        } else {

            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }
        this.size--;
        return first.value;
    }
    /*
            ensureNonEmpty();
        E element = (E) this.head.next;
        if (this.size() == 1) {
            this.head = this.tail = null;
        } else {

            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }
        this.size--;
        return element;
    }
     */

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.value;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.next;
                return value;
            }
        };
    }
}
