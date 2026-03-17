/**
 * Observer Pattern — interface for objects that want to be notified when the
 * catalog model changes.
 *
 * Design justification:
 *   Defining a custom interface (rather than relying on the deprecated
 *   java.util.Observable) keeps the design clean and avoids forced inheritance.
 *   Any class — view, logger, auditor — can become an observer simply by
 *   implementing this interface, achieving the loose coupling central to MVC.
 */
public interface CatalogObserver {
    /**
     * Called by the subject whenever the catalog data is updated.
     * @param event A human-readable description of the change.
     */
    void onCatalogUpdate(String event);
}
