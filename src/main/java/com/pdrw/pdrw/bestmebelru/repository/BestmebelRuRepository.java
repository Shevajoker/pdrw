package com.pdrw.pdrw.bestmebelru.repository;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BestmebelRuRepository extends JpaRepository<BestmebelRu, UUID> {

    List<BestmebelRu> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT p.type FROM BestmebelRu p group by p.type")
    List<String> findAllTypes();

    List<BestmebelRu> findByTypeLike(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu as p
             WHERE p.actual = true
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<BestmebelRu> findActualByType(String article);

    @Query("SELECT round (AVG(p.priceNew), 2) FROM BestmebelRu as p WHERE p.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE BestmebelRu SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countBestmebelRuByActualTrue();

    @Query("SELECT round (AVG(p.priceNew), 2) FROM BestmebelRu as p WHERE p.actual = true and p.type like :type and p.priceNew != 0")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT p.article FROM BestmebelRu p group by p.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM BestmebelRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew ASC LIMIT 1")
    Optional<BestmebelRu> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM BestmebelRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew DESC LIMIT 1")
    Optional<BestmebelRu> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from BestmebelRu as p2 where p2.article = p.article) = 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<BestmebelRu> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate,p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu as p
             WHERE p.actual = true
             AND p.dateUpdate < :date
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<BestmebelRu> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate,p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu as p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from BestmebelRu as p2 where p2.article = p.article) > 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<BestmebelRu> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate,p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu as p
             WHERE p.article = :article
             ORDER BY p.dateUpdate DESC
             LIMIT 2
            """)
    List<BestmebelRu> getItemWithPrevious(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.bestmebelru.model.BestmebelRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM BestmebelRu as p
             WHERE p.actual = true
             AND p.priceOld > 0
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<BestmebelRu> findActualWithSaleByType(String type);

    @Query("""
             SELECT count (p.id)
             FROM BestmebelRu as p
             WHERE p.actual = true
             AND p.type = ?1
            """)
    Integer countItemsByType(String type);

    @Query("""
             SELECT sum(p.priceNew)
             FROM BestmebelRu as p
             WHERE p.actual = true
            """)
    BigDecimal summPriceNewActualTrue();

    @Query("""
            select count(*) from BestmebelRu as p where p.actual = true and p.priceNew >= ?1 and p.priceNew < ?2
            """)
    Integer findDataForDashboardLineChart(BigDecimal priceMin, BigDecimal priceMax);
}
