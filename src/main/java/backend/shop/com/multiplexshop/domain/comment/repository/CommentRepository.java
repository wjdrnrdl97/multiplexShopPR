package backend.shop.com.multiplexshop.domain.comment.repository;

import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
