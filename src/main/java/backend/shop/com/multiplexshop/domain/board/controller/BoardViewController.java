package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardList(@RequestParam(defaultValue = "0")int page, Model model){
        List<BoardResponseDTO> noticePages = boardService.findByNotice();
        Page<BoardResponseDTO> postPages = boardService.findByPost(page);

        model.addAttribute("notice",noticePages);
        model.addAttribute("board",postPages);
        model.addAttribute("page",page);
        return "support/board";
    }



    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId,
                           @RequestParam(defaultValue = "0") int page,
                           Model model){
        Board getBoard = boardService.findById(boardId);
        model.addAttribute("getBoard",new BoardResponseDTO(getBoard));
        model.addAttribute("page",page);
        return "support/read";
    }
    @GetMapping("/post")
    public String updateBoard(@RequestParam(required = false) Long boardId,
                              @RequestParam(defaultValue = "0")int page,
                              Model model){

        if(boardId==null){
            model.addAttribute("Board", new BoardResponseDTO());
            model.addAttribute("page",page);
        }else {
            Board board = (Board) boardService.findById(boardId);
            model.addAttribute("Board", new BoardResponseDTO(board));
            model.addAttribute("page",page);
        }

        return "support/createBoard";
    }
}
