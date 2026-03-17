/**
 * Concrete product class bound from XML data via the DOM parser.
 *
 * Design justification (Null Object Pattern):
 *   Product represents a real, available item. By pairing it with NullProduct,
 *   callers never need to check for null references — isNil() cleanly signals
 *   whether the object carries meaningful data.
 */
public class Product extends AbstractProduct {
    private String id;
    private boolean available;
    private String category;
    private String description;
    private PriceStrategy priceStrategy;

    public Product(String id, String name, double price,
                   boolean available, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.category = category;
        this.description = description;
        // Default to regular pricing
        this.priceStrategy = new RegularPrice();
    }

    /** Attach a pricing strategy at runtime (Strategy Pattern). */
    public void setPriceStrategy(PriceStrategy strategy) {
        this.priceStrategy = strategy;
    }

    /** Returns the final price according to the currently active strategy. */
    public double getFinalPrice() {
        return priceStrategy.calculate(price);
    }

    @Override
    public boolean isNil() { return false; }

    @Override
    public String getDescription() {
        return String.format("[%s] %s — %s | Base: $%.2f | Final: $%.2f | Available: %b",
                id, name, category, price, getFinalPrice(), available);
    }

    /** Returns the raw catalog description text for this product. */
    public String getCatalogDescription() { return description; }

    // Additional getters
    public String getId()       { return id; }
    public boolean isAvailable(){ return available; }
    public String getCategory() { return category; }
}
