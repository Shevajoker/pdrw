package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса ExcelTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class ExcelTriyaRuServiceImplTest {

    @InjectMocks
    private ExcelTriyaRuServiceImpl excelTriyaRuService;
    @Mock
    private TriyaRuService triyaRuService;

    @Test
    @DisplayName("Получаем путь к файлу -> Возвращается путь файла")
    void getExcelFile_returnCorrectPath() {
        TriyaRu triyaRu = new TriyaRu(UUID.randomUUID(), "article", "name", "image", new BigDecimal(6), new BigDecimal(5),
                new BigDecimal(1), new Date(), new Date(), "type", 1, 1, 1, 1, 1.0, true, "link");

        when(triyaRuService.findAll()).thenReturn(List.of(triyaRu));

        Path actual = excelTriyaRuService.getExcelFile();

        assertEquals("Triya-ru.xlsx", actual.getFileName().toString());
    }
}
