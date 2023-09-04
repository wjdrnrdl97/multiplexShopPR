package backend.shop.com.multiplexshop.domain.comment.service;

import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private Comment searchById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("등록되지않는 번호입니다." + commentId));
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }
    public Comment save(CommentRequestDTO commentRequestDTO){
        Comment comment = Comment.dtoToCommentEntity(commentRequestDTO);
        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = searchById(commentId);
        commentRepository.delete(comment);
    }


}

