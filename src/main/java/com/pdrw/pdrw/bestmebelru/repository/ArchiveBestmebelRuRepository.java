package com.pdrw.pdrw.bestmebelru.repository;

import com.pdrw.pdrw.bestmebelru.model.ArchiveBestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.ArchiveBestmebelRuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArchiveBestmebelRuRepository extends JpaRepository<ArchiveBestmebelRu, UUID> {

    @Query("select a from ArchiveBestmebelRu a order by a.age asc, a.url asc")
    List<ArchiveBestmebelRu> findAllOrder();

    @Query("select '001' as id, a.age, a.url, 'des' as description from ArchiveBestmebelRu a group by a.age, a.url")
    List<String> findAllUrls();

    @Query("select a.age, a.url, count(a.url) from ArchiveBestmebelRu a group by a.age, a.url")
    List<ArchiveBestmebelRuDto> findAllDtos();
}
