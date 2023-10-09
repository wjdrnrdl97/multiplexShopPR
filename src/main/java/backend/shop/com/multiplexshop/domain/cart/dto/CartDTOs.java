package backend.shop.com.multiplexshop.domain.cart.dto;

import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartDTOs {

    @Getter
    @NoArgsConstructor
    public static class CartResponseDTO{
        private Long id;
        private Member member;

        @Builder
        public CartResponseDTO(Long id, Member member) {
            this.id = id;
            this.member = member;
        }

        public static CartResponseDTO of(Cart cart){
            return CartResponseDTO.builder()
                    .id(cart.getId())
                    .member(cart.getMember())
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CartRequestDTO {
        private Long memberId;
        private Long productsId;
        private Integer count;

        @Builder
        public CartRequestDTO(Long memberId, Long productsId, Integer count) {
            this.memberId = memberId;
            this.productsId = productsId;
            this.count = count;
        }
    }
}
