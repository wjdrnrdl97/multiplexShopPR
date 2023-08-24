package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

public class BoardDTOs {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetBoardResponseDTO{
        private Long boardId;
        private String boardTitle;
        private String boardContent;
        private LocalDateTime regDate;
        private Long boardViewCount;
        private String memberName;

        public GetBoardResponseDTO(Board board){
            this.boardId = board.getBoardId();
            this.boardTitle = board.getBoardTitle();
            this.boardContent = board.getBoardContent();
            this.regDate = board.getRegDate();
            this.boardViewCount = board.getBoardViewCount();
            this.memberName = board.getId().getMemberName();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetBoardListResponseDTO{
        private Long boardId;
        private String boardTitle;
        private String memberName;
        private LocalDateTime regDate;

        public GetBoardListResponseDTO(Board board){
            this.boardId = board.getBoardId();
            this.boardTitle=board.getBoardTitle();
            this.memberName=board.getId().getMemberName();
            this.regDate=board.getRegDate();
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

