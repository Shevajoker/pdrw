package com.pdrw.pdrw.angstrem.repository;

import com.pdrw.pdrw.angstrem.model.Angstrem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AngstremRepository extends JpaRepository<Angstrem, UUID> {

    List<Angstrem> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT t.type FROM Angstrem as t group by t.type")
    List<String> findAllTypes();

    List<Angstrem> findByTypeLike(String article);

    @Query("SELECT round (AVG(t.priceNew), 2) FROM Angstrem as t WHERE t.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE Angstrem SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countAngstremByActualTrue();

    @Query("SELECT round (AVG(t.priceNew), 2) FROM Angstrem as t WHERE t.actual = true and t.type like :type")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT t.article FROM Angstrem as t group by t.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link) FROM Angstrem as t WHERE t.actual = true AND t.type like :type ORDER BY t.priceNew ASC LIMIT 1")
    Optional<Angstrem> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link) FROM Angstrem as t WHERE t.actual = true AND t.type like :type ORDER BY t.priceNew DESC LIMIT 1")
    Optional<Angstrem> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Angstrem as t
             WHERE t.actual = true
             AND t.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Angstrem as t2 where t2.article = t.article) = 1
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Angstrem> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Angstrem as t
             WHERE t.actual = true
             AND t.dateUpdate < :date
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Angstrem> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Angstrem as t
             WHERE t.actual = true
             AND t.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Angstrem as t2 where t2.article = t.article) > 1
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Angstrem> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.angstrem.model.Angstrem(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Angstrem as t
             WHERE t.article = :article
             ORDER BY t.dateUpdate DESC
             LIMIT 2
            """)
    List<Angstrem> getItemWithPrevious(String article);
}
