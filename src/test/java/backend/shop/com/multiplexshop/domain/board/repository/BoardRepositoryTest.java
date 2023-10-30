package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("해당 게시물번호의 조회수가 +1 증가한다.")
    @Transactional
    public void updateCount(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("abc123")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .boardTitle("테스트")
                .boardContent("테스트 내용")
                .member(saveMember)
                .memberName("작성자")
                .boardViewCount(0L)
                .build();
        Board saveBoard = boardRepository.save(board);
        Long requestBoardId = 1L;
        //when
        boardRepository.updateCount(requestBoardId);
        //then
        Board result = boardRepository.findById(1L).orElse(null);
        assertThat(result.getBoardViewCount()).isEqualTo(1);
    }
}