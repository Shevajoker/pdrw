package com.pdrw.pdrw.pinskdrevru.repository;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PinskdrevRuRepository extends JpaRepository<PinskdrevRu, UUID> {

    List<PinskdrevRu> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT p.type FROM PinskdrevRu p group by p.type")
    List<String> findAllTypes();

    List<PinskdrevRu> findByTypeLike(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevRu as p
             WHERE p.actual = true
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<PinskdrevRu> findActualByType(String article);

    @Query("SELECT round (AVG(p.priceNew), 2) FROM PinskdrevRu as p WHERE p.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE PinskdrevRu SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countPinskdrevRuByActualTrue();

    @Query("SELECT round (AVG(p.priceNew), 2) FROM PinskdrevRu as p WHERE p.actual = true and p.type like :type and p.priceNew != 0")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT p.article FROM PinskdrevRu p group by p.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM PinskdrevRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew ASC LIMIT 1")
    Optional<PinskdrevRu> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM PinskdrevRu p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew DESC LIMIT 1")
    Optional<PinskdrevRu> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevRu p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from PinskdrevRu as p2 where p2.article = p.article) = 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevRu> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevRu as p
             WHERE p.actual = true
             AND p.dateUpdate < :date
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevRu> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevRu as p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from PinskdrevRu as p2 where p2.article = p.article) > 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevRu> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevRu as p
             WHERE p.article = :article
             ORDER BY p.dateUpdate DESC
             LIMIT 2
            """)
    List<PinskdrevRu> getItemWithPrevious(String article);
}
