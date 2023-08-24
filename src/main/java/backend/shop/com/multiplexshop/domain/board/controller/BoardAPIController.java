package backend.shop.com.multiplexshop.domain.board.controller;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.GetBoardResponseDTO;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.GetBoardListResponseDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardAPIController {
    private final BoardService boardService;

    @GetMapping("/api/support/{no}")
    public ResponseEntity<GetBoardResponseDTO> getBoard(@PathVariable Long id){
        Board getBoard =  boardService.getBoard(id);
        return ResponseEntity.ok().body(new GetBoardResponseDTO(getBoard));
    }


//    @GetMapping
//    public ResponseEntity<List<GetBoardListResponseDTO>> getBoardList(){
//        List<Board> getBoardList = boardService.getBoardList().stream().toList();
//        return ResponseEntity.ok().body(new List<GetBoardListResponseDTO>();
}
