package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/join")
    public String getJoinView(@RequestParam(required = false) Long id, Model model){
        if (id == null){
            model.addAttribute("member",new MemberResponseDTO());
        }else {
            Member member = memberService.findById(id);
            model.addAttribute("member", new MemberResponseDTO(member));
        }
        return "join";
    }

    @GetMapping("/login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("/mypage/{id}")
    public String getMyPageView(Model model, @PathVariable Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("등록 되지않은 회원입니다."));
        MemberResponseDTO responseDTO = new MemberResponseDTO(member);
        model.addAttribute("member", responseDTO);
        return "mypage";
    }



}
