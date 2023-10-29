package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberResponseDTO memberSave(MemberRequestDTO memberRequestDTO){
        Member member = dtoToMemberEntity(memberRequestDTO);
        duplicateEmailValidate(member);
        Member savedMember = memberRepository.save(member);
        return MemberResponseDTO.of(savedMember);
    }

    public MemberResponseDTO findById(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지않은 회원 입니다. : " + id));
        return MemberResponseDTO.of(findMember);
    }

    public void duplicateEmailValidate(Member member){
        List<Member> findMemberByEmail = memberRepository.findByEmail(member.getMemberEmailId());
        if (!findMemberByEmail.isEmpty()){
            throw new IllegalStateException("이미 존재하는 E-Mail[ID] 입니다.");
        }
    }
    public HashMap<String,String>validateHandling(BindingResult bindingResult){
        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }


    public void deleteMemberById(Long id){
        Member deleteMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지않은 회원 입니다. : " + id));
        memberRepository.delete(deleteMember);
    }

    private Member dtoToMemberEntity(MemberRequestDTO memberRequestDTO){
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
    public MemberResponseDTO updateMemberByRequest(Long id, MemberRequestDTO request){
        Member findmember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 회원입니다."));
        findmember.updateMember(request.getMemberAddress(), request.getPhoneNumber());
        Member saveUpdateMember = memberRepository.save(findmember);
        return MemberResponseDTO.of(saveUpdateMember);

    }

}
