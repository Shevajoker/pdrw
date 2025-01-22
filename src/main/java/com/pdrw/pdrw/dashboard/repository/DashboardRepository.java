package com.pdrw.pdrw.dashboard.repository;

import com.pdrw.pdrw.dashboard.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Dashboard, Integer> {

    Optional<Dashboard> findByStoreName(String storeName);
}
