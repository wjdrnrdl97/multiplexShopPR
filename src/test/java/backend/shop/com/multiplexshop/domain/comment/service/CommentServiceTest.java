package backend.shop.com.multiplexshop.domain.comment.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.CommentResponseDTO;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;
import static org.assertj.core.api.Assertions.*;


class CommentServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("요청을 통해 해당 댓글을 조회한다.")
    public void CommentResponseDTO(){
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
        //when
        CommentResponseDTO result = commentService.findCommentById(requestCommentId);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getCommentContent()).isEqualTo("내용");
    }
    @Test
    @DisplayName("해당 게시물의 모든 댓글을 조회한다.")
    public void findAllByBoard(){
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
        for(int i = 0; i<5; i++){
            Comment comment = Comment.builder()
                    .board(saveBoard)
                    .member(saveMember)
                    .commentContent("내용" + i)
                    .memberName("테스트")
                    .build();
            commentRepository.save(comment);
        }
        Long requestBoardId = 1L;
        //when
        List<CommentResponseDTO> result = commentService.findAllByBoard(requestBoardId);
        //then
        assertThat(result).hasSize(5);
    }
    @Test
    @DisplayName("요청에 따라 댓글이 생성된다.")
    public void createCommentByRequest(){
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
        //when
        CommentResponseDTO result = commentService.createCommentByRequest(request);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getCommentContent()).isEqualTo("테스트");
    }
    @Test
    @DisplayName("요청에 따라 해당 댓글을 삭제한다.")
    public void deleteCommentById(){
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
        //when
        commentService.deleteCommentById(requestCommentId);
        //then
        List<Comment> result = commentRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("")
    public void updateCommentByRequest(){
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
        //when
        CommentResponseDTO result = commentService.updateCommentByRequest(requestCommentId, request);
        //then
        assertThat(result.getCommentContent()).isEqualTo("테스트 수정");
    }
}