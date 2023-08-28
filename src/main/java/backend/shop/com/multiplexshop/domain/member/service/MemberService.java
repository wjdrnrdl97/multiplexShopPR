package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void memberSave(MemberRequestDTO memberRequestDTO){
        Member member = toMember(memberRequestDTO);
        memberRepository.save(member);
    }

    public Member toMember(MemberRequestDTO memberRequestDTO){
        return Member.builder()
                .memberEmailId(memberRequestDTO.getMemberEmailId())
                .memberName(memberRequestDTO.getMemberName())
                .memberAddress(memberRequestDTO.getMemberAddress())
                .phoneNumber(memberRequestDTO.getPhoneNumber())
                .role(memberRequestDTO.getRole())
                .build();
    }

}
