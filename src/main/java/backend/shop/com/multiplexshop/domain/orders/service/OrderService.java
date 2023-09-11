package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
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

import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;

// 주문 생성하기
    public Orders save(OrderProductsRequestDTO dto){
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("Member not found"));
        Products products = productsRepository.findById(dto.getProductId())
                .orElseThrow(()->new IllegalArgumentException("Products not found"));
        List<OrderProducts> orderProducts = new ArrayList<>();
        OrderProducts orderProduct = OrderProducts.createOrderProducts(products, dto.getOrderCount());
        orderProducts.add(orderProduct);
        Orders order = Orders.createOrders(member, orderProducts);
        return ordersRepository.save(order);
    }
}
