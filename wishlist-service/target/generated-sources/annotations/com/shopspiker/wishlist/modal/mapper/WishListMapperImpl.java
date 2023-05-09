package com.shopspiker.wishlist.modal.mapper;

import com.shopspiker.wishlist.jpa.entity.WishList;
import com.shopspiker.wishlist.modal.WishListModal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T19:36:26+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class WishListMapperImpl implements WishListMapper {

    @Override
    public WishListModal toWishListDto(WishList request) {
        if ( request == null ) {
            return null;
        }

        WishListModal.WishListModalBuilder wishListModal = WishListModal.builder();

        wishListModal.id( request.getId() );
        wishListModal.siteId( request.getSiteId() );
        wishListModal.userId( request.getUserId() );
        List<String> list = request.getProductIds();
        if ( list != null ) {
            wishListModal.productIds( new ArrayList<String>( list ) );
        }
        wishListModal.auditSection( request.getAuditSection() );

        return wishListModal.build();
    }

    @Override
    public List<WishListModal> toWishListDto(List<WishList> list) {
        if ( list == null ) {
            return null;
        }

        List<WishListModal> list1 = new ArrayList<WishListModal>( list.size() );
        for ( WishList wishList : list ) {
            list1.add( toWishListDto( wishList ) );
        }

        return list1;
    }

    @Override
    public WishList toWishListEntity(WishListModal request) {
        if ( request == null ) {
            return null;
        }

        WishList wishList = new WishList();

        wishList.setId( request.getId() );
        wishList.setSiteId( request.getSiteId() );
        wishList.setUserId( request.getUserId() );
        List<String> list = request.getProductIds();
        if ( list != null ) {
            wishList.setProductIds( new ArrayList<String>( list ) );
        }

        return wishList;
    }

    @Override
    public WishList updateWishListEntity(WishListModal request, WishList WishList) {
        if ( request == null ) {
            return WishList;
        }

        WishList.setId( request.getId() );
        WishList.setSiteId( request.getSiteId() );
        WishList.setUserId( request.getUserId() );
        if ( WishList.getProductIds() != null ) {
            List<String> list = request.getProductIds();
            if ( list != null ) {
                WishList.getProductIds().clear();
                WishList.getProductIds().addAll( list );
            }
            else {
                WishList.setProductIds( null );
            }
        }
        else {
            List<String> list = request.getProductIds();
            if ( list != null ) {
                WishList.setProductIds( new ArrayList<String>( list ) );
            }
        }

        return WishList;
    }
}
