package com.shopspiker.catalogbrowse.service.impl;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;
import com.shopspiker.catalogbrowse.service.CatalogBrowseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CatalogBrowseServiceImpl implements CatalogBrowseService {
    @Override
    public List<ProductQuickViewModal> getProductsById(List<String> productIds, Locale language) {
        return productIds.stream().map(p -> {
            ProductQuickViewModal modal = new ProductQuickViewModal();
            modal.setId(p);
            return modal;
        }).collect(Collectors.toList());
    }
}
