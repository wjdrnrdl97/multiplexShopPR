package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.persistence.Access;
import lombok.*;

import java.time.LocalDateTime;

public class BoardDTOs {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class GetBoardResponseDTO{
        private Long boardNo;
        private String boardTitle;
        private String boardContent;
        private LocalDateTime regDate;
        private Long boardViewCount;
        private Member member;

        public GetBoardResponseDTO(Board board){
            this.boardNo = board.getBoardId();
            this.boardTitle = board.getBoardTitle();
            this.boardContent = board.getBoardContent();
            this.regDate = board.getRegDate();
            this.boardViewCount = board.getBoardViewCount();
            this.member = board.getMember();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class PostBoardRequestDTO{
        private Long boardId;
        private String boardTitle;
        private String boardContent;

        public Board toBoard(){
            return Board.builder()
                    .boardId(this.boardId)
                    .boardTitle(this.boardTitle)
                    .boardContent(this.boardContent)
                    .build();
        }
    }

    }

