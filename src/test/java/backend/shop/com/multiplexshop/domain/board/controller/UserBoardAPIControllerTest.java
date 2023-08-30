package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.repository.UserBoardRepository;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserBoardAPIControllerTest {
    @Autowired
    BoardService boardService;
    @Autowired
    UserBoardRepository userBoardRepository;
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
        UserBoard userBoard = userBoardRepository.findById(1L).get();
        //when
        final ResultActions result = mockMvc.perform(get (url, userBoard.getBoardId()));
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
        final String url = "/api/support";
        final String title1 = "title1";
        final String title2 = "title2";
        //when
        final ResultActions perform = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = perform.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardTitle").value(title1))
                .andExpect(jsonPath("$[1].boardTitle").value(title2));
    }
    @Test
    @DisplayName("postBoard(): Board컨트롤러를 이용하여 게시물 등록")
    public void test3()throws Exception{
        //given
        Member member = memberRepository.findById(1L).get();
        final String url = "/api/support";
        final String title = "new title";
        BoardRequestDTO boardRequest = BoardRequestDTO.builder()
                .boardTitle(title)
                .boardContent("new board")
                .memberId(member.getMemberId())
                .build();
        final String requestBody = objectMapper.writeValueAsString(boardRequest);
        //when
        ResultActions perform = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        //then
        perform.andExpect(status().isCreated());
        List<UserBoard> userBoardList = userBoardRepository.findAll();
        assertThat(userBoardList.get(0).getBoardTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("updateBoard(): Board컨트롤러를 이용하여 게시물 수정")
    @Transactional
    public void test4()throws Exception{
    //given
        Member member = memberRepository.findById(2L).get();
        UserBoard userBoard = userBoardRepository.findById(1L).get();
        final String url = "/api/support/{id}";
        final String title = "updateTitle";
        final String content = "updateContent";
        BoardRequestDTO boardRequest = BoardRequestDTO.builder()
                .boardTitle(title)
                .boardContent(content)
                .memberId(member.getMemberId())
                .build();
        String requestBody = objectMapper.writeValueAsString(boardRequest);
    //when
        final ResultActions result = mockMvc.perform(put(url, 1).contentType(MediaType.APPLICATION_JSON)
                                             .content(requestBody));
    //then
    result.andExpect(status().isOk());
    UserBoard updateUserBoard = userBoardRepository.findById(1L).get();
    assertThat(updateUserBoard.getBoardTitle()).isEqualTo(title);
    assertThat(updateUserBoard.getBoardContent()).isEqualTo(content);
}
@Test
@DisplayName("deleteBoad(): Board컨트롤러를 이용하여 게시물 삭제")
@Transactional
public void test5()throws Exception{
    //given
    final String url = "/api/support/{id}";
    //when
    ResultActions resultActions = mockMvc.perform(delete(url,2));
    //then
    resultActions.andExpect(status().isOk());
    List<UserBoard> userBoardList = userBoardRepository.findAll();
    assertThat(userBoardList.size()).isEqualTo(1);

}
}