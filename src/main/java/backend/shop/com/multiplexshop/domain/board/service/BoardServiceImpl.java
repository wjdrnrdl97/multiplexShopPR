package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    @Override
    public Board findByID(Long id){
        return boardRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("Board not found with " + id ));
    }

    @Override
    public Board postBoard(BoardDTOs.PostBoardRequestDTO postBoardRequestDTO) {
        return boardRepository.save(postBoardRequestDTO.toBoard());
    }
}
