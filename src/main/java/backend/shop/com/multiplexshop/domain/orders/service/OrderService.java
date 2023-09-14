package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
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
    private final DeliveryRepository deliveryRepository;

    // 리퀘스트 : 멤버번호, 상품번호, 수량
    public OrderResponseDTO save(OrderRequestDTO request){

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<Long> productId = request.getProductId();
        List<Products> productsList = productsRepository.findAllById(productId);
        Orders createOrder = Orders.createOrder(member, productsList);
        Orders saveOrders = ordersRepository.save(createOrder);
        for (Products products : productsList){
            OrderProducts.addOrderProducts(saveOrders,products);
        }
        return OrderResponseDTO.of(saveOrders);
    }
    public OrderResponseDTO findbyOrdersId(Long id){
        ordersRepository.findById(id).get();
        return null;
    }
    @Transactional
    public void deleteByOrdersIds(Long id){
        Delivery delivery = deliveryRepository.findByOrdersId(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE){
            throw new IllegalArgumentException("이미 배송이 완료된 주문입니다.");
        }
        ordersRepository.changeOrderStatus(id);
    }


}



