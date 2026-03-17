/**
 * Observer Pattern — CatalogDisplay is a concrete observer that reacts to
 * catalog loading events by printing status messages to standard output.
 *
 * Design justification:
 *   CatalogDisplay represents the "View" in an MVC context. It knows nothing
 *   about how the data is loaded or parsed; it simply reacts to notifications
 *   from the model. Replacing or adding display components requires no changes
 *   to the loader or the model.
 */
public class CatalogDisplay implements CatalogObserver {

    private final String displayName;

    public CatalogDisplay(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void onCatalogUpdate(String event) {
        System.out.println("[" + displayName + "] Catalog update received: " + event);
    }
}
