package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import lombok.*;


import java.time.LocalDateTime;


@Getter
public class UserBoardDTOs {

    /**
     * static class 생성자의 외부접근을 허용하기위해 access = AccessLevel.PUBLIC 설정
     */

    @Getter
    @NoArgsConstructor
    public static class UserBoardResponseDTO {
        private Long boardId;
        private Long boardViewCount;
        private LocalDateTime modDate;
        private LocalDateTime regDate;
        private String memberName;
        private String boardTitle;
        private String boardContent;
        private Long memberId;


        public UserBoardResponseDTO(UserBoard userBoard) {
            this.boardId = userBoard.getBoardId();
            this.boardViewCount = userBoard.getBoardViewCount();
            this.modDate = userBoard.getModDate();
            this.regDate = userBoard.getRegDate();
            this.memberName = userBoard.getMember().getMemberName();
            this.boardTitle = userBoard.getBoardTitle();
            this.boardContent = userBoard.getBoardContent();
            this.memberId = userBoard.getMember().getMemberId();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserBoardRequestDTO {
        private Long memberId;
        private String boardTitle;
        private String boardContent;

    }

}



