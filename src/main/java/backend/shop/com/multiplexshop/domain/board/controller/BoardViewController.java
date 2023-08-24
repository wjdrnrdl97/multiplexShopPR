package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id")Long id,Model model){
        Board getBoard = boardService.findByID(id);
        model.addAttribute("dto",new BoardDTOs.GetBoardResponseDTO(getBoard));
        return "support/";
    }
}
