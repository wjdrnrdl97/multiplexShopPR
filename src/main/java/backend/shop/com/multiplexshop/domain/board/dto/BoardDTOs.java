package backend.shop.com.multiplexshop.domain.board.dto;


import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.entity.BoardType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
public class BoardDTOs {

    @Getter
    @NoArgsConstructor
    public static class BoardResponseDTO{
        private Long boardId;
        private Long boardViewCount;
        private LocalDateTime modDate;
        private LocalDateTime regDate;
        private String memberName;
        private String boardTitle;
        private String boardContent;
        private String boardType;
        private Long memberId;

        public BoardResponseDTO(Board board){
            this.boardId = board.getBoardId();
            this.boardViewCount = board.getBoardViewCount();
            this.boardTitle = board.getBoardTitle();
            this.boardContent = board.getBoardContent();
            this.memberName = board.getMemberName();
            this.regDate = board.getRegDate();
            this.modDate = board.getModDate();
            this.boardType = board.getBoardType().label();
            this.memberId = board.getMember().getMemberId();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BoardRequestDTO {
        private Long memberId;
        private String boardTitle;
        private String boardContent;

        @Builder
        public  BoardRequestDTO(Long memberId,String boardTitle,String boardContent, String boardType){
            this.memberId = memberId;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
        }
    }

}
