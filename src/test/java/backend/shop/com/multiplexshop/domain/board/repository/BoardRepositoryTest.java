package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.engine.AttributeDefinition;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("save(): 게시물 생성에 성공한다.")
    public void test1(){

        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .role(Role.USER)
                .build();
        Board board = Board.builder()
                .member(member)
                .boardTitle("테스트입니다.")
                .boardContent("내용입니다.")
                .memberName(member.getMemberName())
                .build();
        //when
        Board savedBoard = boardRepository.save(board);
        //then
        assertThat(savedBoard.getBoardTitle()).isEqualTo("테스트입니다.");

    }
}