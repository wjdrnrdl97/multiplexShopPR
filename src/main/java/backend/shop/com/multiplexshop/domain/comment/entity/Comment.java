package backend.shop.com.multiplexshop.domain.comment.entity;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="boardId", nullable = false)
    private Board board;

    @Column(length = 50,nullable = false)
    private String commentContent;

    @Column(length = 20)
    private String memberName;

    @Builder
    public Comment(String commentContent,String memberName, Board board, Member member){
        this.board = board;
        this.member = member;
        this.commentContent = commentContent;
        this.memberName = memberName;
    }

    public static Comment dtoToCommentEntity(CommentDTOs.CommentRequestDTO commentRequestDTO, Member member, Board board){
        return Comment.builder()
                .commentContent(commentRequestDTO.getCommentContent())
                .memberName(commentRequestDTO.getMemberName())
                .member(member)
                .board(board)
                .build();
    }

    /**
     * 댓글을 수정할 때 기존 댓글 내용을 수정된 댓글 내용으로 수정하는 로직
     * @param commentContent (수정할 댓글 내용)
     */
    public void update(String commentContent) {
        this.commentContent=commentContent;
    }
}
