package com.shopspiker.wishlist.service;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;
import com.shopspiker.wishlist.jpa.entity.WishList;
import com.shopspiker.wishlist.modal.WishListModal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Locale;

public interface WishListService {
    public List<ProductQuickViewModal> getByUser(String userId, String siteId, Locale locale);
    public Page<WishListModal> get(Specification<WishList> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    public WishListModal addItemToWishList(String productId, String userId, String siteId);
    public WishListModal removeItemToWishList(String productId, String userId, String siteId);

    public WishList create(String userId, String siteId);
    public WishList getByUserIdAndSiteId(String userId, String siteId);
}
