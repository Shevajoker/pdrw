package com.pdrw.pdrw.repository;

import com.pdrw.pdrw.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
