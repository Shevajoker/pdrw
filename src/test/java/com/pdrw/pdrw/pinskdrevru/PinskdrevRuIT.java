package com.pdrw.pdrw.pinskdrevru;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuAverageCategoryData;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuData;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuDataService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
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
public class PinskdrevRuIT {

    @Autowired
    protected PinskdrevRuService pinskdrevRuService;
    @Autowired
    protected PinskdrevRuDataService pinskdrevRuDataService;

    private String validJsonData = """
            [{
              "nameProduct": "Кровать двойная «Алези» с высоким изножьем, Массив (дуб), Античная бронза, без механизма, 2000x1200 мм, 2150x1452x1045",
              "dataId": "5018",
              "uriProduct": "https://pinskdrev.ru/catalog/korpusnaya-mebel/krovati/krovat-dvoynaya-16-p349-16-ot-n-m-dlya-spalni-alez/massivdubaantichnayabronza2000x1200mm2150x1452x1045mm/",
              "price": 104805,
              "oldPrice": 123300,
              "photo": "web/catalogfiles/catalog/offers/krovat_dvoinaya_alezi_16_p349_16_antichnaya_bronza.jpg",
              "properties": {
                "Тип": "двуспальная кровать",
                "Длина (мм)": "2180 мм",
                "Высота (мм)": "1045 мм",
                "Длина спального места (мм)": "2000",
                "Ширина спального места (мм)": "1200; 1400; 1600; 1800; 2000",
                "Емкость для постельных принадлежностей": "да, только в кроватях с механизмом трансформации",
                "Основание кровати": "металлокаркас с ламелями",
                "Матрас в комплекте": "нет",
                "Рекомендуемая высота матраса (мм)": "230",
                "Изножье": "высокое"
              }
            }]
            """;
    private String invalidJsonData = "invalid json";

    @BeforeEach
    void auth() {

    }

    @Test
    void testSetData_ValidJson() throws JsonProcessingException {
        int result = pinskdrevRuDataService.setData(validJsonData);
        assertEquals(1, result);
    }

    @Test
    void shouldCreate() {
        PinskdrevRu pinskdrevRu = getNewEntity();
        PinskdrevRu result = pinskdrevRuService.create(pinskdrevRu);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(pinskdrevRu.getArticle(), result.getArticle());
    }

    @Test
    void shouldFindAll() {
        PinskdrevRu pinskdrevRu = getNewEntity();
        PinskdrevRu created = pinskdrevRuService.create(pinskdrevRu);
        assertNotNull(created);
        List<PinskdrevRu> pinskdrevRuList = pinskdrevRuService.findAll();
        assertFalse(pinskdrevRuList.isEmpty());
    }

    @Test
    void shouldFindByArticleOrderByDateUpdateDesc() {
        createEntityes(5);
        List<PinskdrevRu> list = pinskdrevRuService.findAll();
        PinskdrevRu pinskdrevRu = list.getFirst();
        List<PinskdrevRu> result = pinskdrevRuService.findByArticleOrderByDateUpdateDesc(pinskdrevRu.getArticle());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindAllByTypes() {
        createEntityes(5);
        List<PinskdrevRu> list = pinskdrevRuService.findAll();
        PinskdrevRu pinskdrevRu = list.getFirst();
        List<PinskdrevRu> result = pinskdrevRuService.findAllByType(pinskdrevRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindAllTypes() {
        createEntityes(5);
        List<String> result = pinskdrevRuService.findAllTypes();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetData() {
        createEntityes(5);
        PinskdrevRuData result = pinskdrevRuService.getData();
        assertNotNull(result);
    }

    @Test
    void shouldFindById() {
        PinskdrevRu pinskdrevRu = getNewEntity();
        PinskdrevRu created = pinskdrevRuService.create(pinskdrevRu);
        assertNotNull(created);
        PinskdrevRu result = pinskdrevRuService.findById(created.getId());
        assertNotNull(result);
        assertEquals(created.getArticle(), result.getArticle());
        assertEquals(created.getId(), result.getId());

    }

    @Test
    void shouldGetPinskdrevRuAverageCategoryData() {
        createEntityes(5);
        List<PinskdrevRuAverageCategoryData> result = pinskdrevRuService.getPinskdrevRuAverageCategoryData();
        assertNotNull(result);
    }

    @Test
    void shouldGetNewCreatedItems() {
        createEntityes(5);
        List<PinskdrevRu> result = pinskdrevRuService.getNewCreatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetNotUpdatedItems() {
        createEntityes(5);
        List<PinskdrevRu> result = pinskdrevRuService.getNotUpdatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetChangedItems() {
        createEntityes(5);
        List<PinskdrevRu> result = pinskdrevRuService.getNotUpdatedItems(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindActualByType() {
        createEntityes(5);
        PinskdrevRu pinskdrevRu = pinskdrevRuService.findAll().getFirst();
        List<PinskdrevRu> result = pinskdrevRuService.findActualByType(pinskdrevRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindActualWithSaleByType() {
        createEntityes(5);
        List<PinskdrevRu> list = pinskdrevRuService.findAll();
        PinskdrevRu pinskdrevRu = pinskdrevRuService.findAll().getLast();
        List<PinskdrevRu> result = pinskdrevRuService.findActualWithSaleByType(pinskdrevRu.getType());
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldCountItemsByType() {
        createEntityes(5);
        PinskdrevRu pinskdrevRu = pinskdrevRuService.findAll().getFirst();
        Integer result = pinskdrevRuService.countItemsByType(pinskdrevRu.getType());
        assertTrue(result > 0);
    }

    private void createEntityes(int count) {
        for (int i = 0; i < count; i++) {
            PinskdrevRu pinskdrevRu = getNewEntity();
            pinskdrevRuService.create(pinskdrevRu);
        }
    }

    private PinskdrevRu getNewEntity() {
        PinskdrevRu result = new PinskdrevRu();
        result.setArticle(RandomStringUtils.randomAlphabetic(5));
        result.setType(RandomStringUtils.randomAlphabetic(5));
        result.setActual(Boolean.TRUE);
        result.setPriceNew(BigDecimal.TEN);
        result.setPriceOld(BigDecimal.ONE);
        result.setCreateDate(new Date());
        result.setDateUpdate(new Date());
        result.setHeight(10);
        result.setLength(10);
        result.setImage("img");
        result.setLink("link");
        result.setName("name");
        return result;
    }

}