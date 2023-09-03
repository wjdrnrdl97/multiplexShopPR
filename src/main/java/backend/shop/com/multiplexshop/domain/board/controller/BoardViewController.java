package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardList(Model model){
        List<BoardResponseDTO> boardList = boardService.findByAll().stream().map(BoardResponseDTO::new).toList();
        model.addAttribute("board",boardList);
        return "support/board";
    }

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId,HttpServletRequest request,
                                                HttpServletResponse response, Model model){
        Board getBoard = boardService.viewCountValidation(boardId, request,response);
        model.addAttribute("getBoard",new BoardResponseDTO(getBoard));
        return "support/read";
    }
    @GetMapping("/post")
    public String updateBoard(@RequestParam(required = false) Long boardId, Model model){

        if(boardId==null){
            model.addAttribute("Board", new BoardResponseDTO());
        }else {
            Board board = (Board) boardService.findById(boardId);
            model.addAttribute("Board", new BoardResponseDTO(board));
        }

        return "support/createBoard";
    }
}
