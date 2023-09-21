package backend.shop.com.multiplexshop.domain.cart.repository;

import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("장바구니 DB에서 해당 회원의 장바구니를 조회한다.")
    public void findByMember(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);
        Cart cart = Cart.createCart(savedMember);
        cartRepository.save(cart);
        //when
        Cart findCart = cartRepository.findByMember(savedMember).orElse(null);
        //then
        Assertions.assertThat(findCart.getMember()).isEqualTo(savedMember);
    }
}