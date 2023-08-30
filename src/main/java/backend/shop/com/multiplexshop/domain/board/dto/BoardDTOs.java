package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Notice;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
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
        private Long memberId;

        // 공지사항
        public BoardResponseDTO(Notice notice){
            this.boardId = notice.getBoardId();
            this.boardTitle = notice.getBoardTitle();
            this.boardContent = notice.getBoardContent();
            this.memberName = notice.getMember().getMemberName();
            this.regDate = notice.getRegDate();
            this.modDate = notice.getModDate();
            this.boardViewCount = notice.getBoardViewCount();
            this.memberId = notice.getMember().getMemberId();
        }
        
        // 유저보드
        public BoardResponseDTO(UserBoard userBoard){
            this.boardId = userBoard.getBoardId();
            this.boardTitle = userBoard.getBoardTitle();
            this.boardContent = userBoard.getBoardContent();
            this.memberName = userBoard.getMember().getMemberName();
            this.regDate = userBoard.getRegDate();
            this.modDate = userBoard.getModDate();
            this.boardViewCount = userBoard.getBoardViewCount();
            this.memberId = userBoard.getMember().getMemberId();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class BoardRequestDTO {
        private Long memberId;
        private String boardTitle;
        private String boardContent;

    }

}



