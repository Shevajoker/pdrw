package com.pdrw.pdrw.pinskdrevru.repository;

import com.pdrw.pdrw.pinskdrevru.model.ArchivePinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.ArchivePinskdrevRuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArchivePinskdrevRuRepository extends JpaRepository<ArchivePinskdrevRu, UUID> {

    @Query("select a from ArchivePinskdrevRu a order by a.age asc, a.url asc")
    List<ArchivePinskdrevRu> findAllOrder();

    @Query("select '001' as id, a.age, a.url, 'des' as description from ArchivePinskdrevRu a group by a.age, a.url")
    List<String> findAllUrls();

    @Query("select a.age, a.url, count(a.url) from ArchivePinskdrevRu a group by a.age, a.url")
    List<ArchivePinskdrevRuDto> findAllDtos();
}
