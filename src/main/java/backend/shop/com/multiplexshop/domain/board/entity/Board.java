package backend.shop.com.multiplexshop.domain.board.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

    public class Board {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long boardId;

        @Column(length = 30,nullable=false)
        private String boardTitle;

        @Column(length = 500,nullable=false)
        private String boardContent;

        @

    }
