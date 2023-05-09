package com.shopspiker.wishlist.modal;

import com.shopspiker.common.jpa.entity.AuditSection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListModal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String siteId;

    private String userId;

    @Builder.Default
    private List<String> productIds = new ArrayList<>();

    private AuditSection auditSection = new AuditSection();
}
