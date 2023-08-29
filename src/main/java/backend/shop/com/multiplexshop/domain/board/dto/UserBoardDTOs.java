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
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class BoardResponseDTO {
        private Long boardId;
        private Long boardViewCount;
        private LocalDateTime modDate;
        private LocalDateTime regDate;
        private String memberName;
        private String boardTitle;
        private String boardContent;
        private Long memberId;

        @Builder
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
    public static class BoardRequestDTO {
        private final Long boardId;
        private final Long memberId;
        private final String boardTitle;
        private final String boardContent;

        @Builder
        public BoardRequestDTO(Long boardId,Long memberId, String boardTitle, String boardContent) {
            this.memberId = memberId;
            this.boardId = boardId;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
        }
    }

}



