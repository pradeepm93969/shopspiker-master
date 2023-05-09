package com.shopspiker.wishlist.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@Data
@PropertySource("classpath:wishlist-config.properties")
public class WishListConfig {

    @Value("${maxItems}")
    public int maxItems;

}
