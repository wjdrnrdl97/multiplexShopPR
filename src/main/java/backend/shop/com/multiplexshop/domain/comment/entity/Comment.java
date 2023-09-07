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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="boardId")
    private Board board;

    @Column(length = 50,nullable = false)
    private String commentContent;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Builder
    public Comment(String commentContent,String memberName, Board board){
        this.board = board;
        this.commentContent = commentContent;
        this.memberName = memberName;
    }

    public static Comment dtoToCommentEntity(CommentDTOs.CommentRequestDTO commentRequestDTO){
        return Comment.builder()
                .commentContent(commentRequestDTO.getCommentContent())
                .memberName(commentRequestDTO.getMemberName())
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
