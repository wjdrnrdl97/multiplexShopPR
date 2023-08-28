package backend.shop.com.multiplexshop.domain.board.controller;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardResponseDTO;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserBoardAPIController {
    private final BoardService boardService;

    /**
     * 게시물 상세조회
     * @param id (@PathVariable)
     * @return : Http 200 OK, body(getBoard)
     */
    @GetMapping("/api/support/{id}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("id") Long id) {
        Board getBoard = boardService.getBoard(id);
        return ResponseEntity.ok().body(new BoardResponseDTO(getBoard));
    }

    /**
     * 게시물 목록조회
     * @return : Http 200 ok, body(List(Board))
     */
    @GetMapping("/api/support")
    public ResponseEntity<List<BoardResponseDTO>> getBoardList() {
        List<BoardResponseDTO> list = boardService.getBoardList().stream().map(BoardResponseDTO::new).toList();
        return ResponseEntity.ok().body(list);
    }

    /**
     * 게시물 등록
     * @param boardRequestDTO
     * @return Http 201 created, body(postBoard(new Board))
     */
    @PostMapping("/api/support")
    public ResponseEntity<Board> postBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        Board postBoard = boardService.postBoard(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postBoard);
    }

    /**
     * 게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정할 게시물 내용)
     * @return Board(수정한 게시물)
     */

    @PutMapping("/api/support/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long boardId,
                                             @RequestBody BoardRequestDTO boardRequestDTO){
        Board updateBoard = boardService.updateBoard(boardId,boardRequestDTO);
        return ResponseEntity.ok().body(updateBoard);
    }

    @DeleteMapping("/api/support/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") Long boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}
