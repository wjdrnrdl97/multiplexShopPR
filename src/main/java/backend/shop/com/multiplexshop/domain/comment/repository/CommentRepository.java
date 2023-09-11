package backend.shop.com.multiplexshop.domain.comment.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //게시믈별 댓글목록 조회 메소드

    List<Comment> findAllByBoard(Board board);

    void deleteAllByBoard(Board board);



}
