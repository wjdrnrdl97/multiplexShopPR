package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.PostBoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;


    @Override
    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board not Found" + id));
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public Board postBoard(PostBoardRequestDTO postBoardRequestDTO) {
        return boardRepository.save(postBoardRequestDTO.toBoard());
    }
}
