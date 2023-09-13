package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;

    // 리퀘스트 : 멤버번호, 상품번호, 수량
    public OrderResponseDTO save(OrderRequestDTO request){

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<Long> productId = request.getProductId();
        List<Products> productsList = productsRepository.findAllById(productId);
        Orders createOrder = Orders.createOrder(member, productsList);
        Orders saveOrders = ordersRepository.save(createOrder);
        return OrderResponseDTO.of(saveOrders);

//        //멤버 상품별 아이디를 통해 엔티티를 추출
//        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("잘못됬음"));
//        List<Long> productId = request.getProductId().stream()
//                .toList();
//        List<Products> products = productsRepository.findAllById(productId);
//        [1,2,3]
//        Orders orders = request.toEntity(request, member);
//
//        Orders savedOrders = ordersRepository.save(orders);
//
//        return OrderResponseDTO.of(savedOrders);
    }

    private static void setOrderPrice(List<Products> productsList) {
        Integer sum = 0;
        for (Products product : productsList){
            sum += product.getProductPrice() * product.getOrderQuantity();
        }
    }


}



