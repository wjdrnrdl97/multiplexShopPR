package backend.shop.com.multiplexshop.domain.board.controller;




import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;


@RestController
@RequiredArgsConstructor
public class BoardAPIController {

    private final BoardService boardService;


    /**
     * 게시물 상세 조회
     * @param id (@PathVariable, 게시물 번호)
     * @return : Http 200 OK, body(getBoard)
     */
//    @GetMapping("/api/support/{id}")
//    public ResponseEntity<BoardResponseDTO> getUserBoard(@PathVariable("id") Long id) {
//        Board getBoard = boardService.findById(id);
//        return ResponseEntity.ok().body(new BoardResponseDTO(getBoard));
//    }

    /**
     * 게시물 목록 조회
     * @return : Http 200 ok, body(List(Board))
     */
    @GetMapping("/api/support")
    public ResponseEntity<Page<BoardResponseDTO>>
    getBoardList(@RequestParam(defaultValue = "0")int page, Model model) {
        Page<BoardResponseDTO> postPages = boardService.findByPost(page);

        return ResponseEntity.ok().body(postPages);
    }

    /**
     * 게시물 등록
     * @param boardRequestDTO (등록할 게시물 정보)
     * @return Http 201 created, body(postBoard(new Board))
     */
    @PostMapping("/api/support")
    public ResponseEntity<Board> postUserBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        Board postBoard = boardService.save(boardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postBoard);
    }

    /**
     * 게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정할 게시물 내용)
     * @return Board(수정한 게시물)
     */
    @PutMapping("/api/support/{id}")
    public ResponseEntity<Board> updateUserBoard(@PathVariable("id") Long boardId,
                                                 @RequestBody BoardRequestDTO boardRequestDTO){
        Board updateBoard = boardService.update(boardId, boardRequestDTO);
        return ResponseEntity.ok().body(updateBoard);
    }

    @DeleteMapping("/api/support/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") Long boardId){
        boardService.delete(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/support/{id}")
    public ResponseEntity<BoardResponseDTO> upgradeViewCount(@CookieValue("boardView")String boardView,
                                                             @PathVariable("id") Long boardId,
                                                             HttpServletResponse response, HttpServletRequest request){
        Board board = boardService.viewCountValidation(boardId,request,response);
        return ResponseEntity.ok().body(new BoardResponseDTO(board));
    }
}
