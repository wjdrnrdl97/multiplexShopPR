package backend.shop.com.multiplexshop.domain.board.controller;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardResponseDTO;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
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

    @GetMapping("/api/support/{id}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("id") Long id) {
        Board getBoard = boardService.getBoard(id);
        return ResponseEntity.ok().body(new BoardResponseDTO(getBoard));
    }


    @GetMapping("/api/support")
    public ResponseEntity<List<BoardResponseDTO>> getBoardList() {
        List<BoardResponseDTO> list = boardService.getBoardList().stream().map(BoardResponseDTO::new).toList();
        return ResponseEntity.ok().body(list);
    }
}
