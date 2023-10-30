package backend.shop.com.multiplexshop.domain.comment.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest{

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("게시물에 해당하는 모든 댓글을 조회한다.")
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
        //when
        List<Comment> result = commentRepository.findAllByBoard(saveBoard);
        //then
        assertThat(result).hasSize(5);
    }
    @Test
    @DisplayName("게시물에 해당하는 모든 댓글을 삭제한다.")
    public void deleteAllByBoard(){
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
        //when
        commentRepository.deleteAllByBoard(saveBoard);
        //then
        List<Comment> result = commentRepository.findAll();
        assertThat(result).isEmpty();
    }
}