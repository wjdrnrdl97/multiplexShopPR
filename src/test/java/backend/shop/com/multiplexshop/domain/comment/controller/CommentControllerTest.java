package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;


import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class CommentControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("요청에 따라 댓글을 생성한 후 HTTP 201코드를 응답한다.")
    public void postComment() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        Board saveBoard = boardRepository.save(board);
        CommentRequestDTO request = CommentRequestDTO.builder()
                .boardId(1L)
                .memberId(1L)
                .commentContent("테스트")
                .memberName(saveMember.getMemberName())
                .build();
        String uri = "/api/comment";
        //when //then
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("요청에 따라 해당 댓글의 내용을 수정하고 HTTP 204코드에 응답한다.")
    public void updateComment() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        Board saveBoard = boardRepository.save(board);
        Comment comment = Comment.builder()
                .board(saveBoard)
                .member(saveMember)
                .commentContent("내용")
                .memberName("테스트")
                .build();
        commentRepository.save(comment);

        Long requestCommentId = 1L;
        CommentRequestDTO request = CommentRequestDTO.builder()
                .boardId(1L)
                .memberId(1L)
                .commentContent("테스트 수정")
                .memberName(saveMember.getMemberName())
                .build();
        String uri  = "/api/comment/{id}";
        //when //then
        mockMvc.perform(put(uri,requestCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

    }
    @Test
    @DisplayName("요청에 따라 해당 댓글을 삭제하고 HTTP 204코드에 응답한다.")
    public void deleteComment() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        Board saveBoard = boardRepository.save(board);
        Comment comment = Comment.builder()
                .board(saveBoard)
                .member(saveMember)
                .commentContent("내용")
                .memberName("테스트")
                .build();
        commentRepository.save(comment);

        Long requestCommentId = 1L;
        String uri = "/api/comment/{id}";
        //when
        mockMvc.perform(delete(uri,requestCommentId))
                .andExpect(status().isNoContent());
        //then
    }
}