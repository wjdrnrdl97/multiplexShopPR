package backend.shop.com.multiplexshop.domain.board.entity;


import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long boardId;

        @ManyToOne(fetch = FetchType.LAZY)
        private Member member;

        @Column(length = 30,nullable=false)
        private String boardTitle;

        @Column(length = 500,nullable=false)
        private String boardContent;

        @Column(length = 20,nullable = false)
        private String memberName;

        @Column
        private Long boardViewCount;


}
