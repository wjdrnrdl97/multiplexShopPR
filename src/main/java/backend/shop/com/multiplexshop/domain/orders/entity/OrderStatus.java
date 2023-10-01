package backend.shop.com.multiplexshop.domain.orders.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER("ORDER"),
    CANCEL("CANCEL");

    private final String label;

    OrderStatus(String label){
        this.label = label;
    }
    public String label(){
        return label;
    }
}
