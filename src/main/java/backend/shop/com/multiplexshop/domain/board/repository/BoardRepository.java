package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Modifying
    @Query("UPDATE Board b SET b.boardViewCount = b.boardViewCount + 1 WHERE b.boardId = :id")
    void updateCount(@Param("id") Long id);

    @Query("select b from Board b where b.boardType = 'NOTICE'")
    List<Board> findByNotice();

    @Query("select b from Board b where b.boardType = 'POST'")
    Page<Board> findByPost(PageRequest pageable);

}
