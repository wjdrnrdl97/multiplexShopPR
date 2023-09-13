package backend.shop.com.multiplexshop.domain.member.controller;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAPIController.class)
class MemberAPIControllerTestByWebMvcTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected MemberService memberService;

    @Test
    @DisplayName("클라이언트 POST 요청에 200번으로 응답하기에 성공한다.")
    void test() throws Exception {
        //given
        MemberRequestDTO requestDTO = MemberRequestDTO.builder()
                .memberEmailId("a@a.com")
                .password("1234")
                .memberAddress("a")
                .role(Role.USER)
                .phoneNumber("010-9299-3944")
                .build();
        // when //then
        mockMvc.perform(post("/api/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}