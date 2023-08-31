package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardList(Model model){
        List<Board> boardList = boardService.findByAll();
        ArrayList<Board> noticeList = new ArrayList<>();
        ArrayList<Board> postList = new ArrayList<>();
        NoticeBoardValidation(boardList, noticeList, postList);


        model.addAttribute("notice", noticeList);
        model.addAttribute("post",postList);
        return "support/board";
    }

    private static void NoticeBoardValidation
            (List<Board> boardList, ArrayList<Board> noticeList, ArrayList<Board> postList) {
        for (Board board : boardList) {
            if (board.getBoardType().label().equals("NOTICE")){
                noticeList.add(board);
            }else if (board.getBoardType().label().equals("POST")){
                postList.add(board);
            }
        }
    }

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId, Model model){
        Board getBoard = (Board) boardService.findById(boardId);
        model.addAttribute("getBoard",getBoard);
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
