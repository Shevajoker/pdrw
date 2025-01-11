package com.pdrw.pdrw.pinskdrevby.repository;

import com.pdrw.pdrw.pinskdrevby.model.ArchivePinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.model.ArchivePinskdrevByDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArchivePinskdrevByRepository extends JpaRepository<ArchivePinskdrevBy, UUID> {

    @Query("select a from ArchivePinskdrevBy a order by a.age asc, a.url asc")
    List<ArchivePinskdrevBy> findAllOrder();

    @Query("select '001' as id, a.age, a.url, 'des' as description from ArchivePinskdrevBy a group by a.age, a.url")
    List<String> findAllUrls();

    @Query("select a.age, a.url, count(a.url) from ArchivePinskdrevBy a group by a.age, a.url")
    List<ArchivePinskdrevByDto> findAllDtos();
}
