/**
 * Main driver — demonstrates all required design patterns and XML processing.
 *
 * Patterns exercised:
 *   1. DOM / JAXP  — CatalogLoader parses catalog.xml into AbstractProduct objects.
 *   2. Null Object  — NullProduct is returned for missing/unavailable items;
 *                     no null checks are needed in application code.
 *   3. Strategy     — RegularPrice and SalePrice are applied to Product instances
 *                     at runtime without modifying the product class.
 *   4. Observer     — CatalogDisplay instances are notified whenever the model
 *                     changes, decoupling the view from data loading.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Dynamic Catalog System ===\n");

        // ------------------------------------------------------------------
        // 1. Set up the model (Subject) and attach observers (Observer Pattern)
        // ------------------------------------------------------------------
        CatalogModel model = new CatalogModel();
        CatalogDisplay primaryDisplay   = new CatalogDisplay("PrimaryDisplay");
        CatalogDisplay secondaryDisplay = new CatalogDisplay("SecondaryDisplay");

        model.addObserver(primaryDisplay);
        model.addObserver(secondaryDisplay);

        // ------------------------------------------------------------------
        // 2. Parse catalog.xml using the DOM / JAXP loader
        // ------------------------------------------------------------------
        CatalogLoader loader = new CatalogLoader();
        String xmlPath = "catalog.xml";
        try {
            loader.load(xmlPath, model);  // triggers Observer notifications
        } catch (Exception e) {
            System.err.println("Failed to load catalog: " + e.getMessage());
            return;
        }

        // ------------------------------------------------------------------
        // 3. Display all loaded products
        // ------------------------------------------------------------------
        System.out.println("\n--- All Products (Regular Pricing) ---");
        for (AbstractProduct p : model.getProducts()) {
            System.out.println(p.getDescription());
        }

        // ------------------------------------------------------------------
        // 4. Demonstrate Strategy Pattern — apply SalePrice to available products
        // ------------------------------------------------------------------
        System.out.println("\n--- Products with 20% Sale Discount ---");
        PriceStrategy saleStrategy = new SalePrice(0.20);
        for (AbstractProduct p : model.getProducts()) {
            if (!p.isNil()) {
                Product product = (Product) p;
                product.setPriceStrategy(saleStrategy);
                System.out.println(product.getDescription());
            }
        }

        // ------------------------------------------------------------------
        // 5. Demonstrate Null Object Pattern — search for existing and missing IDs
        // ------------------------------------------------------------------
        System.out.println("\n--- Product Lookup (Null Object Pattern) ---");
        String[] lookupIds = {"P001", "P999"};
        for (String id : lookupIds) {
            AbstractProduct result = model.findById(id);
            System.out.printf("Lookup '%s': isNil=%b | %s%n",
                    id, result.isNil(), result.getDescription());
        }

        // ------------------------------------------------------------------
        // 6. Demonstrate Observer Pattern — dynamically add a product
        // ------------------------------------------------------------------
        System.out.println("\n--- Adding a New Product (Observer Notification) ---");
        Product newProduct = new Product("P006", "Webcam HD", 59.99,
                true, "Accessories", "1080p HD webcam with built-in microphone.");
        model.addProduct(newProduct);

        System.out.println("\n=== End of Demo ===");
    }
}
