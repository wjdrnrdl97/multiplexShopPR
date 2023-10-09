    package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs;
import backend.shop.com.multiplexshop.domain.delivery.service.DeliveryService;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersViewController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping("/complete/{id}")
    public String getCompleteOrderByOrderId(@PathVariable("id")Long id, Model model){
        List<OrderProductsResponseDTO> completeOrder = orderService.findAllByOrderId(id);
        model.addAttribute("orderProduct", completeOrder);
        return "order/orderComplete";
    }
    @GetMapping("/{id}")
    public String getDetailOrderWithProductsAndDeliveryByOrderId(@PathVariable("id") Long id, Model model){
        List<OrderProductsResponseDTO> findAllByOrderId = orderService.findAllByOrderId(id);
        model.addAttribute("orderProduct",findAllByOrderId);

        DeliveryResponseDTO findDeliveryByOrderId = deliveryService.findDeliveryByOrderId(id);
        model.addAttribute("orderDelivery",findDeliveryByOrderId);
        return "order/detailOrder";
    }

}
