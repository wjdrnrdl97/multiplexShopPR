package backend.shop.com.multiplexshop.domain.cart.controller;


import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;

@RestController
@RequiredArgsConstructor
public class CartAPIController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<CartResponseDTO> postCartByRequest(@RequestBody CartRequestDTO request){
        CartResponseDTO response = cartService.createCartWithProductsByRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/api/cart/")
}
