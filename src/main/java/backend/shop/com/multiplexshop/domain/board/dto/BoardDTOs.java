package backend.shop.com.multiplexshop.domain.board.dto;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BoardDTOs {
    @Getter
    @NoArgsConstructor(Access=AccessLevel.PROTECTED)
    @Builder
    public static class BoardResponseDTO(){
        private String boardTitle;
    }
}
