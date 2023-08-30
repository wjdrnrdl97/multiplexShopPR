package backend.shop.com.multiplexshop.domain.board.controller;



import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;


@RestController
public class UserBoardAPIController {

    private final BoardService<UserBoard> boardService;


    public UserBoardAPIController(@Qualifier("userBoard") BoardService<UserBoard> boardService) {
        this.boardService = boardService;
    }

    /**
     * 게시물 상세조회
     * @param id (@PathVariable)
     * @return : Http 200 OK, body(getBoard)
     */
    @GetMapping("/api/support/{id}")
    public ResponseEntity<BoardResponseDTO> getUserBoard(@PathVariable("id") Long id) {
        UserBoard getUserBoard = boardService.findById(id);
        return ResponseEntity.ok().body(new BoardResponseDTO(getUserBoard));
    }

    /**
     * 게시물 목록조회
     * @return : Http 200 ok, body(List(Board))
     */
    @GetMapping("/api/support")
    public ResponseEntity<List<BoardResponseDTO>> getUserBoardList() {
        List<BoardResponseDTO> list = boardService.findByAll().stream().map(BoardResponseDTO::new).toList();
        return ResponseEntity.ok().body(list);
    }

    /**
     * 게시물 등록
     * @param boardRequestDTO
     * @return Http 201 created, body(postBoard(new Board))
     */
    @PostMapping("/api/support")
    public ResponseEntity<UserBoard> postUserBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        UserBoard postUserBoard = boardService.save(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postUserBoard);
    }

    /**
     * 게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정할 게시물 내용)
     * @return UserBoard(수정한 게시물)
     */
    @PutMapping("/api/support/{id}")
    public ResponseEntity<UserBoard> updateUserBoard(@PathVariable("id") Long boardId,
                                                 @RequestBody BoardRequestDTO boardRequestDTO){
        UserBoard updateUserBoard = boardService.update(boardId, boardRequestDTO);
        return ResponseEntity.ok().body(updateUserBoard);
    }

    @DeleteMapping("/api/support/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") Long boardId){
        boardService.delete(boardId);
        return ResponseEntity.ok().build();
    }
}
