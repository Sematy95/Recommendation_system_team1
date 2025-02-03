package pro.sky.recommendation_service.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pro.sky.recommendation_service.service.ManagementService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Service
public class ManagementServiceImpl implements ManagementService {

    private static final Logger log = LoggerFactory.getLogger(ManagementServiceImpl.class);

    private final CaffeineCacheManager cacheManager;
    private File xmlFile;

    public ManagementServiceImpl(CaffeineCacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.xmlFile = new File("./pom.xml");
    }

    @Override
    public void clearCaches() {
        log.info("clear caches method was invoked");

        cacheManager.getCacheNames().forEach(cacheName -> {
            cacheManager.getCache(cacheName).clear();
            log.info("cache:{} was cleared", cacheName);
        });
    }

    /**
     * Метод для извлечения имени и версии проекта из файла pom.xml
     * @return Возвращает имя и версию проекта
     * @throws IOException Ошибки неудачных/прерванных операций ввода-вывода
     * @throws SAXException Ошибки или предупреждения, полученные либо из синтаксического анализатора XML, либо из приложения
     * @throws ParserConfigurationException Ошибки конфигурации
     */
    public String getInfo() throws IOException, SAXException, ParserConfigurationException {
        log.info("project info method was invoked");
        String str = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();
        String version = getElementValue(root, "version");
        String name = getElementValue(root, "name");
        log.info("{\n\"name\": " + name + "\n" + "\"version\": " + version + "\n}");
        str = "{\n\"name\": " + name + "\n" + "\"version\": " + version + "\n}";
        return str;
    }

    /**
     *
     * @param parent корневой элемент
     * @param tagName корневой элемент
     * @return Возврашает строку с требуемым контентом из элемента сгенерированного документа
     */
    private static String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

}
