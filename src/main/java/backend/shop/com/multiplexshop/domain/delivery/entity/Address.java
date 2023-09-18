package backend.shop.com.multiplexshop.domain.delivery.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
@Embeddable
@Getter
public class Address {

    private String shippingAddress;
    private Integer zipcode;

    protected Address(){
    }
    @Builder
    public Address(String shippingAddress,Integer zipcode){
        this.shippingAddress = shippingAddress;
        this.zipcode = zipcode;
    }
    @Builder
    public static Address createAddress(){
        return Address.builder()
                .shippingAddress("임시주소")
                .zipcode(0000)
                .build();
    }
}
