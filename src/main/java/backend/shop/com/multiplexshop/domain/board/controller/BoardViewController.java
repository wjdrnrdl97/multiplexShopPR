package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.board.service.UserBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardList(Model model){
        List<UserBoard> userBoardList = boardService.getBoardList();
        model.addAttribute("userBoardList", userBoardList);
        return "support/board";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Long boardId, Model model){
        UserBoard getBoard = boardService.getBoard(boardId);
        model.addAttribute("getBoard",getBoard);
        return "support/read";
    }

    @GetMapping("/post")
    public String postBoard(){
        return "support/createBoard";
    }

    @GetMapping("/post/{id}")
    public String updateBoard(@PathVariable("id") Long boardId){
        return "support/createBoard";
    }
}
