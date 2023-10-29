package backend.shop.com.multiplexshop.domain.board.dto;


import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.entity.BoardType;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
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
        private BoardType boardType;
        private Member member;

        @Builder
        public BoardResponseDTO(Long boardId, Long boardViewCount, LocalDateTime modDate, LocalDateTime regDate, String memberName, String boardTitle, String boardContent, BoardType boardType, Member member) {
            this.boardId = boardId;
            this.boardViewCount = boardViewCount;
            this.modDate = modDate;
            this.regDate = regDate;
            this.memberName = memberName;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
            this.boardType = boardType;
            this.member = member;
        }

        public static BoardResponseDTO of(Board board){
            return BoardResponseDTO.builder()
                    .boardId(board.getBoardId())
                    .boardViewCount(board.getBoardViewCount())
                    .modDate(board.getModDate())
                    .regDate(board.getRegDate())
                    .memberName(board.getMemberName())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .boardType(board.getBoardType())
                    .member(board.getMember())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BoardRequestDTO {
        private Long memberId;

        @NotBlank(message = "제목을 입력해주세요.")
        private String boardTitle;
        @NotBlank(message = "내용을 입력해주세요.")
        private String boardContent;

        private BoardType boardType;

        @Builder
        public  BoardRequestDTO(Long memberId,String boardTitle,String boardContent, BoardType boardType){
            this.memberId = memberId;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
            this.boardType = boardType;
        }
    }

}
