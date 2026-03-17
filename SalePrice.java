/**
 * Strategy Pattern — SalePrice applies a configurable discount percentage.
 *
 * Design justification:
 *   The discount logic is fully isolated here. Adding a new pricing variant
 *   (e.g., BulkPrice, MemberPrice) requires only a new class implementing
 *   PriceStrategy — the rest of the system remains untouched, demonstrating
 *   the Open/Closed Principle alongside loose coupling.
 */
public class SalePrice implements PriceStrategy {

    private final double discountRate; // e.g. 0.20 for 20 % off

    /**
     * @param discountRate Fraction to deduct from the base price (0.0 – 1.0).
     */
    public SalePrice(double discountRate) {
        if (discountRate < 0.0 || discountRate > 1.0) {
            throw new IllegalArgumentException("Discount rate must be between 0.0 and 1.0");
        }
        this.discountRate = discountRate;
    }

    @Override
    public double calculate(double basePrice) {
        return basePrice * (1.0 - discountRate);
    }

    public double getDiscountRate() { return discountRate; }
}
