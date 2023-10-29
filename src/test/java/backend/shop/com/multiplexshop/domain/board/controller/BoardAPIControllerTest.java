package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.entity.BoardType;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import jakarta.servlet.http.Cookie;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@SpringBootTest
@AutoConfigureMockMvc
class BoardAPIControllerTest extends ControllerTestSupport {
    @Test
    @DisplayName("getBoardList(): Board컨트롤러를 이용하여 게시물 목록조회")
    public void test2() throws Exception{
        //given
        final String url = "/api/support";
        final String title1 = "NOTICE";
        final String title2 = "POST";
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
        Member member = Member.builder()
                .memberEmailId("사용자1")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);
        final String url = "/api/support";
        final String title = "NEW POST";
        BoardRequestDTO boardRequest = BoardRequestDTO.builder()
                .memberId(1L)
                .boardTitle(title)
                .boardContent("new board")
                .boardType(BoardType.POST)
                .build();
        final String requestBody = objectMapper.writeValueAsString(boardRequest);
        //when
        ResultActions perform = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        //then
        perform.andExpect(status().isCreated());
        List<Board> userBoardList = boardRepository.findAll();
        assertThat(userBoardList.get(0).getBoardTitle()).isEqualTo(title);
    }
    @Test
    @DisplayName("postBoard(): Board컨트롤러를 이용하여 게시물 등록 유효성 검사")
    public void test3_1()throws Exception{
        //given
        Member member = Member.builder()
                .memberEmailId("사용자1")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);
        final String url = "/api/support";
        final String title = "";
        BoardRequestDTO boardRequest = BoardRequestDTO.builder()
                .memberId(1L)
                .boardTitle(title)
                .boardContent("new board")
                .boardType(BoardType.POST)
                .build();
        final String requestBody = objectMapper.writeValueAsString(boardRequest);
        //when//then
        ResultActions perform = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @DisplayName("updateBoard(): Board컨트롤러를 이용하여 게시물 수정")
    public void test4()throws Exception{
    //given
        Member member = memberRepository.findById(2L).get();
        final String url = "/api/support/{id}";
        final String title = "NEW NOTICE";
        final String content = "NEW NOTIFICATION";
        BoardRequestDTO boardRequest = BoardRequestDTO.builder()
                .boardTitle(title)
                .boardContent(content)
//                .memberId(member.getMemberId())
                .build();
        String requestBody = objectMapper.writeValueAsString(boardRequest);
    //when
        final ResultActions result = mockMvc.perform(put(url, 2).contentType(MediaType.APPLICATION_JSON)
                                             .content(requestBody));
    //then
    result.andExpect(status().isOk());
    Board updateUserBoard = boardRepository.findById(2L).get();
    assertThat(updateUserBoard.getBoardTitle()).isEqualTo(title);
    assertThat(updateUserBoard.getBoardContent()).isEqualTo(content);
}
@Test
@DisplayName("deleteBoad(): Board컨트롤러를 이용하여 게시물 삭제")
public void test5()throws Exception{
    //given
    final String url = "/api/support/{id}";
    //when
    ResultActions resultActions = mockMvc.perform(delete(url,2));
    //then
    resultActions.andExpect(status().isOk());
    List<Board> userBoardList = boardRepository.findAll();
    assertThat(userBoardList.size()).isEqualTo(2);

}
@Test
@DisplayName("컨트롤러를 이용하여 게시물 조회수 증가 확인")
public void test6()throws Exception{
    //given
    Cookie cookie = new Cookie("boardView","[0]");
    final String url = "/api/support/boardView/{id}";



    //when
    ResultActions perform = mockMvc.perform(get(url,98).cookie(cookie));
    //then
    perform.andExpect(status().isOk());
    Board board = boardRepository.findById(98L).get();
    assertThat(board.getBoardViewCount()).isEqualTo(1);
}
    @Test
    @DisplayName("컨트롤러를 이용하여 조회한 게시물 조회 시 조회수 증가하지 않기")
    public void test7()throws Exception{
        //given
        Cookie cookie = new Cookie("boardView","[98]");
        final String url = "/api/support/boardView/{id}";

        //when
        ResultActions perform = mockMvc.perform(get(url,98).cookie(cookie));
        //then
        perform.andExpect(status().isOk());
        Board board = boardRepository.findById(98L).get();
        assertThat(board.getBoardViewCount()).isEqualTo(0);
    }
}