package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardList(Model model){
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("boardList",boardList);
        return "support/board";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Long boardId, Model model){
        Board getboard = boardService.getBoard(boardId);
        model.addAttribute("board",getboard);
        return "support/read";
    }

}
