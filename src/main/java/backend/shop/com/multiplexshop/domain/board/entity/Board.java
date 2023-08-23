package backend.shop.com.multiplexshop.domain.board.entity;


import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class Board extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long boardId;

        @Column(length = 30,nullable=false)
        private String boardTitle;

        @Column(length = 500,nullable=false)
        private String boardContent;

    }
