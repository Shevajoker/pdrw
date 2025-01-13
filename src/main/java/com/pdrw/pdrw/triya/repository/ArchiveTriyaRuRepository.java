package com.pdrw.pdrw.triya.repository;

import com.pdrw.pdrw.triya.model.ArchiveTriyaRu;
import com.pdrw.pdrw.triya.model.ArchiveTriyaRuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArchiveTriyaRuRepository extends JpaRepository<ArchiveTriyaRu, UUID> {

    @Query("select a from ArchiveTriyaRu a order by a.age asc, a.url asc")
    List<ArchiveTriyaRu> findAllOrder();

    @Query("select '001' as id, a.age, a.url, 'des' as description from ArchiveTriyaRu a group by a.age, a.url")
    List<String> findAllUrls();

    @Query("select a.age, a.url, count(a.url) from ArchiveTriyaRu a group by a.age, a.url")
    List<ArchiveTriyaRuDto> findAllDtos();
}
