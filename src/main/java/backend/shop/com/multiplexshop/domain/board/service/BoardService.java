package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     *  게시물 상세 조회
     * @param id (조회할 게시물 번호)
     * @return Board(조회 상세정보)
     */
    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board not Found" + id));
    }

    /**
     *  게시물 목록 조회
     * @return List<Board>
     */
    public List<Board> findByAll() {
        return boardRepository.findAll();
    }

    /**
     *  게시물 등록
     * @param boardRequestDTO (등록할 게시물 정보)
     * @return Board
     */
    public Board save(BoardRequestDTO boardRequestDTO) {
        Board board = dtoToBoardEntity(boardRequestDTO);
        return boardRepository.save(board);
    }

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    @Transactional
    public Board update(Long boardId, BoardRequestDTO boardRequestDTO){
        Board getBoard = boardRepository.findById(boardId).get();
        getBoard.updateBoard(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContent());
        return boardRepository.save(getBoard);
    }

    /**
     * 게시물 삭제
     * @param boardId (삭제할 게시물 번호)
     */

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalStateException("Board not Found" + boardId));
        boardRepository.deleteById(boardId);
    }

    /**
     * 요청한 dto 정보 Entity 변환
     * @param boardRequestDTO (요청정보)
     * @return Board(Entity)
     */
    public Board dtoToBoardEntity(BoardRequestDTO boardRequestDTO) {
        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
            .orElseThrow(()->new IllegalArgumentException("Member not found"));
        return Board.builder()
                .boardTitle(boardRequestDTO.getBoardTitle())
                .boardContent(boardRequestDTO.getBoardContent())
                .member(member)
                .memberName(member.getMemberName())
                .boardType(boardRequestDTO.getBoardType())
                .build();
    }

    }



