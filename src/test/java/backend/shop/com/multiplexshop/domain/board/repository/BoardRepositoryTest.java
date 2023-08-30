package backend.shop.com.multiplexshop.domain.board.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
//
//    @Test
//    @DisplayName("save(): 게시물 생성에 성공한다.")
//    public void test1(){
//
//        //given
//        Member member = Member.builder()
//                .memberEmailId("test")
//                .password("1234")
//                .memberName("테스트")
//                .role(Role.USER)
//                .build();
//        Board board = Board.builder()
//                .member(member)
//                .boardTitle("테스트입니다.")
//                .boardContent("내용입니다.")
//                .memberName(member.getMemberName())
//                .build();
//        //when
//        Board savedBoard = boardRepository.save(board);
//        //then
//        assertThat(savedBoard.getBoardTitle()).isEqualTo("테스트입니다.");
//
//    }
}