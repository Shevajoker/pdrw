package com.pdrw.pdrw.pinskdrev.repository;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
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
}
