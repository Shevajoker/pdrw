package com.pdrw.pdrw.triya.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса TriyaRuDataServiceImpl")
@ExtendWith(MockitoExtension.class)
class TriyaRuDataServiceImplTest {

    @InjectMocks
    private TriyaRuDataServiceImpl triyaRuDataServiceImpl;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private TriyaRuRepository triyaRuRepository;
    @Mock
    private JsonNode jsonNode;
    @Mock
    private JsonNode childNode;
    @Mock
    private Iterator<JsonNode> iterator;

    private static final String DATA = "data";
    private static final String TEST_DATA = "test data";
    private static final Integer AGE = 2020;

    @Test
    @DisplayName("Устанавливаем данные -> Проверка вызова методов и возвращение count")
    void setData_checkInvokeMethodAndReturnCount() throws IOException {
        when(objectMapper.readTree(anyString())).thenReturn(jsonNode);
        when(jsonNode.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(childNode);

        int actual = triyaRuDataServiceImpl.setData(DATA);

        assertAll(
                () -> assertEquals(1, actual),
                () -> verify(triyaRuRepository).markActualFalse("-"),
                () -> verify(triyaRuRepository).save(any(TriyaRu.class))
        );
    }
}
