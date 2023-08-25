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

}
