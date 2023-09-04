package backend.shop.com.multiplexshop.domain.comment.dto;


import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDTOs {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentResponseDTO{
        private String memberName;
        private String commentContent;
        private LocalDateTime regDate;
        private LocalDateTime modDate;

    public CommentResponseDTO(Comment comment){
        this.memberName = comment.getMemberName();
        this.commentContent = comment.getCommentContent();
        this.regDate = comment.getRegDate();
        this.modDate = comment.getModDate();
    }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentRequestDTO{
        private String memberName;
        private String commentContent;
    }
}
