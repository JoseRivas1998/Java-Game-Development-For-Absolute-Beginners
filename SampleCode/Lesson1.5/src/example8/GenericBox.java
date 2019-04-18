package example8;

public class GenericBox<T> {

    private T value;

    public GenericBox(T val) {
        this.value = val;
    }

    public T get() {
        return value;
    }

    public void set(T newValue) {
        this.value = newValue;
    }

}
