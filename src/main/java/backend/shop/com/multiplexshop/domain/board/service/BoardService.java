package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.PostBoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public interface BoardService {

    public Board getBoard(Long id);

    public List<Board> getBoardList();
    public Board postBoard(PostBoardRequestDTO postBoardRequestDTO);
}
