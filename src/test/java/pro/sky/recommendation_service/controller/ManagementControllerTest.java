package pro.sky.recommendation_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.xml.sax.SAXException;
import pro.sky.recommendation_service.service.impl.DynamicRuleServiceImpl;
import pro.sky.recommendation_service.service.impl.ManagementServiceImpl;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManagementController.class)
class ManagementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ManagementServiceImpl managementServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void clearCaches() {
    }

    @Test
    @DisplayName("Выдача информации - имя и версия сервиса")
    void info() throws Exception {

        when(managementServiceImpl.getInfo()).thenReturn("Инфо");

        //test & check

        mvc.perform(get("/management/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(managementServiceImpl,times(1)).getInfo();
    }
}