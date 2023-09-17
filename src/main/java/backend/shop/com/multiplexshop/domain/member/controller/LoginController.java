package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.config.interceptor.SessionConst;
import backend.shop.com.multiplexshop.domain.member.dto.LoginDTO;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;



    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO loginRequest, HttpServletRequest request){

        Member loginMember
                = loginService.checkPasswordForLogin(loginRequest.getMemberEmailId(), loginRequest.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginMember);

        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }




}
