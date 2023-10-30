package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest extends IntegrationTestSupport {

    @Autowired
    LoginService loginService;

    @Test
    @DisplayName("이메일과 패스워드가 요청오면 해당 이메일을 조회한 후 패스워드를 체크한다.")
    public void checkPasswordForLogin(){
        //given
        Member LoginMember = Member.builder()
                .memberEmailId("test@naver.com")
                .password("abc123")
                .memberName("테스트")
                .build();
        memberRepository.save(LoginMember);
        String loginId = "test@naver.com";
        String loginPW = "abc123";
        //when
        MemberResponseDTO result = loginService.checkPasswordForLogin(loginId, loginPW);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getMemberName()).isEqualTo("테스트");
    }
    @Test
    @DisplayName("잘못된 이메일 또는 패스워드가 요청 올 경우")
    public void checkPasswordForLoginThrowException(){
        //given
        Member LoginMember = Member.builder()
                .memberEmailId("test@naver.com")
                .password("abc123")
                .memberName("테스트")
                .build();
        memberRepository.save(LoginMember);
        String loginId = "test@naver.com";
        String loginPW = "잘못된 패스워드";
        //when //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->{
            MemberResponseDTO result = loginService.checkPasswordForLogin(loginId, loginPW);
            });
        assertThat(exception.getMessage()).isEqualTo("이메일 또는 비밀번호가 잘못되었습니다.");
    }


}