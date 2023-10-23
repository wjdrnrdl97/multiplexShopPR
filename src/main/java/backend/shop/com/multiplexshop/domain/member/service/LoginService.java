package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberResponseDTO;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public MemberResponseDTO checkPasswordForLogin(String loginId, String loginPW){
        Member loginMember = memberRepository.findByEmail(loginId).stream().
                filter(member -> member.getPassword().equals(loginPW))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));
        log.info("loginMember = {}",loginMember.toString());

        return MemberResponseDTO.withoutPassword(loginMember);
    }


}
