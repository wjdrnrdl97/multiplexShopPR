package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;

@RestController
@RequiredArgsConstructor
public class OrdersAPIController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<OrderResponseDTO> postOrder(@RequestBody OrderRequestDTO request){
        OrderResponseDTO postOrderByRequest = orderService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOrderByRequest);
    }

}
