package example7;

public class ResizableSet {

    private int[] data;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public ResizableSet() {
        this(DEFAULT_CAPACITY);
    }

    public ResizableSet(int capacity) {
        this.data = new int[Math.max(capacity, 1)];
    }

    public boolean put(int i) {
        boolean added = false;
        if(!contains(i)) {
            added = true;
            if(size >= data.length) {
                resize();
            }
            data[size] = i;
            size++;
        }
        return added;
    }

    private void resize() {
        int newCapacity = 3 * data.length / 2;
        int[] temp = new int[newCapacity];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }

    private boolean contains(int num) {
        boolean found = false;
        for (int i = 0; i < size && !found; i++) {
            found = data[i] == num;
        }
        return found;
    }

    public int[] toArray() {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = data[i];
        }
        return arr;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if(i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
