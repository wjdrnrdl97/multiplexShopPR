package backend.shop.com.multiplexshop.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {


    @GetMapping("/join")
    public String getJoinView(){
        return "join";
    }

    @GetMapping("/login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("/mypage")
    public String getMyPageView(){
        return "mypage";
    }
}
