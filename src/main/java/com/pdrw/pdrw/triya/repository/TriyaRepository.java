package com.pdrw.pdrw.triya.repository;

import com.pdrw.pdrw.triya.model.Triya;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TriyaRepository extends JpaRepository<Triya, UUID> {

    List<Triya> findByArticleOrderByDateUpdateDesc(String article);

    @Query("SELECT t.type FROM Triya as t group by t.type")
    List<String> findAllTypes();

    List<Triya> findByTypeLike(String article);

    @Query("SELECT round (AVG(t.priceNew), 2) FROM Triya as t WHERE t.actual = true")
    BigDecimal getAveragePrice();

    @Modifying
    @Query("UPDATE Triya SET actual = false WHERE article = ?1")
    void markActualFalse(String article);

    Long countTriyaByActualTrue();

    @Query("SELECT round (AVG(t.priceNew), 2) FROM Triya as t WHERE t.actual = true and t.type like :type")
    BigDecimal getAveragePriceByType(String type);

    @Query("SELECT t.article FROM Triya as t group by t.article")
    List<String> findAllArticles();

    @Query("SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link) FROM Triya as t WHERE t.actual = true AND t.type like :type ORDER BY t.priceNew ASC LIMIT 1")
    Optional<Triya> findByTypeAndMinPrice(String type);

    @Query("SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link) FROM Triya as t WHERE t.actual = true AND t.type like :type ORDER BY t.priceNew DESC LIMIT 1")
    Optional<Triya> findByTypeAndMaxPrice(String type);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Triya as t
             WHERE t.actual = true
             AND t.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Triya as t2 where t2.article = t.article) = 1
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Triya> findNewCreatedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Triya as t
             WHERE t.actual = true
             AND t.dateUpdate < :date
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Triya> findNotUpdatedItems(Date date, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Triya as t
             WHERE t.actual = true
             AND t.createDate BETWEEN :fromDate and :toDate
             AND (select count(*) from Triya as t2 where t2.article = t.article) > 1
             ORDER BY t.id ASC
             LIMIT :limit
            """)
    List<Triya> getChangedItems(Date fromDate, Date toDate, Integer limit);

    @Query("""
             SELECT new com.pdrw.pdrw.triya.model.Triya(t.id, t.article, t.name, t.image, t.priceNew, t.priceOld, t.discount, t.createDate, t.dateUpdate, t.type, t.length, t.width, t.height, t.weight, t.volume, t.actual, t.link)
             FROM Triya as t
             WHERE t.article = :article
             ORDER BY t.dateUpdate DESC
             LIMIT 2
            """)
    List<Triya> getItemWithPrevious(String article);
}
