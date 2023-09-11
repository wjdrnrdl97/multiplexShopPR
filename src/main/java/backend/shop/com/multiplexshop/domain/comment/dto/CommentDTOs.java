package backend.shop.com.multiplexshop.domain.comment.dto;


import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
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

        @Builder
        public CommentResponseDTO(Long id, String memberName, String commentContent, LocalDateTime regDate, LocalDateTime modDate) {
            this.id = id;
            this.memberName = memberName;
            this.commentContent = commentContent;
            this.regDate = regDate;
            this.modDate = modDate;
        }

        public static CommentResponseDTO of(Comment comment){
            return CommentResponseDTO.builder()
                    .id(comment.getCommentId())
                    .memberName(comment.getMemberName())
                    .commentContent(comment.getCommentContent())
                    .regDate(comment.getRegDate())
                    .modDate(comment.getModDate())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CommentRequestDTO{
        private String memberName;
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
