package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void memberSave(MemberRequestDTO memberRequestDTO){

        Member member = dtoToMemberEntity(memberRequestDTO);
        duplicateEmailValidate(member);
        memberRepository.save(member);
    }

    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지않은 회원 입니다. : "+id));
    }

    public void duplicateEmailValidate(Member member){
        List<Member> findMemberByEmail = memberRepository.findByEmail(member.getMemberEmailId());
        if (!findMemberByEmail.isEmpty()){
            throw new IllegalStateException("이미 존재하는 E-Mail[ID] 입니다.");
        }
    }

    public void deleteMemberById(Long id){
        Member deleteMember = findById(id);
        memberRepository.delete(deleteMember);
    }



    public Member dtoToMemberEntity(MemberRequestDTO memberRequestDTO){
        return Member.builder()
                .memberEmailId(memberRequestDTO.getMemberEmailId())
                .password(memberRequestDTO.getPassword())
                .memberName(memberRequestDTO.getMemberName())
                .memberAddress(memberRequestDTO.getMemberAddress())
                .phoneNumber(memberRequestDTO.getPhoneNumber())
                .role(memberRequestDTO.getRole())
                .build();
    }

    @Transactional
    public Member update(Long id, MemberRequestDTO request){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 회원입니다."));

        member.updateMember(request.getMemberAddress(), request.getPhoneNumber());

        return member;

    }

}
