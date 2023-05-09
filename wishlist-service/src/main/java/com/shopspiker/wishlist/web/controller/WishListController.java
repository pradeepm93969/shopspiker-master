package com.shopspiker.wishlist.web.controller;

import com.shopspiker.catalogbrowse.modal.ProductQuickViewModal;
import com.shopspiker.common.web.constant.HttpHeaderConstants;
import com.shopspiker.wishlist.jpa.entity.WishList;
import com.shopspiker.wishlist.modal.WishListModal;
import com.shopspiker.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/wishlist/v1")
@Tag(name = "Wish List Management")
@SecurityRequirement(name = "bearerAuth")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @GetMapping("/users/{id}/wishlist")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_WISHLIST','ROLE_MANAGE_WISHLIST','ROLE_SUPER_ADMIN') " +
            "or #userId == authentication.principal.username")
    public List<ProductQuickViewModal> getById(
            @PathVariable("id") @NotBlank(message = "id is mandatory") String userId,
            @RequestHeader(HttpHeaderConstants.SITE_HEADER) String siteId,
            @RequestHeader(HttpHeaderConstants.LOCALE_HEADER) Locale locale) {
        return wishListService.getByUser(userId, siteId, locale);
    }

    @GetMapping("/wishlists")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_WISHLIST','ROLE_MANAGE_WISHLIST','ROLE_SUPER_ADMIN')")
    public Page<WishListModal> get(
            @And({@Spec(path = "ids", params = "ids", paramSeparator = ',' , spec = In.class),
                    @Spec(path = "createdAt", params = {"createdAtGt",
                            "createdAtLt"}, spec = Between.class)}) Specification<WishList> spec,
            @Positive @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return wishListService.get(spec, pageNo - 1, pageSize, sortBy, sortDirection);
    }

    @PutMapping("/users/{id}/wishlist")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_WISHLIST','ROLE_SUPER_ADMIN') " +
            "or #userId == authentication.principal.username")
    public WishListModal addItemToWishList(
            @PathVariable("id") @NotBlank(message = "id is mandatory") String userId,
            @RequestHeader(HttpHeaderConstants.SITE_HEADER) String siteId,
            @RequestParam("productId") String productId) {
        return wishListService.addItemToWishList(productId, userId, siteId);
    }

    @DeleteMapping("/users/{id}/wishlist")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_WISHLIST','ROLE_SUPER_ADMIN') " +
            "or #userId == authentication.principal.username")
    public WishListModal removeItemToWishList(
            @PathVariable("id") @NotBlank(message = "id is mandatory") String userId,
            @RequestHeader(HttpHeaderConstants.SITE_HEADER) String siteId,
            @RequestParam("productId") String productId) {
        return wishListService.removeItemToWishList(productId, userId, siteId);
    }
}
