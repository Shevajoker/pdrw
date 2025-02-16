package com.pdrw.pdrw.triya.repository;

import com.pdrw.pdrw.triya.model.TriyaRu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TriyaRuRepository extends JpaRepository<TriyaRu, UUID> {

    List<TriyaRu> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT p.type FROM TriyaRu p group by p.type")
    List<String> findAllTypes();

    List<TriyaRu> findByTypeLike(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.actual = true
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<TriyaRu> findActualByType(String article);

    @Query("SELECT round (AVG(p.priceNew), 2) FROM TriyaRu as p WHERE p.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE TriyaRu SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countByActualTrue();

    @Query("SELECT round (AVG(p.priceNew), 2) FROM TriyaRu as p WHERE p.actual = true and p.type like :type and p.priceNew != 0")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT p.article FROM TriyaRu p group by p.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM TriyaRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew ASC LIMIT 1")
    Optional<TriyaRu> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM TriyaRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew DESC LIMIT 1")
    Optional<TriyaRu> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from TriyaRu as p2 where p2.article = p.article) = 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<TriyaRu> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.actual = true
             AND p.dateUpdate < :date
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<TriyaRu> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from TriyaRu as p2 where p2.article = p.article) > 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<TriyaRu> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.article = :article
             ORDER BY p.dateUpdate DESC
             LIMIT 2
            """)
    List<TriyaRu> getItemWithPrevious(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.actual = true
             AND p.priceOld > 0
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<TriyaRu> findActualWithSaleByType(String type);

    @Query("""
             SELECT count (p.id)
             FROM TriyaRu as p
             WHERE p.actual = true
             AND p.type = ?1
            """)
    Integer countItemsByType(String type);

    @Query("""
             SELECT sum(p.priceNew)
             FROM TriyaRu as p
             WHERE p.actual = true
            """)
    BigDecimal summPriceNewActualTrue();

    @Query("""
            select count(*) from TriyaRu as t where t.actual = true and t.priceNew >= ?1 and t.priceNew < ?2
            """)
    Integer findDataForDashboardLineChart(BigDecimal priceMin, BigDecimal priceMax);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.TriyaRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM TriyaRu as p
             WHERE p.actual = true
            """)
    List<TriyaRu> findAllActual();
}
