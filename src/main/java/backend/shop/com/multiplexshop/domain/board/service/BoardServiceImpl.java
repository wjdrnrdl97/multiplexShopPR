package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public Board postBoard(BoardRequestDTO boardRequestDTO) {
        return boardRepository.save(boardRequestDTO.toBoard());
    }

    @Override
    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDTO boardRequestDTO) {
        Board getBoard = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("Board not Found" + boardId));
        getBoard.updateBoard(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContent());
        return boardRepository.save(getBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("Board not Found" + boardId));
        boardRepository.deleteById(boardId);
    }
}

