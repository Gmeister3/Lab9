/**
 * Null Object Pattern — NullProduct replaces null references for missing items.
 *
 * Design justification:
 *   Instead of returning null when a product is not found, the system returns a
 *   NullProduct. Callers can invoke any AbstractProduct method without a
 *   NullPointerException; isNil() allows optional differentiation when needed.
 *   This removes null-check boilerplate and keeps the codebase stable.
 */
public class NullProduct extends AbstractProduct {

    public NullProduct() {
        this.name  = "N/A";
        this.price = 0.0;
    }

    @Override
    public boolean isNil() { return true; }

    @Override
    public String getDescription() {
        return "[NullProduct] The requested product was not found in the catalog.";
    }
}
