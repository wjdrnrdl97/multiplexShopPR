package backend.shop.com.multiplexshop.domain.board.service;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



/**
 *
 */
@Service
public interface BoardService {

    /**
     *  게시물 상세조회
     * @param id / BoardId
     * @return Board / exception
     */
    public Board getBoard(Long id);

    /**
     *  게시물목록 조회
     * @return List<Board>
     */
    public List<Board> getBoardList();

    /**
     *  게시물 등록
     * @param boardRequestDTO
     * @return Board
     */
    public Board postBoard(BoardRequestDTO boardRequestDTO);

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    public Board updateBoard(Long boardId, BoardRequestDTO boardRequestDTO);

    public void deleteBoard(Long boardId);
}
