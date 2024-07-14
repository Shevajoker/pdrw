package com.pdrw.pdrw.hoff.repository;

import com.pdrw.pdrw.hoff.model.HoffItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HoffItemRepository extends JpaRepository<HoffItem, UUID> {

    List<HoffItem> findByArticleOrderByDateUpdateDesc(String article);
}
