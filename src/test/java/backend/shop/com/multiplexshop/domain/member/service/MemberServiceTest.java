package backend.shop.com.multiplexshop.domain.member.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberRequestDTO;
import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberResponseDTO;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("요청에 따라 회원을 생성한다.")
    public void createMemberByRequest(){
        //given
        MemberRequestDTO request = MemberRequestDTO.builder()
                .memberEmailId("test@naver.com")
                .password("abc123")
                .memberName("테스트")
                .memberAddress("테스트 주소")
                .phoneNumber("1234-5678")
                .build();
        //when
        MemberResponseDTO result = memberService.createMemberByRequest(request);
        //then
        assertThat(result).isNotNull();
        assertThat(request.getMemberName()).isEqualTo("테스트");
    }
    @Test
    @DisplayName("요청한 회원번호로 회원목록을 조회한다.")
    public void findByMemberId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Long request = 1L;
        //when
        MemberResponseDTO result = memberService.findByMemberId(request);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getMemberId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("DB에 저장되지 않은 회원번호로 회원목록을 조회시 예외가 발생한다.")
    public void findByMemberIdThrowException(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Long request = 2L;
        //when //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->{
            memberService.findByMemberId(request);
        });
        assertThat(exception.getMessage()).isEqualTo("등록되지 않은 회원입니다.");
    }
    @Test
    @DisplayName("요청한 회원의 이메일 아이디가 이미 존재하면 예외가 발생한다.")
    public void duplicateEmailValidate(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Member findMember = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("중복테스트")
                .build();
        //when //then
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->{
            memberService.duplicateEmailValidate(findMember);
        });
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 E-Mail[ID] 입니다.");
    }
    @Test
    @DisplayName("요청한 회원번호로 회원을 조회한 후 해당 회원을 삭제한다.")
    public void deleteMemberById(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Long request = 1L;
        //when
        memberService.deleteMemberById(request);
        //then
        List<Member> result = memberRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("요청한 회원번호로 회원을 조회한 후 해당 회원의 정보를 수정한다.")
    public void updateMemberByRequest(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Long requestMemberId = 1L;
        MemberRequestDTO updateRequest = MemberRequestDTO.builder()
                .memberAddress("테스트 주소")
                .phoneNumber("1234-5678")
                .build();
        //when
        MemberResponseDTO result = memberService.updateMemberByRequest(requestMemberId, updateRequest);
        //then
        assertThat(result.getMemberId()).isEqualTo(1L);
        assertThat(result.getMemberAddress()).isEqualTo("테스트 주소");

    }
}