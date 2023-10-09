package backend.shop.com.multiplexshop.domain.products.entity;

import lombok.Getter;

@Getter
public enum Categories {

    FOOD("음식"),
    STUFF("잡화");

    private final String  description;

    Categories(String description) {
        this.description = description;
    }
}

