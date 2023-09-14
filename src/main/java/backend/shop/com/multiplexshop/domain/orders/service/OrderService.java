package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderProductsRepository orderProductsRepository;

    // 멤버번호, 상품번호리스트을 입력받아 주문생성 및 주문상품 생성
    public OrderResponseDTO save(OrderRequestDTO request){

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Long> productId = request.getProductId();
        List<Products> productsList = productsRepository.findAllById(productId);

        Orders createOrder = Orders.createOrder(member, productsList);
        Orders saveOrders = ordersRepository.save(createOrder);

        for (Products products : productsList){
            OrderProducts createOrderProducts = OrderProducts.createOrderProducts(saveOrders, products);
            orderProductsRepository.save(createOrderProducts);
        }
        return OrderResponseDTO.of(saveOrders);
    }

    // 주문번호를 입력받아 주문상품 상세 조회
    public List<OrderProductsResponseDTO> findByOrdersIdAll(Long id){
        List<OrderProducts> findOrderProducts = ordersRepository.findByOrdersIdAll(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        List<OrderProductsResponseDTO> responseDTOList = findOrderProducts.stream()
                .map(o -> OrderProductsResponseDTO.of(o)).toList();
        return responseDTOList;
    }
    // 주문번호를 입력받아 논리적 삭제하기.
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



