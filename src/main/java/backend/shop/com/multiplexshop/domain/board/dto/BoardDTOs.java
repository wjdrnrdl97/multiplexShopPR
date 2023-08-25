package backend.shop.com.multiplexshop.domain.board.dto;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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
    @RequiredArgsConstructor
    @Builder
    public static class BoardRequestDTO {

        @Autowired
        MemberRepository memberRepository;

        private Long boardId;
        private Long boardViewCount;
        private Long memberId;
        private String memberName;
        private String boardTitle;
        private String boardContent;

        public Board toBoard() {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException(memberId+"번 글은  찾을 수 없는 게시물입니다."));
                //
            return Board.builder()
                    .boardId(this.boardId)
                    .boardViewCount(this.boardViewCount)
                    .memberName(this.memberName)
                    .boardTitle(this.boardTitle)
                    .boardContent(this.boardContent)
                    .member(member)
                    .build();
        }
    }
}

