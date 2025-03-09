package com.pdrw.pdrw.bestmebelru;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuAverageCategoryData;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuData;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuDataService;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Используем профиль "test" для тестов
@Transactional // Откатываем транзакции после каждого теста
public class BestmebelRuIT {

    @Autowired
    protected BestmebelRuService bestmebelRuService;
    @Autowired
    protected BestmebelRuDataService bestmebelRuDataService;

    private String validJsonData = """
            [{
                 "nameProduct": "Кухня с барной стойкой Моника 13 BMS",
                 "dataId": "134510",
                 "uriProduct": "https://www.bestmebelshop.ru/catalog/mebel-dlya-kukhni/kukhnya-s-barnoy-stoykoy-monika-13-bms/",
                 "price": 175590,
                 "oldPrice": 292650,
                 "photo": "https://www.bestmebelshop.ruupload/resize_cache/iblock/0c2/3840_2160_12f5acf3a40db29ac22367486cf84834b/gajfkz8ue5uzcteex6xarllcrjyxsuxv.jpg",
                 "properties": {
                   "Материал корпуса": "ЛДСП",
                   "Материал фасадов": "МДФ",
                   "Производитель": "\\"Бэст-Мебель\\" (Россия)",
                   "Срок изготовления": "1 день",
                   "Гарантия": "18 месяцев",
                   "Подъём": "от 350 руб",
                   "Сборка": "от 10%",
                   "Артикул": "0950490",
                   "Ширина:": "Указать свой"
                 }
               }]
            """;
    private String invalidJsonData = "invalid json";

    @BeforeEach
    void auth() {

    }

    @Test
    void testSetData_ValidJson() throws JsonProcessingException {
        int result = bestmebelRuDataService.setData(validJsonData);
        assertEquals(1, result);
    }

    @Test
    void shouldCreate() {
        BestmebelRu bestmebelRu = getNewEntity();
        BestmebelRu result = bestmebelRuService.create(bestmebelRu);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(bestmebelRu.getArticle(), result.getArticle());
    }

    @Test
    void shouldFindAll() {
        BestmebelRu bestmebelRu = getNewEntity();
        BestmebelRu created = bestmebelRuService.create(bestmebelRu);
        assertNotNull(created);
        List<BestmebelRu> bestmebelRuList = bestmebelRuService.findAll();
        assertFalse(bestmebelRuList.isEmpty());
    }

    @Test
    void shouldFindByArticleOrderByDateUpdateDesc() {
        createEntityes(5);
        List<BestmebelRu> list = bestmebelRuService.findAll();
        BestmebelRu bestmebelRu = list.getFirst();
        List<BestmebelRu> result = bestmebelRuService.findByArticleOrderByDateUpdateDesc(bestmebelRu.getArticle());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindAllByTypes() {
        createEntityes(5);
        List<BestmebelRu> list = bestmebelRuService.findAll();
        BestmebelRu bestmebelRu = list.getFirst();
        List<BestmebelRu> result = bestmebelRuService.findAllByType(bestmebelRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindAllTypes() {
        createEntityes(5);
        List<String> result = bestmebelRuService.findAllTypes();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetData() {
        createEntityes(5);
        BestmebelRuData result = bestmebelRuService.getData();
        assertNotNull(result);
    }

    @Test
    void shouldFindById() {
        BestmebelRu bestmebelRu = getNewEntity();
        BestmebelRu created = bestmebelRuService.create(bestmebelRu);
        assertNotNull(created);
        BestmebelRu result = bestmebelRuService.findById(created.getId());
        assertNotNull(result);
        assertEquals(created.getArticle(), result.getArticle());
        assertEquals(created.getId(), result.getId());

    }

    @Test
    void shouldGetBestmebelRuAverageCategoryData() {
        createEntityes(5);
        List<BestmebelRuAverageCategoryData> result = bestmebelRuService.getBestmebelRuAverageCategoryData();
        assertNotNull(result);
    }

    @Test
    void shouldGetNewCreatedItems() {
        createEntityes(5);
        List<BestmebelRu> result = bestmebelRuService.getNewCreatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetNotUpdatedItems() {
        createEntityes(5);
        List<BestmebelRu> result = bestmebelRuService.getNotUpdatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetChangedItems() {
        createEntityes(5);
        List<BestmebelRu> result = bestmebelRuService.getNotUpdatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindActualByType() {
        createEntityes(5);
        BestmebelRu bestmebelRu = bestmebelRuService.findAll().getFirst();
        List<BestmebelRu> result = bestmebelRuService.findActualByType(bestmebelRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindActualWithSaleByType() {
        createEntityes(5);
        List<BestmebelRu> list = bestmebelRuService.findAll();
        BestmebelRu bestmebelRu = bestmebelRuService.findAll().getLast();
        List<BestmebelRu> result = bestmebelRuService.findActualWithSaleByType(bestmebelRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldCountItemsByType() {
        createEntityes(5);
        BestmebelRu bestmebelRu = bestmebelRuService.findAll().getFirst();
        Integer result = bestmebelRuService.countItemsByType(bestmebelRu.getType());
        assertTrue(result > 0);
    }

    private void createEntityes(int count) {
        for (int i = 0; i < count; i++) {
            BestmebelRu bestmebelRu = getNewEntity();
            bestmebelRuService.create(bestmebelRu);
        }
    }

    private BestmebelRu getNewEntity() {
        BestmebelRu result = new BestmebelRu();
        result.setArticle(RandomStringUtils.randomAlphabetic(5));
        result.setType(RandomStringUtils.randomAlphabetic(5));
        result.setActual(Boolean.TRUE);
        result.setPriceNew(BigDecimal.TEN);
        result.setPriceOld(BigDecimal.ONE);
        result.setCreateDate(new Date());
        result.setDateUpdate(new Date());
        result.setImage("img");
        result.setLink("link");
        result.setName("name");
        return result;
    }

}
