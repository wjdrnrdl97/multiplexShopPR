package backend.shop.com.multiplexshop.domain.delivery.entity;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    READY("READY"),
    COMPLETE("COMPLETE");

    private final String label;

    DeliveryStatus(String label){
        this.label = label;
    }
    public String label(){
        return label;
    }
}
