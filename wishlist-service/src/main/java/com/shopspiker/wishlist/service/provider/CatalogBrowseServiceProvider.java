package com.shopspiker.wishlist.service.provider;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;
import com.shopspiker.catalogbrowse.service.CatalogBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CatalogBrowseServiceProvider {

    @Autowired
    private CatalogBrowseService catalogBrowseService;

    public List<ProductQuickViewModal> getProductsById(List<String> productIds, Locale language) {
        return catalogBrowseService.getProductsById(productIds, language);
    }
}
