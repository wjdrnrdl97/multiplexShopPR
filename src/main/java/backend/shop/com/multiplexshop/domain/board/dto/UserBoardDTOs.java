package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import lombok.*;


import java.time.LocalDateTime;


public class UserBoardDTOs {


    @Getter
    @AllArgsConstructor
    @Builder
    public static class BoardResponseDTO {
        private Long boardId;
        private Long boardViewCount;
        private LocalDateTime modDate;
        private LocalDateTime regDate;
        private String memberName;
        private String boardTitle;
        private String boardContent;
        private Long memberId;

        public BoardResponseDTO(UserBoard userBoard) {
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
    @Builder
    public static class BoardRequestDTO {
        private Long boardId;
        private Long memberId;
        private String boardTitle;
        private String boardContent;
    }

    }



