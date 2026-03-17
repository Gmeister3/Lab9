import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM Parser — uses JAXP to read catalog.xml and populate a CatalogModel.
 *
 * Design justification:
 *   The loader is responsible only for XML-to-object binding; it has no knowledge
 *   of pricing strategies or how results are displayed. This separation of concerns
 *   means parsing logic can be changed (e.g., swapped for SAX or StAX) without
 *   affecting the model or observers.
 */
public class CatalogLoader {

    /**
     * Parses the given XML file, creates AbstractProduct instances, and loads them
     * into the provided CatalogModel (which will then notify its observers).
     *
     * @param xmlFilePath Path to the catalog XML file.
     * @param model       The CatalogModel to populate.
     * @throws Exception  If the XML cannot be parsed.
     */
    public void load(String xmlFilePath, CatalogModel model) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Disable external entity resolution to prevent XXE vulnerabilities
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));
        document.getDocumentElement().normalize();

        NodeList productNodes = document.getElementsByTagName("product");
        List<AbstractProduct> loaded = new ArrayList<>();

        for (int i = 0; i < productNodes.getLength(); i++) {
            Element elem = (Element) productNodes.item(i);

            String id        = elem.getAttribute("id");
            boolean available = Boolean.parseBoolean(elem.getAttribute("available"));
            String name      = getTextContent(elem, "name");
            double price     = Double.parseDouble(getTextContent(elem, "price"));
            String category  = getTextContent(elem, "category");
            String desc      = getTextContent(elem, "description");

            // Store every product regardless of availability; the available flag
            // on the Product object conveys stock status without losing any data.
            loaded.add(new Product(id, name, price, available, category, desc));
        }

        model.setProducts(loaded);
    }

    /** Helper: retrieves trimmed text content of the first matching child element. */
    private String getTextContent(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return "";
        }
        return nodes.item(0).getTextContent().trim();
    }
}
