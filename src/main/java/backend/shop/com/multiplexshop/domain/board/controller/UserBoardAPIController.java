package backend.shop.com.multiplexshop.domain.board.controller;


import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.BoardResponseDTO;
import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
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
        UserBoard getUserBoard = boardService.getBoard(id);
        return ResponseEntity.ok().body(new BoardResponseDTO(getUserBoard));
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
    public ResponseEntity<UserBoard> postBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        UserBoard postUserBoard = boardService.postBoard(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postUserBoard);
    }

    /**
     * 게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정할 게시물 내용)
     * @return Board(수정한 게시물)
     */

    @PutMapping("/api/support/{id}")
    public ResponseEntity<UserBoard> updateBoard(@PathVariable("id") Long boardId,
                                                 @RequestBody BoardRequestDTO boardRequestDTO){
        UserBoard updateUserBoard = boardService.updateBoard(boardId,boardRequestDTO);
        return ResponseEntity.ok().body(updateUserBoard);
    }

    @DeleteMapping("/api/support/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") Long boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}
