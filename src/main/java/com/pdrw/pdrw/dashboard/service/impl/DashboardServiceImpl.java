package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.repository.BestmebelRuRepository;
import com.pdrw.pdrw.dashboard.entity.*;
import com.pdrw.pdrw.dashboard.repository.DashboardRepository;
import com.pdrw.pdrw.dashboard.service.DashboardService;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final TriyaRuRepository triyaRuRepository;
    private final BestmebelRuRepository bestmebelRuRepository;

    @Override
    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void createDashboardPinskdrevRu() {
        List<PinskdrevRu> pinskdrevRuList = pinskdrevRuRepository.findAll();
        if (!pinskdrevRuList.isEmpty()) {
            Dashboard dashboard = new Dashboard();

            dashboardRepository.findByStoreName("PinskdrevRu")
                    .ifPresent(dashboardExist -> dashboard.setId(dashboardExist.getId()));

            dashboard.setStoreName("PinskdrevRu");
            dashboard.setTotalProducts(pinskdrevRuList.size());

            BigDecimal totalPriceWithOutDiscount = pinskdrevRuList.stream()
                    .map(PinskdrevRu::getPriceOld)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithoutDiscounts(totalPriceWithOutDiscount);

            BigDecimal totalPriceWithDiscount = pinskdrevRuList.stream()
                    .map(PinskdrevRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithDiscounts(totalPriceWithDiscount);

            BigDecimal averageDiscount = totalPriceWithDiscount.divide(totalPriceWithOutDiscount, 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"));
            dashboard.setAverageDiscountPercentage(averageDiscount);

            BigDecimal priceMode = pinskdrevRuList.stream().map(PinskdrevRu::getPriceNew)
                    .collect(Collectors.groupingBy(number -> number, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(BigDecimal.ZERO);
            dashboard.setPriceMode(priceMode);

            BigDecimal averagePrice = pinskdrevRuList.stream()
                    .map(PinskdrevRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(pinskdrevRuList.size()), 2, BigDecimal.ROUND_HALF_UP);
            dashboard.setAveragePrice(averagePrice);

            String mostPopularCategoryName = pinskdrevRuList.stream().map(PinskdrevRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long mostPopularCategoryCount = pinskdrevRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getType().equals(mostPopularCategoryName)).count();
            dashboard.setMostPopularCategory(new MostPopularCategory(mostPopularCategoryName, (int) mostPopularCategoryCount));

            String leastPopularCategoryName = pinskdrevRuList.stream().map(PinskdrevRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long leastPopularCategoryCount = pinskdrevRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getType().equals(leastPopularCategoryName)).count();
            dashboard.setLeastPopularCategory(new LeastPopularCategory(leastPopularCategoryName, (int) leastPopularCategoryCount));

            PinskdrevRu max = pinskdrevRuList.stream()
                    .max(Comparator.comparing(PinskdrevRu::getDiscount))
                    .orElseThrow();
            dashboard.setMaxDiscountProduct(new MaxDiscountProduct(max.getDiscount(), max.getLink()));

            PinskdrevRu min = pinskdrevRuList.stream()
                    .filter(pinskdrevRu -> pinskdrevRu.getDiscount().compareTo(BigDecimal.ZERO) != 0 )
                    .min(Comparator.comparing(PinskdrevRu::getDiscount))
                    .orElseThrow();
            dashboard.setMinDiscountProduct(new MinDiscountProduct(min.getDiscount(), min.getLink()));

            long count = pinskdrevRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getDiscount().equals(BigDecimal.ZERO)).count();

            BigDecimal discountedProductsPercentage = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(pinskdrevRuList.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
            dashboard.setDiscountedProductsPercentage(discountedProductsPercentage);
            dashboard.setTotalDeletedProducts(0);
            dashboard.setTotalUpdatedProducts(0);

            Date lastCreateDate = pinskdrevRuList.stream()
                    .map(PinskdrevRu::getCreateDate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);
            Date lastUpdateDate = pinskdrevRuList.stream()
                    .map(PinskdrevRu::getDateUpdate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);

            dashboard.setLastDateCreate(lastCreateDate);
            dashboard.setLastDateUpdate(lastUpdateDate);

            dashboardRepository.save(dashboard);
            log.info("Update dashboard PinskdrevRu");
        }
    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void createDashboardTriya() {
        List<TriyaRu> triyaRuList = triyaRuRepository.findAll();
        if (!triyaRuList.isEmpty()) {
            Dashboard dashboard = new Dashboard();

            dashboardRepository.findByStoreName("TriyaRu")
                    .ifPresent(dashboardExist -> dashboard.setId(dashboardExist.getId()));

            dashboard.setStoreName("TriyaRu");
            dashboard.setTotalProducts(triyaRuList.size());

            BigDecimal totalPriceWithOutDiscount = triyaRuList.stream()
                    .map(TriyaRu::getPriceOld)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithoutDiscounts(totalPriceWithOutDiscount);

            BigDecimal totalPriceWithDiscount = triyaRuList.stream()
                    .map(TriyaRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithDiscounts(totalPriceWithDiscount);

            BigDecimal averageDiscount = totalPriceWithDiscount.divide(totalPriceWithOutDiscount, 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"));
            dashboard.setAverageDiscountPercentage(averageDiscount);

            BigDecimal priceMode = triyaRuList.stream().map(TriyaRu::getPriceNew)
                    .collect(Collectors.groupingBy(number -> number, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(BigDecimal.ZERO);
            dashboard.setPriceMode(priceMode);

            BigDecimal averagePrice = triyaRuList.stream()
                    .map(TriyaRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(triyaRuList.size()), 2, BigDecimal.ROUND_HALF_UP);
            dashboard.setAveragePrice(averagePrice);

            String mostPopularCategoryName = triyaRuList.stream().map(TriyaRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long mostPopularCategoryCount = triyaRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getType().equals(mostPopularCategoryName)).count();
            dashboard.setMostPopularCategory(new MostPopularCategory(mostPopularCategoryName, (int) mostPopularCategoryCount));

            String leastPopularCategoryName = triyaRuList.stream().map(TriyaRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long leastPopularCategoryCount = triyaRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getType().equals(leastPopularCategoryName)).count();
            dashboard.setLeastPopularCategory(new LeastPopularCategory(leastPopularCategoryName, (int) leastPopularCategoryCount));

            TriyaRu max = triyaRuList.stream()
                    .max(Comparator.comparing(TriyaRu::getDiscount))
                    .orElseThrow();
            dashboard.setMaxDiscountProduct(new MaxDiscountProduct(max.getDiscount(), max.getLink()));

            TriyaRu min = triyaRuList.stream()
                    .filter(triyaRu -> triyaRu.getDiscount().compareTo(BigDecimal.ZERO) != 0 )
                    .min(Comparator.comparing(TriyaRu::getDiscount))
                    .orElseThrow();
            dashboard.setMinDiscountProduct(new MinDiscountProduct(min.getDiscount(), min.getLink()));

            long count = triyaRuList.stream().filter(pinskdrevRu -> pinskdrevRu.getDiscount().equals(BigDecimal.ZERO)).count();

            BigDecimal discountedProductsPercentage = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(triyaRuList.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
            dashboard.setDiscountedProductsPercentage(discountedProductsPercentage);
            dashboard.setTotalDeletedProducts(0);
            dashboard.setTotalUpdatedProducts(0);

            Date lastCreateDate = triyaRuList.stream()
                    .map(TriyaRu::getCreateDate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);
            Date lastUpdateDate = triyaRuList.stream()
                    .map(TriyaRu::getDateUpdate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);

            dashboard.setLastDateCreate(lastCreateDate);
            dashboard.setLastDateUpdate(lastUpdateDate);

            dashboardRepository.save(dashboard);
            log.info("Update dashboard Triya");
        }
    }

    @Override
    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void createDashboardBestmebelRu() {
        List<BestmebelRu> bestmebelRuList = bestmebelRuRepository.findAll();
        if (!bestmebelRuList.isEmpty()) {
            Dashboard dashboard = new Dashboard();

            dashboardRepository.findByStoreName("BestmebelRu")
                    .ifPresent(dashboardExist -> dashboard.setId(dashboardExist.getId()));

            dashboard.setStoreName("BestmebelRu");
            dashboard.setTotalProducts(bestmebelRuList.size());

            BigDecimal totalPriceWithOutDiscount = bestmebelRuList.stream()
                    .map(BestmebelRu::getPriceOld)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithoutDiscounts(totalPriceWithOutDiscount);

            BigDecimal totalPriceWithDiscount = bestmebelRuList.stream()
                    .map(BestmebelRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dashboard.setTotalPriceWithDiscounts(totalPriceWithDiscount);

            BigDecimal averageDiscount = totalPriceWithDiscount.divide(totalPriceWithOutDiscount, 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"));
            dashboard.setAverageDiscountPercentage(averageDiscount);

            BigDecimal priceMode = bestmebelRuList.stream().map(BestmebelRu::getPriceNew)
                    .collect(Collectors.groupingBy(number -> number, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(BigDecimal.ZERO);
            dashboard.setPriceMode(priceMode);

            BigDecimal averagePrice = bestmebelRuList.stream()
                    .map(BestmebelRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(bestmebelRuList.size()), 2, BigDecimal.ROUND_HALF_UP);
            dashboard.setAveragePrice(averagePrice);

            String mostPopularCategoryName = bestmebelRuList.stream().map(BestmebelRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long mostPopularCategoryCount = bestmebelRuList.stream().filter(bestmebelRu -> bestmebelRu.getType().equals(mostPopularCategoryName)).count();
            dashboard.setMostPopularCategory(new MostPopularCategory(mostPopularCategoryName, (int) mostPopularCategoryCount));

            String leastPopularCategoryName = bestmebelRuList.stream().map(BestmebelRu::getType)
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);

            long leastPopularCategoryCount = bestmebelRuList.stream().filter(bestmebelRu -> bestmebelRu.getType().equals(leastPopularCategoryName)).count();
            dashboard.setLeastPopularCategory(new LeastPopularCategory(leastPopularCategoryName, (int) leastPopularCategoryCount));

            BestmebelRu max = bestmebelRuList.stream()
                    .max(Comparator.comparing(BestmebelRu::getDiscount))
                    .orElseThrow();
            dashboard.setMaxDiscountProduct(new MaxDiscountProduct(max.getDiscount(), max.getLink()));

            BestmebelRu min = bestmebelRuList.stream()
                    .filter(bestmebelRu -> bestmebelRu.getDiscount().compareTo(BigDecimal.ZERO) != 0)
                    .min(Comparator.comparing(BestmebelRu::getDiscount))
                    .orElseThrow();
            dashboard.setMinDiscountProduct(new MinDiscountProduct(min.getDiscount(), min.getLink()));

            long count = bestmebelRuList.stream().filter(bestmebelRu -> bestmebelRu.getDiscount().equals(BigDecimal.ZERO)).count();

            BigDecimal discountedProductsPercentage = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(bestmebelRuList.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
            dashboard.setDiscountedProductsPercentage(discountedProductsPercentage);
            dashboard.setTotalDeletedProducts(0);
            dashboard.setTotalUpdatedProducts(0);

            Date lastCreateDate = bestmebelRuList.stream()
                    .map(BestmebelRu::getCreateDate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);
            Date lastUpdateDate = bestmebelRuList.stream()
                    .map(BestmebelRu::getDateUpdate)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder()).orElse(null);

            dashboard.setLastDateCreate(lastCreateDate);
            dashboard.setLastDateUpdate(lastUpdateDate);

            dashboardRepository.save(dashboard);
            log.info("Update dashboard BestmebelRu");
        }
    }

    private BigDecimal getDiscountPercentage(BigDecimal totalPriceWithOutDiscount, BigDecimal discount) {
        if (totalPriceWithOutDiscount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Защита от деления на ноль
        }
        return discount.divide(totalPriceWithOutDiscount, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
