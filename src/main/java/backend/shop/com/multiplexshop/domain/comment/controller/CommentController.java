package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

//@RestController
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    /**
     * 게시물을 상세조회 했을때 api 방식으로  (@param boardId 해당 게시물)의 댓글 데이터를 조회하는 메서드
     * @param boardId
     * @param model
     * @return
     */
    @GetMapping(value = "/api/comment/{boardId}")
    public String getCommentList(@PathVariable Long boardId, Model model){
        List<CommentResponseDTO> commentResponseDTOList
                = commentService.findAllByBoard(boardId);

        model.addAttribute("comment",commentResponseDTOList);
        return "reply/comment";
    }




    @ResponseBody
    @PostMapping(value = "/api/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentResponseDTO> postComment (@RequestBody CommentRequestDTO commentRequestDTO){
        Comment comment = commentService.save(commentRequestDTO);
        CommentResponseDTO response = CommentResponseDTO.of(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ResponseBody
    @PutMapping(value = "/api/comment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long commentId,
                                                 @RequestBody CommentRequestDTO commentRequestDTO){
        Comment comment = commentService.update(commentId,commentRequestDTO);
        return ResponseEntity.ok().body(comment);
    }

    //수정 요청 뷰 메서드
    @GetMapping("/api/CommentUpdate/")
    public String getUpdateTag(){
        return "reply/commentUpdate";
    }

    @ResponseBody
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id")Long commentId) {
          commentService.deleteCommentById(commentId);
          return ResponseEntity.ok().build();
    }
}
