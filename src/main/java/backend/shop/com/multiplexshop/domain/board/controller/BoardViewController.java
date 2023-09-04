package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public String getBoardList(@RequestParam(defaultValue = "0")int page, Model model){
        List<BoardResponseDTO> noticePages = boardService.findByNotice();
        Page<BoardResponseDTO> postPages = boardService.findByPost(page);

        model.addAttribute("notice",noticePages);
        model.addAttribute("board",postPages);
        model.addAttribute("page",page);
        return "support/board";
    }



    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId,HttpServletRequest request,
                           @RequestParam(defaultValue = "0") int page,
                                                HttpServletResponse response, Model model){
        Board getBoard = boardService.viewCountValidation(boardId, request,response);
//        Board getBoard = boardService.findById(boardId);
//>>>>>>> feat_board_paging
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
            Board board = (Board) boardService.searchById(boardId);
            model.addAttribute("Board", new BoardResponseDTO(board));
            model.addAttribute("page",page);
        }

        return "support/createBoard";
    }
}
