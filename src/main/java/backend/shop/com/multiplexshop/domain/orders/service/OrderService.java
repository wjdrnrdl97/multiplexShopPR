package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
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

import java.util.ArrayList;
import java.util.List;


import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;
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
    private final CartProductsRepository cartProductsRepository;

    // 멤버번호, 상품번호리스트을 입력받아 주문생성 및 주문상품 생성
    public OrderResponseDTO save(OrderRequestDTO request){

        // 회원번호를 이용하여 회원 조회
        Member findMemberById = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 회원을 이용하여 주문 생성하기
        Orders createOrderByMember = Orders.createOrder(findMemberById);
        Orders savedOrder = ordersRepository.save(createOrderByMember);

        List<OrderProductsRequestDTO> productWithCountList = request.getProductWithCount();
        for(OrderProductsRequestDTO dto : productWithCountList) {

            // 상품번호 받아 상품 조회
            Products findProductById = productsRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            OrderProducts createOrderProduct = OrderProducts
                                            .createOrderProducts(createOrderByMember, findProductById, dto.getCount());
            orderProductsRepository.save(createOrderProduct);
            }
            // 주문를 받아 배송 생성
            Delivery delivery = Delivery.createDelivery(savedOrder);
            deliveryRepository.save(delivery);

        return OrderResponseDTO.of(savedOrder);
    }

    public List<OrderProductsResponseDTO> findOrderWithProductsByMemberID(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<OrderProducts> findAllByMember = orderProductsRepository.findAllByMember(findMember);
        List<OrderProductsResponseDTO> findAllByMemberOfDTO = findAllByMember
                                                        .stream().map(OrderProductsResponseDTO::of).toList();
        return findAllByMemberOfDTO;
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

    @Transactional
    public List<OrderProductsResponseDTO> findAllByOrderId(Long id){
        List<OrderProducts> findOrderProducts = orderProductsRepository.findByOrdersIdAll(id);
        List<OrderProductsResponseDTO> responseDTOList = findOrderProducts.stream()
                .map(OrderProductsResponseDTO::of).toList();
        return responseDTOList;
    }
}



