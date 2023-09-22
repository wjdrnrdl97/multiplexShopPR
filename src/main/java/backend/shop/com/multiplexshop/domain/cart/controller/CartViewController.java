package backend.shop.com.multiplexshop.domain.cart.controller;


import backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs;
import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartViewController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public String getMyCart(@PathVariable("id")Long id, Model model){
        List<CartProductsResponseDTO> cartWithProductsByMemberId = cartService.getCartWithProductsByMemberId(id);
        model.addAttribute("dto",cartWithProductsByMemberId);
        return "cart/cart";
    }

}
