package example5;

public interface Loggable {

    // All methods in interfaces are automatically public and abstract, they cannot be made private, protected, package-private,
    // or static
    void log();

    // Ok I lied, they can be static, and can even have default implementation but that is WAY out of scope!

}
