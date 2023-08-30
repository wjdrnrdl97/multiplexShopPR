package backend.shop.com.multiplexshop.domain.board.service;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 */
@Service
public interface BoardService<T> {
    /**
     *  유저게시물 상세조회
     * @param id / BoardId
     * @return Board / exception
     */
    public T findById(Long id);

    /**
     *  유저게시물목록 조회
     * @return List<Board>
     */
    public List<T> findByAll();

    /**
     *  유저게시물 등록
     * @param BoardRequestDTO
     * @return Board
     */
    public T save(BoardRequestDTO BoardRequestDTO);

    /**
     *  유저게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    public T update(Long boardId, BoardRequestDTO boardRequestDTO);

    public void delete(Long boardId);

    public T dtoToBoardEntity(BoardRequestDTO boardRequestDTO);

}
