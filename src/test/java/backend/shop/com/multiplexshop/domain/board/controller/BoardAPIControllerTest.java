package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class BoardAPIControllerTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @Test
    @DisplayName("getBoard(): Board컨트롤러를 이용하여 게시물 상세조회")
    @Transactional
    public void test1() throws Exception{
        //given
        Member member = memberRepository.findById(1L).get();
        final String url = "/api/support/{good}";
        final String title = "title1";
        final String content = "content1";
        Board board = boardRepository.findById(1L).get();
        //when
        final ResultActions result = mockMvc.perform(get (url,board.getBoardId()));
        MvcResult mvcResult = result.andReturn();

        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println("응답: " + responseBody);

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.boardTitle").value(title))
                .andExpect(jsonPath("$.boardContent").value(content));
    }
    @Test
    @DisplayName("getBoardList(): Board컨트롤러를 이용하여 게시물 목록조회")
    @Transactional
    public void test2() throws Exception{
        //given
        String url = "/api/support";
        final String title1 = "title1";
        final String title2 = "title2";
        //when
        ResultActions perform = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = perform.andReturn();

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardTitle").value(title1))
                .andExpect(jsonPath("$[1].boardTitle").value(title2));
    }
}