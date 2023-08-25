package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoardDTOs {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BoardResponseDTO {
        private Long boardId;
        private Long boardViewCount;
        private Member member;
        private LocalDateTime modDate;
        private LocalDateTime regDate;
        private String memberName;
        private String boardTitle;
        private String boardContent;

        public BoardResponseDTO(Board board) {
            this.boardId = board.getBoardId();
            this.boardViewCount = board.getBoardViewCount();
            this.member = board.getMember();
            this.modDate = board.getModDate();
            this.regDate = board.getRegDate();
            this.memberName = board.getMember().getMemberName();
            this.boardTitle = board.getBoardTitle();
            this.boardContent = board.getBoardContent();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BoardRequestDTO {
        private Long boardId;
        private Long boardViewCount;
        private Member member;
        private String memberName;
        private String boardTitle;
        private String boardContent;

        public Board toBoard() {
            return Board.builder()
                    .boardId(this.boardId)
                    .boardViewCount(this.boardViewCount)
                    .member(this.member)
                    .memberName(this.memberName)
                    .boardTitle(this.boardTitle)
                    .boardContent(this.boardContent)
                    .build();
        }
    }
}

