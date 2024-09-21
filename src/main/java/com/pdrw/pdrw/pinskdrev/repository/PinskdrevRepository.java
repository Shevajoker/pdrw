package com.pdrw.pdrw.pinskdrev.repository;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PinskdrevRepository extends JpaRepository<Pinskdrev, UUID> {

    List<Pinskdrev> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT p.type FROM Pinskdrev p group by p.type")
    List<String> findAllTypes();

    List<Pinskdrev> findByTypeLike(String article);

    @Query("SELECT round (AVG(p.priceNew), 2) FROM Pinskdrev as p WHERE p.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE Pinskdrev SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countPinskdrevByActualTrue();

    @Query("SELECT round (AVG(p.priceNew), 2) FROM Pinskdrev as p WHERE p.actual = true and p.type like :type")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT p.article FROM Pinskdrev p group by p.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.pinskdrev.model.Pinskdrev(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM Pinskdrev p WHERE p.actual = true AND p.type like :type ORDER BY p.priceNew ASC LIMIT 1")
    Optional<Pinskdrev> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.pinskdrev.model.Pinskdrev(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link) FROM Pinskdrev p WHERE p.actual = true AND p.type like :type ORDER BY p.priceNew DESC LIMIT 1")
    Optional<Pinskdrev> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrev.model.Pinskdrev(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM Pinskdrev p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Pinskdrev as p2 where p2.article = p.article) = 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<Pinskdrev> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrev.model.Pinskdrev(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM Pinskdrev as p
             WHERE p.actual = true
             AND p.dateUpdate < :date
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<Pinskdrev> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.pinskdrev.model.Pinskdrev(p.id, p.article, p.name, p.image, p.priceNew, p.priceOld, p.discount, p.createDate, p.dateUpdate, p.type, p.length, p.width, p.height, p.weight, p.volume, p.actual, p.link)
             FROM Pinskdrev p
             WHERE p.actual = true
             AND p.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Pinskdrev as p2 where p2.article = p.article) > 1
             ORDER BY p.id ASC
             LIMIT :limit
            """)
    List<Pinskdrev> getChangedItems(Date fromDate, Date toDate, Integer limit);
}
