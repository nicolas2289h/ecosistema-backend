package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.dto.DashboardDTO;

import java.util.List;
import java.util.Map;

public interface IDashboardService {
    List<DashboardDTO> getPublicationsData();
    Map<String, Long> getSupplierCountByCategory();
    Map<String, Long> getCreatedProducts();
    Map<String, Object> getDashboardInfo();
}
