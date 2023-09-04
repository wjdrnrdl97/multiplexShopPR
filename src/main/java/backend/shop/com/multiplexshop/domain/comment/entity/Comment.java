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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId")
    private Board board;

    @Column(length = 50,nullable = false)
    private String commentContent;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Builder
    public Comment(String commentContent,String memberName){
        this.commentContent = commentContent;
        this.memberName = memberName;
    }
    public static Comment dtoToCommentEntity(CommentDTOs.CommentRequestDTO commentRequestDTO){
        return Comment.builder()
                .commentContent(commentRequestDTO.getCommentContent())
                .memberName(commentRequestDTO.getMemberName())
                .build();
    }

}
