package implementations;

import java.util.Iterator;

public class ReversedList2<E> implements Iterable<E> {
    public static final int INITIAL_CAPACITY = 7;
    private Object[] elements;
    private int size;
    private int head;
    private int tail;

    public ReversedList2() {
        this.elements = new Object[INITIAL_CAPACITY];
     /*   int middle = INITIAL_CAPACITY / 2;
        head = tail = middle;*/
    }

    public void add(E element) {
        if (this.size == 0) {
            this.elements[this.head] = element;
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;

        Object[] newElements = new Object[newCapacity];
        int middle = newCapacity / 2;
        int begin = middle - this.size / 2;
        int index = this.head;

        for (int i = begin; index < this.tail; i++) {
            newElements[i] = this.elements[index++];

        }
        this.head = begin;
        this.tail = this.head + this.size - 1;
        return newElements;
    }

    public E removeAt(int index) {
        if (isEmpty()) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            E element = this.getAt(i);
            if (this.elements[i] == element) {
                this.elements[i] = null;
                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }
                this.removeLast();
                return element;
            }
        }
        return null;
    }


    public E removeLast() {
        if (!isEmpty()) {
            E element = this.getAt(this.tail);
            this.elements[this.tail] = null;
            this.tail--;
            this.size--;
            return element;
        }
        return null;
    }


    @SuppressWarnings("unckecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }


    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.elements.length;
    }


    public boolean isEmpty() {
        return this.size == 0;
    }


    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index <= tail;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }
}
