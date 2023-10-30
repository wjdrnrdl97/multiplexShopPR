package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.repository.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static backend.shop.com.multiplexshop.domain.orders.dto.OrderProductsDTOs.*;
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

    @Transactional
    public OrderResponseDTO createOrderByRequest(OrderRequestDTO request){

        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        Orders createOrderByMember = Orders.createOrder(findMember);
        Orders savedCreateOrder = ordersRepository.save(createOrderByMember);

        List<OrderProductsRequestDTO> productWithCountList = request.getProductWithCount();
        productWithCountList.stream().forEach(dto -> {
            Products findProduct = productsRepository.findAndPessimisticLockById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));


            findProduct.decreaseStockQuantity(dto.getCount());
            Products savedProductOfDecreaseStock = productsRepository.save(findProduct);

            OrderProducts createNewOrderProduct = OrderProducts
                    .createOrderProducts(createOrderByMember, savedProductOfDecreaseStock, dto.getCount());
            orderProductsRepository.save(createNewOrderProduct);

            cartProductsRepository.deleteByCartMemberAndProducts(findMember,findProduct);
        });

            Delivery createDeliveryByNewOrder = Delivery.createDelivery(savedCreateOrder);
            deliveryRepository.save(createDeliveryByNewOrder);

        return OrderResponseDTO.of(savedCreateOrder);
    }

    public List<OrderProductsResponseDTO> findOrderProductsByMemberId(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        List<OrderProducts> findAllByMember = orderProductsRepository.findAllByMember(findMember);
        List<OrderProductsResponseDTO> findAllByMemberOfDTO = findAllByMember
                                                        .stream().map(OrderProductsResponseDTO::of).toList();
        return findAllByMemberOfDTO;
    }

    @Transactional
    public void deleteByOrdersIds(Long id){
        Delivery findDeliveryByOrder = deliveryRepository.findByOrderId(id)
                .orElseThrow(() -> new IllegalArgumentException("배송정보가 없습니다."));

        if(findDeliveryByOrder.getDeliveryStatus() == DeliveryStatus.COMPLETE){
            throw new IllegalArgumentException("이미 배송이 완료된 주문입니다.");
        }

        Orders findByOrderIdFromRequest = ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        findByOrderIdFromRequest.changeOrderStatus();
        ordersRepository.save(findByOrderIdFromRequest);

        List<OrderProducts> findAllByOrderId = orderProductsRepository.findByOrdersIdAll(id);

        findAllByOrderId.stream().forEach(orderProducts -> {
            Long getCancelProductsId = orderProducts.getProducts().getId();
            Integer getCancelCount = orderProducts.getCount();

            Products productsByCancelOrder = productsRepository
                    .findAndPessimisticLockById(getCancelProductsId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

            productsByCancelOrder.increaseStockQuantity(getCancelCount);
            productsRepository.save(productsByCancelOrder);
        });
    }

    public List<OrderProductsResponseDTO> findAllByOrderId(Long id){
        List<OrderProducts> findOrderProducts = orderProductsRepository.findByOrdersIdAll(id);
        List<OrderProductsResponseDTO> entityOfResponseDTO = findOrderProducts.stream()
                .map(OrderProductsResponseDTO::of).toList();
        return entityOfResponseDTO;
    }

    public List<OrderResponseDTO> findAllByMemberId(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        List<Orders> findAllByMember = ordersRepository.findAllByMember(findMember);

        return findAllByMember.stream().map(OrderResponseDTO::of).toList();
    }
    public OrderProductsResponseDTO getProductsAndInjectCount(Long productId, Integer count){
        Products findProductByRequest = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        return OrderProductsResponseDTO.builder()
                .products(findProductByRequest)
                .count(count)
                .build();
    }
}



