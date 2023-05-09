package com.shopspiker.wishlist.modal.mapper;

import com.shopspiker.wishlist.jpa.entity.WishList;
import com.shopspiker.wishlist.modal.WishListModal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishListMapper {

    WishListModal toWishListDto(WishList request);

    List<WishListModal> toWishListDto(List<WishList> list);

    @Mapping(target = "auditSection", ignore = true)
    WishList toWishListEntity(WishListModal request);

    @Mapping(target = "auditSection", ignore = true)
    WishList updateWishListEntity(WishListModal request, @MappingTarget WishList WishList);

}
