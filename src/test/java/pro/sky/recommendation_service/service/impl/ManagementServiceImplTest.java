package pro.sky.recommendation_service.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagementServiceImplTest {
    @InjectMocks
    private ManagementServiceImpl managementService;

    @Mock
    private CaffeineCacheManager caffeineCacheManager;

    @Mock
    private File xmlFile = new File("./pom.xml");

    @Test
    @DisplayName("Clear cache")
    void clearCaches() {
        Cache cache = Mockito.mock(Cache.class);
        when(caffeineCacheManager.getCacheNames()).thenReturn(List.of("cacheName1", "cacheName2"));
        Mockito.when(caffeineCacheManager.getCache(anyString())).thenReturn(cache);

        managementService.clearCaches();

        assertFalse(cache.invalidate());
    }

    @Test
    @DisplayName("Вывод информации о имени и версии сервиса")
    void getInfo() throws IOException, SAXException, ParserConfigurationException {
        // test & check
        String actual = managementService.getInfo();
        assertNotNull(actual);
        assertTrue(actual.length() > 0);
        assertEquals(actual.getClass(), String.class);
    }
}