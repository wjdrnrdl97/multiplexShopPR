package backend.shop.com.multiplexshop.domain.cart.controller;


import backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs;
import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;

@RestController
@RequiredArgsConstructor
public class CartAPIController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<CartResponseDTO> postCartByRequest(@RequestBody CartRequestDTO request){
        CartResponseDTO response = cartService.createCartWithProductsByRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/cart/{memberId}")
    public ResponseEntity<List<CartProductsResponseDTO>>getCartWithProductsByMember(@PathVariable("memberId") Long id){
        List<CartProductsResponseDTO> response = cartService.getCartWithProductsByMemberId(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/cart/{id}")
    public ResponseEntity deleteCartProductsById(@PathVariable("id")Long id){
        cartService.deleteCartProductsById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/api/cart/all/{memberId}")
    public ResponseEntity deleteCartProductsAllByMember(@PathVariable("memberId")Long memberId){
        cartService.deleteCartProductsAllByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
}
