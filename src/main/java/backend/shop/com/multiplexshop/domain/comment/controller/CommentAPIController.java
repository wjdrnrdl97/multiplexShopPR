package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

@RestController
@RequiredArgsConstructor
public class CommentAPIController {

    private final CommentService commentService;
    @GetMapping("/api/comment")
    public ResponseEntity<List<CommentResponseDTO>> getCommentList(){

        return null;
    }

    @PostMapping("/api/comment")
    public ResponseEntity<Comment> postComment (@RequestBody CommentRequestDTO commentRequestDTO){
        return null;
    }

    @PutMapping("/api/comment/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long commentId,
                                                 @RequestBody CommentRequestDTO commentRequestDTO){
        return null;
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id")Long commentId) {
          commentService.deleteCommentById(commentId);
          return ResponseEntity.ok().build();
    }
}
