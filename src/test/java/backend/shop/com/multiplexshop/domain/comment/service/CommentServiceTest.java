package backend.shop.com.multiplexshop.domain.comment.service;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {


    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    BoardRepository boardRepository;


    @Test
    @DisplayName("댓글이 삭제가되어야한다.")
    public void test2(){
        //given
        Member member = getMember(1L);
        final String commentContent = "comment Content~~~";


        Comment comment = Comment.builder()
                .commentContent(commentContent)
                .memberName(member.getMemberName())
                .build();
        commentRepository.save(comment);

        //when
        commentRepository.delete(comment);
        //then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(0);
    }

    @Test
    @DisplayName("댓글의 생성에 성공해야한다.")
    public void save(){
        //given
        Member member = getMember(1L);
        final String commentContent = "new Content";
        CommentRequestDTO dto = CommentRequestDTO.builder()
                .commentContent(commentContent)
                .memberName(member.getMemberName())
                .build();

        //when
        Comment save = commentService.save(dto);
        //then
        Comment comment = commentRepository.findById(1L).get();
        assertThat(comment)
                .isNotNull()
                .extracting("commentContent","memberName")
                .contains(commentContent,member.getMemberName());
    }

    @Test
    @DisplayName("댓글 목록 조회하기")
    public void getCommentList() throws Exception{
        //given
        Member member = getMember(1L);
        String commentContent1 = "content1";
        String commentContent2 = "content2";
        Comment comment1 = Comment.builder()
                .commentContent(commentContent1)
                .memberName(member.getMemberName())
                .build();


        Comment comment2 = Comment.builder().commentContent(commentContent2)
                .memberName(member.getMemberName()).build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        //when
        List<Comment> comments = commentService.findAll();
        //then
        assertThat(comments).hasSize(2)
                .extracting("commentContent","memberName")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(commentContent1,member.getMemberName()),
                        Tuple.tuple(commentContent2, member.getMemberName())
                );
    }

    @Test
    @DisplayName("댓글의 잘못된 번호를 검색했을때 지정된 예외가 처리되어야한다.")
    public void searchById() throws Exception{
        //given

        //when //then
        assertThatThrownBy(() -> commentService.searchById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지않는 번호입니다."+0L);


    }

    @Test
    @DisplayName("댓글 수정 버튼을 눌렀을 때 댓글 수정에 성공해야한다.")
    public void update(){
        //given
        Member member = getMember(1L);
        final String originalContent = "Content";

        Comment originalComment = Comment.builder()
                .commentContent(originalContent)
                .memberName(member.getMemberName())
                .build();

        commentRepository.save(originalComment);

        final String newContent = "new Content~~";

        CommentRequestDTO updateRequestDTO = CommentRequestDTO.builder()
                .commentContent(newContent)
                .memberName(member.getMemberName())
                .build();

        //when
        commentService.update(originalComment.getCommentId(), updateRequestDTO);

        //then
        Comment updatedComment = commentRepository.findById(originalComment.getCommentId()).get();
        assertThat(updatedComment).extracting("commentContent", "memberName")
                .contains(newContent, member.getMemberName());

    }



    
    @Test
    @DisplayName("게시물 번호를 통해서 게시물 번호와 연관된 댓글 목록을 가져오는데에 성공한다. ")
    public void findAllByBoard() throws Exception{
        //given
        Board board = boardRepository.findById(1L).get();
        Member member = getMember(1L);
        final String content1 = "첫번째 댓글입니다.";
        final String content2 = "두번째 댓글입니다.";

        Comment comment1 = Comment.builder()
                .board(board)
                .memberName(member.getMemberName())
                .commentContent(content1)
                .build();

        Comment comment2 = Comment.builder()
                .board(board)
                .memberName(member.getMemberName())
                .commentContent(content2)
                .build();

        commentRepository.saveAll(List.of(comment1, comment2));


        //when

        List<Comment> commentsByBoardId = commentRepository.findAllByBoard(board);

        //then
        assertThat(commentsByBoardId).hasSize(2)
                .extracting("board", "commentContent")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(board, comment1),
                        Tuple.tuple(board, comment2)
                );
    }

    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        return member;
    }
}