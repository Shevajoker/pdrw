package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.dashboard.entity.*;
import com.pdrw.pdrw.dashboard.repository.DashboardRepository;
import com.pdrw.pdrw.dashboard.service.DashboardService;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.triya.model.TriyaRu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

    @Transactional
    public void createDashboardPinskdrevRu(List<PinskdrevRu> pinskdrevRuList) {

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
                    .max(Comparator.comparing(pinskdrevRu -> getDiscountPercentage(pinskdrevRu.getPriceOld(), pinskdrevRu.getDiscount())))
                    .orElseThrow();
            dashboard.setMaxDiscountProduct(new MaxDiscountProduct(getDiscountPercentage(max.getPriceOld(), max.getDiscount()), max.getLink()));

            PinskdrevRu min = pinskdrevRuList.stream()
                    .min(Comparator.comparing(pinskdrevRu -> getDiscountPercentage(pinskdrevRu.getPriceOld(), pinskdrevRu.getDiscount())))
                    .orElseThrow();
            dashboard.setMinDiscountProduct(new MinDiscountProduct(getDiscountPercentage(min.getPriceOld(), min.getDiscount()), min.getLink()));

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
        }
    }

    @Transactional
    public void createDashboardTriya(List<TriyaRu> triyaRuList) {

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
                    .max(Comparator.comparing(pinskdrevRu -> getDiscountPercentage(pinskdrevRu.getPriceOld(), pinskdrevRu.getDiscount())))
                    .orElseThrow();
            dashboard.setMaxDiscountProduct(new MaxDiscountProduct(getDiscountPercentage(max.getPriceOld(), max.getDiscount()), max.getLink()));

            TriyaRu min = triyaRuList.stream()
                    .min(Comparator.comparing(pinskdrevRu -> getDiscountPercentage(pinskdrevRu.getPriceOld(), pinskdrevRu.getDiscount())))
                    .orElseThrow();
            dashboard.setMinDiscountProduct(new MinDiscountProduct(getDiscountPercentage(min.getPriceOld(), min.getDiscount()), min.getLink()));

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
        }
    }

    private BigDecimal getDiscountPercentage(BigDecimal totalPriceWithOutDiscount, BigDecimal discount) {
        if (totalPriceWithOutDiscount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Защита от деления на ноль
        }
        return discount.divide(totalPriceWithOutDiscount, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
