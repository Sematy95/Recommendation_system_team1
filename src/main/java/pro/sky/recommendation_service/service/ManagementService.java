package pro.sky.recommendation_service.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Interface defining the contract for management operations.
 */
public interface ManagementService {
    /**
     * Clears all caches.
     */
    public void clearCaches();

    /**
     * Retrieves project information.
     *
     * @return A string representation of the project information.
     * @throws IOException                  If an I/O error occurs.
     * @throws SAXException                 If a SAX parsing error occurs.
     * @throws ParserConfigurationException If a parser configuration error occurs.
     */
    public String getInfo() throws IOException, SAXException, ParserConfigurationException;
}
