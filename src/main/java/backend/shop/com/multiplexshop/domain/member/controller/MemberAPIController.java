package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@RestController
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;

    @PostMapping(value = "/api/join",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String postMemberJoin(@RequestBody MemberRequestDTO memberRequestDTO){
        memberService.memberSave(memberRequestDTO);
        return "redirect:/";
    }

    @PutMapping(value = "/api/mypage/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member> updateMemberInfo(@PathVariable Long id,
                                                   @RequestBody MemberRequestDTO memberRequestDTO){
        Member updatedMember = memberService.update(id, memberRequestDTO);

        return ResponseEntity.ok().body(updatedMember);
    }




}
