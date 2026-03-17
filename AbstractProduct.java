/**
 * Abstract class to support the Null Object Pattern.
 * Goal: No hard coding of null checks / loose dependencies.
 */
public abstract class AbstractProduct {
    protected String name;
    protected double price;

    // Method to check if the object is a "Null" variant 
    public abstract boolean isNil();
    
    // Method to return descriptive data 
    public abstract String getDescription();

    // Standard getters
    public String getName() { return name; }
    public double getPrice() { return price; }
}
