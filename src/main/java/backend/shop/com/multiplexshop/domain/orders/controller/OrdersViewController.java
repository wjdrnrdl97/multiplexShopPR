    package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersViewController {

    private final OrderService orderService;

    @GetMapping("/complete/{id}")
    public String getCompleteOrderByOrderId(@PathVariable("id")Long id, Model model){
                List<OrderProductsResponseDTO> completeOrder = orderService.findAllByOrderId(id);
        model.addAttribute("orderProduct", completeOrder);
        return "order/orderComplete";
    }

}
