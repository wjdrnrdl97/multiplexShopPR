package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/api/comment")
    public ResponseEntity<List<CommentResponseDTO>> getCommentListAPI(){
        List<CommentResponseDTO> commentResponseDTOList
                = commentService.findAll()
                .stream()
                .map(CommentResponseDTO::new)
                .toList();

        return ResponseEntity.ok().body(commentResponseDTOList);

    }

    @GetMapping("/comment{boardId}")
    public String  getCommentList(@PathVariable Long boardId, Model model){
        List<CommentResponseDTO> commentResponseDTOList
                = commentService.findAll()
                .stream()
                .map(CommentResponseDTO::new)
                .toList();

        model.addAttribute("comment",commentResponseDTOList);
        return "/reply/comment";
    }

    @PostMapping("/api/comment")
    public ResponseEntity<Comment> postComment (@RequestBody CommentRequestDTO commentRequestDTO){
        Comment comment = commentService.save(commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/api/comment/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long commentId,
                                                 @RequestBody CommentRequestDTO commentRequestDTO){
        Comment comment = commentService.update(commentId,commentRequestDTO);
        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id")Long commentId) {
          commentService.deleteCommentById(commentId);
          return ResponseEntity.ok().build();
    }
}
