package implementations;

 import java.util.Iterator;

    public class ReversedList<E> implements Iterable<E> {

        private static final int INITIAL_CAPACITY = 2;

        private E[] elements;

        private int size;

        private int capacity;


        public ReversedList(int capacity) {
            this.size = 0;
            this.elements = (E[]) new Object[capacity];
            this.capacity = capacity;
        }

        public ReversedList() {
            this(INITIAL_CAPACITY);
        }

        public int size() {
            return this.size;
        }

        public int capacity() {
            return this.capacity;
        }

        public E get(int index) {

            checkIndex(index);

            return elements[size - 1 - index];
        }

        public void set(int index, E item) {

            checkIndex(index);

            this.elements[size - 1 - index] = item;
        }

        public void add(E element) {

            if (size >= this.capacity) {
                resize();
            }

            this.elements[this.size++] = element;
        }

        public E removeAt(int index) {

            checkIndex(index);

            index = size - 1 - index;

            E element = this.elements[index];

            shift(index);

            this.size--;

            if (this.size <= this.capacity / 4
                    && this.capacity > INITIAL_CAPACITY) {
                resize();
            }

            return element;
        }

        private void checkIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IllegalArgumentException();
            }
        }

        private void resize() {

            if (size <= this.capacity / 4) {
                this.capacity /= 2;
            } else if (size >= this.capacity) {
                this.capacity *= 2;
            }

            E[] newArray = (E[]) new Object[this.capacity];
            System.arraycopy(elements, 0,
                    newArray, 0, size);

            this.elements = newArray;
        }

        private void shift(int index) {

            System.arraycopy(elements, index + 1,
                    elements, index, size - 1 - index);

            elements[size - 1] = null;
        }


        @Override
        public Iterator<E> iterator() {
            return new ListIterator();
        }

        private final class ListIterator implements Iterator<E> {
            private int counter;

            public ListIterator() {
                this.counter = 0;
            }

            @Override
            public boolean hasNext() {
                return size > this.counter;
            }

            @Override
            public E next() {
                return get(this.counter++);
            }
        }
    }