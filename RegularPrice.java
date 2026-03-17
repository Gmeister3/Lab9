/**
 * Strategy Pattern — RegularPrice returns the base price unchanged.
 *
 * Design justification:
 *   Encapsulating the "no adjustment" algorithm in its own class keeps the
 *   Product class free of conditional logic. Swapping strategies at runtime
 *   (e.g., from RegularPrice to SalePrice) requires no changes to Product.
 */
public class RegularPrice implements PriceStrategy {

    @Override
    public double calculate(double basePrice) {
        return basePrice;
    }
}
