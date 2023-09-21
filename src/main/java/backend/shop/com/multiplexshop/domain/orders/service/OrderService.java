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

        // 회원번호를 이용하여 회원 조회
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 상품번호 리스트를 받아 여러 상품 조회하여 리스트
        List<Long> productId = request.getProductId();
        List<Products> productsList = productsRepository.findAllById(productId);

        // 상품리스트와 회원을 받아 주문 생성
        Orders createOrder = Orders.createOrder(member, productsList);
        Orders saveOrders = ordersRepository.save(createOrder);

        // 주문과 상품리스트를 받아 상품리스트 수 만큼 주문상품 생성
        for (Products products : productsList){
            OrderProducts createOrderProducts = OrderProducts.createOrderProducts(saveOrders, products);
            orderProductsRepository.save(createOrderProducts);
        }

        // 주문를 받아 배송 생성
        Delivery delivery = Delivery.createDelivery(saveOrders);
        deliveryRepository.save(delivery);

        return OrderResponseDTO.of(saveOrders);
    }

    // 주문번호를 입력받아 주문상품 상세 조회
    public List<OrderProductsResponseDTO> findAllByOrderId(Long id){
        List<OrderProducts> findOrderProducts = ordersRepository.findByOrdersIdAll(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        List<OrderProductsResponseDTO> responseDTOList = findOrderProducts.stream()
                .map(OrderProductsResponseDTO::of).toList();
        return responseDTOList;
    }

    @Transactional
    public void deleteByOrdersIds(Long id){
        Delivery delivery = deliveryRepository.findByOrderId(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));

        if(delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE){
            throw new IllegalArgumentException("This order has already been completed.");
        }

        Orders findChangeOrderByOrderId = ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        findChangeOrderByOrderId.changeOrderStatus();
        ordersRepository.save(findChangeOrderByOrderId);
    }

}



