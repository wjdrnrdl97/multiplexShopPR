package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CommentController {

    private final CommentService commentService;

    /**
     * 게시물을 상세조회 했을때 api 방식으로  (@param  해당 게시물)의 댓글 데이터를 조회하는 메서드
     * @param
     * @param model
     * @return
     */
    @GetMapping(value = "/api/comment/{id}")
    public String getCommentList(@PathVariable Long id, Model model){
        List<CommentResponseDTO> commentResponseDTOList
                = commentService.findAllByBoard(id);

        model.addAttribute("comment",commentResponseDTOList);
        return "reply/comment";
    }




    @ResponseBody
    @PostMapping(value = "/api/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentResponseDTO> postComment (@RequestBody CommentRequestDTO commentRequestDTO){
        CommentResponseDTO createComment = commentService.createCommentByRequest(commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
    }

    @ResponseBody
    @PutMapping(value = "/api/comment/{id}")
    public ResponseEntity updateComment(@PathVariable("id") Long commentId,
                                        @RequestBody CommentRequestDTO commentRequestDTO){
        log.info("PUT REQUEST = {}", commentRequestDTO);
        commentService.updateCommentByRequest(commentId, commentRequestDTO);
        return ResponseEntity.noContent().build();
    }

    //수정 요청 뷰 메서드
    @GetMapping("/api/CommentUpdate/{commentId}")
    public String getUpdateTag(Model model, @PathVariable("commentId")Long commentId){
        CommentResponseDTO findCommentByRequest = commentService.findCommentById(commentId);
        model.addAttribute("reply",findCommentByRequest);

        return "reply/commentUpdate";
    }

    @ResponseBody
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id")Long commentId) {
          commentService.deleteCommentById(commentId);
          return ResponseEntity.noContent().build();
    }
}
