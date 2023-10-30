package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardResponseDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.entity.BoardType;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class BoardServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("요청에 따라 해당 번호의 게시물을 조회한다.")
    public void findBoardByboardId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        boardRepository.save(board);
        //when
        BoardResponseDTO result = boardService.findBoardByboardId(1L);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getBoardTitle()).isEqualTo("제목");
    }
    @Test
    @DisplayName("보드타입이 'NOTICE'인 게시물을 전체 조회한다.")
    public void findAllByNotice(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        for(int i = 0; i<5; i++){
            Board notice = Board.builder()
                    .member(saveMember)
                    .boardTitle("제목" + i)
                    .boardContent("내용")
                    .memberName(saveMember.getMemberName())
                    .boardType(BoardType.NOTICE)
                    .build();
            boardRepository.save(notice);
            Board post = Board.builder()
                    .member(saveMember)
                    .boardTitle("제목" + i)
                    .boardContent("내용")
                    .memberName(saveMember.getMemberName())
                    .boardType(BoardType.POST)
                    .build();
            boardRepository.save(post);
        }
        //when
        List<BoardResponseDTO> result = boardService.findAllByNotice();
        //then
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getBoardType()).isEqualTo(BoardType.NOTICE);
    }
    @Test
    @DisplayName("보드타입이 'POST'인 게시물을 전체 조회하여 페이징 처리한다.")
    public void findAllByPost(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        for(int i = 0; i<5; i++){
            Board notice = Board.builder()
                    .member(saveMember)
                    .boardTitle("제목" + i)
                    .boardContent("내용")
                    .memberName(saveMember.getMemberName())
                    .boardType(BoardType.NOTICE)
                    .build();
            boardRepository.save(notice);
            Board post = Board.builder()
                    .member(saveMember)
                    .boardTitle("제목" + i)
                    .boardContent("내용")
                    .memberName(saveMember.getMemberName())
                    .boardType(BoardType.POST)
                    .build();
            boardRepository.save(post);
        }
        int page = 1;
        //when
        Page<BoardResponseDTO> result = boardService.findAllByPost(page);
        //then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getNumberOfElements()).isEqualTo(5);
    }
    @Test
    @DisplayName("요청에 따라 게시물을 생성한다.")
    public void createBoardByRequest(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        BoardRequestDTO request = BoardRequestDTO.builder()
                .memberId(1L)
                .boardTitle("테스트")
                .boardContent("내용")
                .boardType(BoardType.POST)
                .build();
        //when
        BoardResponseDTO result = boardService.createBoardByRequest(request);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getBoardTitle()).isEqualTo("테스트");
    }
    @Test 
    @DisplayName("요청에 따라 해당 게시물 번호로 게시물을 조회한 후 요청한 내용으로 수정한다.")    
    public void updateBoardInfoByRequest(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        boardRepository.save(board);
        Long requestBoardId = 1L;
        BoardRequestDTO request = BoardRequestDTO.builder()
                .memberId(1L)
                .boardTitle("테스트 수정")
                .boardContent("내용 수정")
                .boardType(BoardType.POST)
                .build();
        //when
        BoardResponseDTO result = boardService.updateBoardInfoByRequest(requestBoardId, request);
        //then
        assertThat(result.getBoardTitle()).isEqualTo("테스트 수정");
        assertThat(result.getBoardContent()).isEqualTo("내용 수정");
    }
    @Test
    @DisplayName("요청에 따라 해당 게시물을 삭제한다.")
    @Transactional
    public void deleteByRequest(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        Board board = Board.builder()
                .member(saveMember)
                .boardTitle("제목")
                .boardContent("내용")
                .memberName(saveMember.getMemberName())
                .build();
        boardRepository.save(board);
        //when
        boardService.deleteByRequest(1L);
        //then
        List<Board> result = boardRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("dto를 엔티티 변환하는데 성공한다.")
    public void dtoToBoardEntity(){
        //given
        Member member = Member.builder()
                .memberEmailId("test@naver.com")
                .password("1234")
                .memberName("테스트")
                .build();
        Member saveMember = memberRepository.save(member);
        BoardRequestDTO request = BoardRequestDTO.builder()
                .memberId(1L)
                .boardTitle("테스트")
                .boardContent("내용")
                .boardType(BoardType.POST)
                .build();
        //when
        Board result = boardService.dtoToBoardEntity(request);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getMemberName()).isEqualTo("테스트");

    }
}