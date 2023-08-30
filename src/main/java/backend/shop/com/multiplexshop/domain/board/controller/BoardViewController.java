package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.repository.UserBoardRepository;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Controller
@RequestMapping("/support")
public class BoardViewController {
    private final BoardService boardService;
    private final UserBoardRepository userBoardRepository;

    public BoardViewController(@Qualifier("userBoard") BoardService boardService, UserBoardRepository userBoardRepository) {
        this.boardService = boardService;
        this.userBoardRepository = userBoardRepository;
    }

    @GetMapping()
    public String getBoardList(Model model){
        List<UserBoard> userBoardList = boardService.findByAll();
        model.addAttribute("userBoardList", userBoardList);
        return "support/board";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Long boardId, Model model){
        UserBoard getBoard = (UserBoard) boardService.findById(boardId);
        model.addAttribute("getBoard",getBoard);
        return "support/read";
    }
    @GetMapping("/post")
    public String updateBoard(@RequestParam(required = false) Long boardId, Model model){

        if(boardId==null){
            model.addAttribute("Board", new BoardResponseDTO());
        }else {
            UserBoard board = (UserBoard) boardService.findById(boardId);
            model.addAttribute("Board", new BoardResponseDTO(board));
        }

        return "support/createBoard";
    }
}
