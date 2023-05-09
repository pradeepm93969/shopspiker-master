package com.shopspiker.catalogbrowse.service;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;

import java.util.List;
import java.util.Locale;

public interface CatalogBrowseService {
    List<ProductQuickViewModal> getProductsById(List<String> productIds, Locale language);
}
