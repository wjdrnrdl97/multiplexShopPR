package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
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

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;
import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberRequestDTO.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberAPIControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    MemberRepository memberRepository;
    

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    @DisplayName("postMemberJoin(): 회원 저장에 성공하기")
    void test1() throws Exception{
        //given
        final String url = "/api/join";
        final String userEmail = "test2@naver.com";
        final String userPW = "1234";
        MemberRequestDTO userRequest = builder()
                .memberEmailId(userEmail)
                .password(userPW)
                .memberName("kim")
                .memberAddress("test Addr")
                .phoneNumber("010-9299-3944")
                .build();

        String requestBody = mapper.writeValueAsString(userRequest);
        //when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.get(0).getMemberEmailId()).isEqualTo(userEmail);
        assertThat(members.get(0).getPassword()).isEqualTo(userPW);
        result.andExpect(status().isOk());
        
    }

    @Test()
    @DisplayName("postMemberJoin():회원 등록시 중복회원 검증에 성공해 예외처리가 터져 테스트가 실패해야한다.")
    void test2() throws Exception{
        //given
        final String url = "/api/join";
        final String userEmail = "test@naver.com";
        final String userPW = "1234";
        MemberRequestDTO userRequest = builder()
                .memberEmailId(userEmail)
                .password(userPW)
                .memberName("kim")
                .memberAddress("test Addr")
                .phoneNumber("010-9299-3944")
                .build();

        String requestBody = mapper.writeValueAsString(userRequest);

        //when
        assertThrows(ServletException.class, () -> {
            ResultActions result = mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));

        //then
            result.andExpect(status().isBadRequest());
        });


    }

    @Test
    @DisplayName("updateMemberInfo(): 회원 정보 수정에 성공한다.")
    void test3() throws Exception{
         //given
        String url = "/api/mypage/{id}";
        String newAddr = "연수동 552-1";
        String newTel = "010-9299-3944";

        Member member = memberRepository.findById(99L).get();
        MemberRequestDTO request = builder()
                .memberAddress(newAddr)
                .phoneNumber(newTel)
                .build();

        //when
        ResultActions result = mockMvc.perform(put(url, member.getMemberId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));
        //then
        result.andExpect(status().isOk());

        Member modifiedMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(modifiedMember.getMemberAddress()).isEqualTo(newAddr);
        assertThat(modifiedMember.getPhoneNumber()).isEqualTo(newTel);


    }

}