package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.dto.DashboardDTO;
import com.semillero.ecosistemas.model.*;
import com.semillero.ecosistemas.repository.IProductRepository;
import com.semillero.ecosistemas.repository.IPublicationRepository;
import com.semillero.ecosistemas.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService implements IDashboardService {
    @Autowired
    private IPublicationRepository publicationRepository;
    @Autowired
    private ISupplierRepository supplierRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Map<String, Object> getDashboardInfo() {
        Map<String, Long> providersByCategory = getSupplierCountByCategory();
        List<DashboardDTO> publicationsData = getPublicationsData();
        Map<String, Long> productsData = getCreatedProducts();

        Map<String, Object> dashboardInfo = new LinkedHashMap<>();
        dashboardInfo.put("Nuevos productos creados", productsData);
        dashboardInfo.put("Proveedores por categoria", providersByCategory);
        dashboardInfo.put("Publicaciones", publicationsData);

        return dashboardInfo;
    }

    // Lista de Publicaciones (Titulo, Fecha, Views)
    @Override
    public List<DashboardDTO> getPublicationsData() {
//        List<Publication> publicationList = publicationRepository.findByDeletedFalse();
        List<Publication> publicationList = publicationRepository.findAll();
        List<DashboardDTO> dashboardDTOList = new ArrayList<>();

        for (Publication item : publicationList) {
            DashboardDTO newDashboardDTOItem = DashboardDTO.builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .creationDate(item.getCreationDate())
                    .views(item.getViewCount())
                    .build();

            dashboardDTOList.add(newDashboardDTOItem);
        }
        return dashboardDTOList;
    }

    // Proveedores por categoria MODIFICAR A PRODUCTOS POR CATEGORIA ***********************************
    @Override
    public Map<String, Long> getSupplierCountByCategory() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .flatMap(supplier -> supplier.getProductList().stream())
                .collect(Collectors.groupingBy(product -> product.getCategory().getName(), Collectors.counting()));
    }

    // Nuevos productos creados, aprobados, en revision, denegados
    @Override
    public Map<String, Long> getCreatedProducts() {
        List<Product> products = productRepository.findByDeletedFalse();

        Map<Status, Long> statusCounts = products.stream()
                .collect(Collectors.groupingBy(Product::getStatus, Collectors.counting()));

        Map<String, Long> productInfo = new LinkedHashMap<>();
        productInfo.put("Total", (long) products.size());
        productInfo.put("Aceptado", statusCounts.getOrDefault(Status.ACEPTADO, 0L));
        productInfo.put("Revision inicial", statusCounts.getOrDefault(Status.REVISION_INICIAL, 0L));
        productInfo.put("Denegado", statusCounts.getOrDefault(Status.DENEGADO, 0L));

        return productInfo;
    }
}
