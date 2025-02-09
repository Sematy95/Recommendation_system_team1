package pro.sky.recommendation_service.service;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ManagementService {

    public void clearCaches();

    public String getInfo() throws IOException, SAXException, ParserConfigurationException;

}
