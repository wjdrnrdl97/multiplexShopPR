package backend.shop.com.multiplexshop.domain.comment.dto;


import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDTOs {

    @Getter
    @NoArgsConstructor
    public static class CommentResponseDTO{
        private Long id;
        private String memberName;
        private String commentContent;
        private LocalDateTime regDate;
        private LocalDateTime modDate;
        private Member member;

        @Builder
        public CommentResponseDTO(Long id, String memberName, String commentContent,
                                                        LocalDateTime regDate, LocalDateTime modDate,Member member) {
            this.id = id;
            this.memberName = memberName;
            this.commentContent = commentContent;
            this.regDate = regDate;
            this.modDate = modDate;
            this.member = member;
        }

        public static CommentResponseDTO of(Comment comment){
            return CommentResponseDTO.builder()
                    .id(comment.getCommentId())
                    .memberName(comment.getMemberName())
                    .commentContent(comment.getCommentContent())
                    .regDate(comment.getRegDate())
                    .modDate(comment.getModDate())
                    .member(comment.getMember())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CommentRequestDTO{
        private String memberName;
        @NotBlank(message = "댓글 내용을 입력해 주세요.")
        private String commentContent;
        private Long boardId;
        private Long memberId;


        @Builder
        public CommentRequestDTO(String memberName, String commentContent,Long boardId,Long memberId) {
            this.memberName = memberName;
            this.commentContent = commentContent;
            this.boardId = boardId;
            this.memberId = memberId;
        }
    }


}
