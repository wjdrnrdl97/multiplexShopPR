package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
public interface BoardService {

    public Board findByID(Long id);

    public Board postBoard(BoardDTOs.PostBoardRequestDTO postBoardRequestDTO);
}
