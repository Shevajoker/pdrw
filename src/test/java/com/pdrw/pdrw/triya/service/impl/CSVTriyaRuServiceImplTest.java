package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса CSVTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class CSVTriyaRuServiceImplTest {

    @InjectMocks
    private CSVTriyaRuServiceImpl csvTriyaRuService;
    @Mock
    private TriyaRuService triyaRuService;

    @Test
    @DisplayName("Получаем путь к файлу -> Возвращается путь файла")
    void getCSVPath_returnCorrectPath() {
        TriyaRu triyaRu = new TriyaRu(UUID.randomUUID(), "article", "name", "image", new BigDecimal(6), new BigDecimal(5),
                new BigDecimal(1), new Date(), new Date(), "type", 1, 1, 1, 1, 1.0, true, "link");

        when(triyaRuService.findAll()).thenReturn(List.of(triyaRu));

        Path actual = csvTriyaRuService.getCSVPath();
        assertEquals("triya-ru.csv", actual.getFileName().toString());
    }

    @Test
    @DisplayName("Получаем путь к файлу -> Ловим исключение")
    void getCSVPath_thenException() {
        try (MockedStatic<Files> mockStatic = mockStatic(Files.class)) {
            mockStatic.when(() -> Files.newBufferedWriter(Paths.get("./triya-ru.csv"), StandardCharsets.UTF_8)).thenThrow(IOException.class);

            assertThrows(RuntimeException.class, () -> csvTriyaRuService.getCSVPath());
        }
    }
}
