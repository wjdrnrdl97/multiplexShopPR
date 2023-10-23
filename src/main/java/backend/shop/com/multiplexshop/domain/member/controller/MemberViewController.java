package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.delivery.service.DeliveryService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.ProductsResponseDTO;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;
import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final OrderService orderService;
    private final ProductsService productsService;
    private final DeliveryService deliveryService;

    @GetMapping("/join")
    public String getJoinView(@RequestParam(required = false) Long id, Model model){
        if (id == null){
            model.addAttribute("member",new MemberResponseDTO());
        }else {
            MemberResponseDTO  findMember = memberService.findById(id);
            model.addAttribute("member", findMember);
        }
        return "member/join";
    }

    @GetMapping("/login")
    public String getLoginView(){
        return "login/login";
    }

    @GetMapping("/mypage/{id}")
    public String getMyPageView(Model model, @PathVariable Long id){
        MemberResponseDTO getMemberByMemberId = memberService.findById(id);
        model.addAttribute("member", getMemberByMemberId);

        List<OrderResponseDTO> getOrderByMemberId = orderService.findAllByMemberId(id);
        model.addAttribute("order", getOrderByMemberId);

        List<DeliveryResponseDTO> getDeliveryByMemberId = deliveryService.findAllByMemberId(id);
        model.addAttribute("orderDelivery",getDeliveryByMemberId);
        return "member/mypage";
    }
    @GetMapping("/adminpage")
    public String getAdminPageView(@RequestParam(defaultValue = "0") int page, Model model){
        Page<ProductsResponseDTO> allProductOfPagination = productsService.findAllOfPagination(page);
        model.addAttribute("product", allProductOfPagination);
        return "admin/adminPage";
    }
}
