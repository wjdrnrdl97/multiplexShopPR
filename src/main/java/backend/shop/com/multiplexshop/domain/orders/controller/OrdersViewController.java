package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersViewController {

    private final OrderService orderService;

}
