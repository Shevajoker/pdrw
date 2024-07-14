package com.pdrw.pdrw.hoff.service.impl;

import com.pdrw.pdrw.hoff.model.HoffItem;
import com.pdrw.pdrw.hoff.repository.HoffItemRepository;
import com.pdrw.pdrw.hoff.service.HoffItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoffItemServiceImpl implements HoffItemService {

    private final HoffItemRepository hoffItemRepository;

    public HoffItemServiceImpl(HoffItemRepository hoffItemRepository) {
        this.hoffItemRepository = hoffItemRepository;
    }


    @Override
    public HoffItem createItem(HoffItem item) {
        return hoffItemRepository.save(item);
    }

    @Override
    public HoffItem findById(UUID id) {
        return hoffItemRepository.findById(id).orElseThrow();
    }

    @Override
    public List<HoffItem> findAll() {
        return hoffItemRepository.findAll();
    }

    @Override
    public List<HoffItem> findByArticleOrderByDateUpdateDesc(String article) {
        return hoffItemRepository.findByArticleOrderByDateUpdateDesc(article);
    }
}
