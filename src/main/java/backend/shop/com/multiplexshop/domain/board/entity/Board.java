package backend.shop.com.multiplexshop.domain.board.entity;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.common.BaseEntity;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comments;

import java.util.ArrayList;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long boardId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "memberId",nullable = false)
        private Member member;

        @Column(length = 30,nullable=false)
        private String boardTitle;

        @Column(length = 500,nullable=false)
        private String boardContent;

        @Column(length = 20,nullable = false)
        private String memberName;

        @Column(columnDefinition = "bigint default 0")
        private Long boardViewCount = 0L;

        @Enumerated(EnumType.STRING)
        @Column(length = 10)
        private BoardType boardType;

        @Builder
        public Board(Long boardId, Member member, String boardTitle, String boardContent, String memberName,
                                                                        Long boardViewCount, BoardType boardType) {
                this.boardId = boardId;
                this.member = member;
                this.boardTitle = boardTitle;
                this.boardContent = boardContent;
                this.memberName = memberName;
                this.boardViewCount = boardViewCount;
                this.boardType = boardType;
        }

        /**
         *  비즈니스 로직 : 게시물 수정
         */
        public void updateBoard(String updateTitle, String updateContent){
                this.boardTitle = updateTitle;
                this.boardContent = updateContent;
        }


}
