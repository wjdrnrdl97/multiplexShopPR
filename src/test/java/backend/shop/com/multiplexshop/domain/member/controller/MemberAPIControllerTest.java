package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;
import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberRequestDTO.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



class MemberAPIControllerTest extends ControllerTestSupport {



    @Test
    @DisplayName("postMemberByRequest(): 회원 저장에 성공하기")
    void postMemberByRequest() throws Exception{
        //given
        final String url = "/api/join";
        final String userEmail = "test2@naver.com";
        final String userPW = "test1234!@#$";
        MemberRequestDTO userRequest = builder()
                .memberEmailId(userEmail)
                .password(userPW)
                .memberName("홍길동")
                .memberAddress("test Addr")
                .phoneNumber("010-9299-3944")
                .build();

        String requestBody = objectMapper.writeValueAsString(userRequest);
        //when  //then
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("postMemberByRequest(): 조건에 맞지않는 요청으로 회원 저장에 실패하기")
    void postMemberByBadRequest() throws Exception{
        //given
        final String url = "/api/join";
        final String userEmail = "test2@naver.com";
        final String userPW = "1234";
        MemberRequestDTO userRequest = builder()
                .memberEmailId(userEmail)
                .password(userPW)
                .memberName("홍길동")
                .memberAddress("test Addr")
                .phoneNumber("010-9299-3944")
                .build();

        String requestBody = objectMapper.writeValueAsString(userRequest);
        //when  //then
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("updateMemberInfo(): 회원 정보 수정에 성공한다.")
    void updateMemberInfo() throws Exception{
         //given
        String url = "/api/mypage/{id}";

        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("test1234!@#$")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);

        MemberRequestDTO updateRequest = builder()
                .memberEmailId("test@naver.com")
                .password("test1234!@#$")
                .memberName("테스트")
                .memberAddress("서울 강남구")
                .phoneNumber("02-1234-5678")
                .build();

        Long requestMemberId = saveMember.getMemberId();
        //then //when
        ResultActions result = mockMvc.perform(put(url, requestMemberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("updateMemberInfo(): 잘못된 요청으로 회원 정보 수정에 실패한다.")
    void updateMemberInfoByBadRequest() throws Exception{
        //given
        String url = "/api/mypage/{id}";

        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("test1234!@#$")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);

        MemberRequestDTO updateRequest = builder()
                .memberEmailId("test@naver.com")
                .password("test1234!@#$")
                .memberName("테스트")
                .memberAddress("")
                .phoneNumber("02-1234-5678")
                .build();

        Long requestMemberId = saveMember.getMemberId();
        //then //when
        ResultActions result = mockMvc.perform(put(url, requestMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deleteMember(): 회원 삭제에 성공한다.")
    void test4() throws Exception{
        //given
        String url = "/api/mypage/{id}";
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("test1234!@#$")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        //when
        ResultActions result = mockMvc.perform(delete(url, saveMember.getMemberId()));
        //then
        result.andExpect(status().isOk());
    }

}