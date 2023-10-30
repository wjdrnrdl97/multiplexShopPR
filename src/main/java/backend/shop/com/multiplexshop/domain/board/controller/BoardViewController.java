package backend.shop.com.multiplexshop.domain.board.controller;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;
import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;


@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class BoardViewController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping()
    public String getBoardList(@RequestParam(defaultValue = "0")int page, Model model){
        List<BoardResponseDTO> noticePages = boardService.findAllByNotice();
        Page<BoardResponseDTO> postPages = boardService.findAllByPost(page);

        model.addAttribute("notice",noticePages);
        model.addAttribute("board",postPages);
        model.addAttribute("page",page);
        return "support/board";
    }



    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId, HttpServletRequest request, HttpSession session,
                           @RequestParam(defaultValue = "0") int page, HttpServletResponse response, Model model){

        Board getBoard = boardService.viewCountValidation(boardId, request,response);
        List<CommentResponseDTO> commentResponseDTOList = commentService.findAllByBoard(boardId);

        MemberResponseDTO loginUser = (MemberResponseDTO) session.getAttribute("loginUser");

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("comment", commentResponseDTOList);
        model.addAttribute("getBoard", BoardResponseDTO.of(getBoard));
        model.addAttribute("page",page);
        return "support/read";
    }

    @GetMapping("/post")
    public String updateBoard(@RequestParam(required = false) Long boardId,
                              @RequestParam(defaultValue = "0")int page,
                              Model model, HttpSession session){

        MemberResponseDTO loginUser = (MemberResponseDTO) session.getAttribute("loginUser");
        if(boardId == null){
            model.addAttribute("login",loginUser);
            model.addAttribute("Board", new BoardResponseDTO());
            model.addAttribute("page",page);
        }else {
            BoardResponseDTO findBoardByBoardId = boardService.findBoardByboardId(boardId);
            model.addAttribute("Board", findBoardByBoardId);
            model.addAttribute("page",page);
        }

        return "support/createBoard";
    }

}
