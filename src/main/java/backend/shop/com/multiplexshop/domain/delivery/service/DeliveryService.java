package backend.shop.com.multiplexshop.domain.delivery.service;



import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;


@Service
@RequiredArgsConstructor
public class DeliveryService{

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final DeliveryRepository deliveryRepository;

    public List<DeliveryResponseDTO> findAllByMemberId(Long id){
        List<Delivery> deliveryList = new ArrayList<>();

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Orders> findOrdersByMember = ordersRepository.findAllByMember(findMember);
        findOrdersByMember.stream().forEach(orders -> {
            Delivery delivery = deliveryRepository.findByOrderId(orders.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
            deliveryList.add(delivery);
        });
        return deliveryList.stream().map(DeliveryResponseDTO::of).toList();
    }
    public DeliveryResponseDTO findDeliveryByOrderId(Long id){
        Delivery delivery = deliveryRepository.findByOrderId(id)
                                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
        return DeliveryResponseDTO.of(delivery);
    }
}
