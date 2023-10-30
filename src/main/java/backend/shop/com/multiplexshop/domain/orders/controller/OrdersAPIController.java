package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.dto.OrderProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;

@RestController
@RequiredArgsConstructor
public class OrdersAPIController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<OrderResponseDTO> postOrderByRequest(@RequestBody OrderRequestDTO request){
        OrderResponseDTO postOrderByRequest = orderService.createOrderByRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOrderByRequest);
    }
    @GetMapping("/api/order/{id}")
    public ResponseEntity<List<OrderProductsResponseDTO>> getOrderWithProductByOrderId(@PathVariable("id") Long id){
        List<OrderProductsResponseDTO> findAllByOrderId = orderService.findAllByOrderId(id);
        return ResponseEntity.ok().body(findAllByOrderId);
    }

    @DeleteMapping("api/order/{id}")
    public ResponseEntity deleteOrderByOrderId(@PathVariable("id") Long id){
        orderService.deleteByOrdersIds(id);
        return ResponseEntity.noContent().build();
    }

}
