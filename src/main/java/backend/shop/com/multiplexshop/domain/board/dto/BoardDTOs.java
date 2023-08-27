package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;


public class BoardDTOs {


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

        public BoardResponseDTO(Board board) {
            this.boardId = board.getBoardId();
            this.boardViewCount = board.getBoardViewCount();
            this.modDate = board.getModDate();
            this.regDate = board.getRegDate();
            this.memberName = board.getMember().getMemberName();
            this.boardTitle = board.getBoardTitle();
            this.boardContent = board.getBoardContent();
            this.memberId = board.getMember().getMemberId();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class BoardRequestDTO {
        private Long boardId;
        private Member member;
        private String memberName;
        private String boardTitle;
        private String boardContent;

        public Board toBoard() {
            return Board.builder()
                    .boardId(this.boardId)
                    .memberName(this.memberName)
                    .boardTitle(this.boardTitle)
                    .boardContent(this.boardContent)
                    .member(this.member)
                    .build();
        }
    }
}


