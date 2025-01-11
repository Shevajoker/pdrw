package com.pdrw.pdrw.pinskdrevby.repository;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PinskdrevByRepository extends JpaRepository<PinskdrevBy, UUID> {

    List<PinskdrevBy> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT p.type FROM PinskdrevBy p group by p.type")
    List<String> findAllTypes();

    List<PinskdrevBy> findByTypeLike(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy as p
             WHERE p.actual = true
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<PinskdrevBy> findActualByType(String article);

    @Query("SELECT round (AVG(p.priceNew), 2) FROM PinskdrevBy as p WHERE p.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE PinskdrevBy SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countByActualTrue();

    @Query("SELECT round (AVG(p.priceNew), 2) FROM PinskdrevBy as p WHERE p.actual = true and p.type like :type and p.priceNew != 0")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT p.article FROM PinskdrevBy p group by p.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM PinskdrevBy p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew ASC LIMIT 1")
    Optional<PinskdrevBy> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM PinskdrevBy p WHERE p.actual = true AND p.type like :type and p.priceNew != 0 ORDER BY p.priceNew DESC LIMIT 1")
    Optional<PinskdrevBy> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from PinskdrevBy as p2 where p2.article = p.article) = 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevBy> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy as p
             WHERE p.actual = true
             AND p.dateUpdate < :date
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevBy> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy as p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from PinskdrevBy as p2 where p2.article = p.article) > 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<PinskdrevBy> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy as p
             WHERE p.article = :article
             ORDER BY p.dateUpdate DESC
             LIMIT 2
            """)
    List<PinskdrevBy> getItemWithPrevious(String article);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM PinskdrevBy as p
             WHERE p.actual = true
             AND p.priceOld > 0
             AND p.type = ?1
             ORDER BY p.priceNew
             ASC
            """)
    List<PinskdrevBy> findActualWithSaleByType(String type);

    @Query("""
             SELECT count (p.id)
             FROM PinskdrevBy as p
             WHERE p.actual = true
             AND p.type = ?1
            """)
    Integer countItemsByType(String type);
}
