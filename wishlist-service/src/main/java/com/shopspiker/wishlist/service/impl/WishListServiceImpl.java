package com.shopspiker.wishlist.service.impl;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;
import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.wishlist.configuration.WishListConfig;
import com.shopspiker.wishlist.jpa.entity.WishList;
import com.shopspiker.wishlist.jpa.repository.WishListRepository;
import com.shopspiker.wishlist.modal.WishListModal;
import com.shopspiker.wishlist.modal.mapper.WishListMapper;
import com.shopspiker.wishlist.service.WishListService;
import com.shopspiker.wishlist.service.provider.CatalogBrowseServiceProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListRepository repository;

    @Autowired
    private WishListConfig wishListConfig;

    @Autowired
    private WishListMapper mapper;

    @Autowired
    private CatalogBrowseServiceProvider catalogBrowseServiceProvider;

    @Override
    public List<ProductQuickViewModal> getByUser(String userId, String siteId, Locale language) {
        WishList wishList = getByUserIdAndSiteId(userId, siteId);
        if (CollectionUtils.isEmpty(wishList.getProductIds()))
            return new ArrayList<>();

        return catalogBrowseServiceProvider.getProductsById(wishList.getProductIds(), language);
    }

    @Override
    public Page<WishListModal> get(Specification<WishList> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc")) ? Sort.Direction.ASC
                        : Sort.Direction.DESC, sortBy));
        Page<WishList> page = repository.findAll(spec, pageable);
        return new PageImpl<>(mapper.toWishListDto(page.getContent()), pageable,
                page.getTotalElements());
    }

    @Override
    public WishListModal addItemToWishList(String productId, String userId, String siteId) {
        WishList wishList = getByUserIdAndSiteId(userId, siteId);

        if (wishList.getProductIds().size() >= wishListConfig.getMaxItems()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "MAX_ITEM_LIMIT",
                    "Max item limit reached");
        }
        if (!wishList.getProductIds().contains(productId)) {
            wishList.getProductIds().add(productId);
            wishList = repository.save(wishList);
        }
        return mapper.toWishListDto(wishList);
    }

    @Override
    public WishListModal removeItemToWishList(String productId, String userId, String siteId) {
        WishList wishList = getByUserIdAndSiteId(userId, siteId);

        if (wishList.getProductIds().contains(productId)) {
            wishList.getProductIds().remove(productId);
            wishList = repository.save(wishList);
        }
        return mapper.toWishListDto(wishList);
    }

    @Override
    public WishList create(String userId, String siteId) {
        WishList wishList = new WishList();
        wishList.setUserId(userId);
        wishList.setSiteId(siteId);
        return repository.save(wishList);
    }

    @Override
    public WishList getByUserIdAndSiteId(String userId, String siteId) {
        WishList wishList = repository.findByUserIdAndSiteId(userId, siteId);
        if (wishList == null) {
            wishList = create(userId, siteId);
        }
        return wishList;
    }
}
