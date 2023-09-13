package backend.shop.com.multiplexshop.domain.delivery.service;

import backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs;
import backend.shop.com.multiplexshop.domain.delivery.entity.Address;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final OrdersRepository ordersRepository;

    public DeliveryResponseDTO save(DeliveryRequestDTO dto){
        Orders order = ordersRepository.findById(dto.getOrderID())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Address address = Address.builder()
                .shippingAddress(dto.getShippingAddress())
                .zipcode(dto.getZipcode())
                .build();
        Delivery createDelivery = Delivery.createDelivery(order, address);
        Delivery saveDelivery = deliveryRepository.save(createDelivery);
        return DeliveryResponseDTO.of(saveDelivery);
    }
}
