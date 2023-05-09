package com.shopspiker.wishlist.jpa.repository;

import com.shopspiker.wishlist.jpa.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WishListRepository extends JpaRepository<WishList, String>, JpaSpecificationExecutor<WishList> {
    WishList findByUserIdAndSiteId(String userId, String siteId);
}
