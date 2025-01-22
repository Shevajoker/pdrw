package com.pdrw.pdrw.triya.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.triya.model.ArchiveTriyaRu;
import com.pdrw.pdrw.triya.model.ArchiveTriyaRuDto;
import com.pdrw.pdrw.triya.repository.ArchiveTriyaRuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса ArchiveTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class ArchiveTriyaRuServiceImplTest {

    @InjectMocks
    private ArchiveTriyaRuServiceImpl archiveTriyaRuService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ArchiveTriyaRuRepository archiveTriyaRuRepository;
    @Mock
    private JsonNode jsonNode;
    @Mock
    private JsonNode childNode;
    @Mock
    private Iterator<JsonNode> iterator;
    @Mock
    private List<ArchiveTriyaRu> triyaRuList;
    private List<ArchiveTriyaRuDto> triyaRuDtoList;

    private static final String DATA = "data";
    private static final String TEST_DATA = "test data";
    private static final Integer AGE = 2020;

    @Test
    @DisplayName("Устанавливаем данные -> Проверка вызова ArchiveTriyaRuRepository#save и ArchiveTriyaRuRepository#deleteAllInBatch")
    void setData_checkInvokeSaveAndDeleteAllInBatch() throws IOException {

        when(objectMapper.readTree(any(File.class))).thenReturn(jsonNode);
        when(jsonNode.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(childNode);
        when(childNode.get(0)).thenReturn(childNode);
        when(childNode.get(1)).thenReturn(childNode);
        when(childNode.get(2)).thenReturn(childNode);
        when(childNode.asInt()).thenReturn(AGE);
        when(childNode.asText()).thenReturn(TEST_DATA);

        archiveTriyaRuService.setData(DATA);

        ArchiveTriyaRu archiveTriyaRu = new ArchiveTriyaRu();
        archiveTriyaRu.setAge(AGE);
        archiveTriyaRu.setUrl(TEST_DATA);
        archiveTriyaRu.setDescription(TEST_DATA);

        assertAll(
                () -> verify(archiveTriyaRuRepository).deleteAllInBatch(),
                () -> verify(archiveTriyaRuRepository).save(archiveTriyaRu)
        );
    }

    @Test
    @DisplayName("Устанавливаем данные -> Ловим исключение")
    void setData_thenException() throws IOException {

        when(objectMapper.readTree(any(File.class))).thenThrow(IOException.class);

        assertThrows(AssertionError.class, () -> archiveTriyaRuService.setData(DATA));
    }

    @Test
    @DisplayName("Получаем список ArchiveTriyaRu")
    void getArchiveTriyaRu_returnCorrectList() {
        when(archiveTriyaRuRepository.findAllOrder()).thenReturn(triyaRuList);
        when(archiveTriyaRuRepository.findAllUrls()).thenReturn(List.of("test"));
        when(archiveTriyaRuRepository.findAllDtos()).thenReturn(triyaRuDtoList);

        List<ArchiveTriyaRu> actual = archiveTriyaRuService.getArchiveTriyaRu();

        assertNull(actual);
    }
}
