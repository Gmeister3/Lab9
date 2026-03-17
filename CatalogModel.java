import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Observer Pattern — CatalogModel acts as the Subject/Observable.
 *
 * Design justification:
 *   CatalogModel owns the authoritative list of products (the "Model" in MVC).
 *   Whenever the list is modified it notifies every registered CatalogObserver,
 *   decoupling the data layer from all presentation or logging concerns. Observers
 *   can be added or removed at runtime without touching this class.
 */
public class CatalogModel {

    private final List<AbstractProduct> products = new ArrayList<>();
    private final List<CatalogObserver>  observers = new ArrayList<>();

    // ---------------------------------------------------------------
    // Observer management
    // ---------------------------------------------------------------

    public void addObserver(CatalogObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CatalogObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String event) {
        for (CatalogObserver observer : observers) {
            observer.onCatalogUpdate(event);
        }
    }

    // ---------------------------------------------------------------
    // Data management
    // ---------------------------------------------------------------

    /** Replaces the current product list and notifies observers. */
    public void setProducts(List<AbstractProduct> loaded) {
        products.clear();
        products.addAll(loaded);
        notifyObservers("Catalog loaded — " + products.size() + " product(s) available.");
    }

    /** Appends a single product and notifies observers. */
    public void addProduct(AbstractProduct product) {
        products.add(product);
        notifyObservers("Product added: " + product.getName());
    }

    /**
     * Searches the catalog by product ID (case-insensitive).
     * Returns a NullProduct when no match is found, implementing the Null Object
     * Pattern so callers never receive a bare null reference.
     *
     * @param id The product identifier to look up.
     * @return The matching Product, or a NullProduct if not found.
     */
    public AbstractProduct findById(String id) {
        for (AbstractProduct p : products) {
            if (p instanceof Product) {
                Product concreteProduct = (Product) p;
                if (concreteProduct.getId().equalsIgnoreCase(id)) {
                    return concreteProduct;
                }
            }
        }
        return new NullProduct();
    }

    /** Returns an unmodifiable view of all products. */
    public List<AbstractProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
