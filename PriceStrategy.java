/**
 * Interface to allow different variants of a pricing algorithm.
 * This promotes loose coupling between the data and the calculation logic.
 */
public interface PriceStrategy {
    /**
     * Calculates the final price based on the specific strategy.
     * @param basePrice The original price parsed from the XML.
     * @return The adjusted price.
     */
    double calculate(double basePrice);
}
