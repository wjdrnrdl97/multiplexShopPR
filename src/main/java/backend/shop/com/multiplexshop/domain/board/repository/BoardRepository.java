package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Modifying
    @Query("UPDATE Board b SET b.boardViewCount = b.boardViewCount + 1 WHERE b.boardId = :id")
    void updateCount(@Param("id") Long id);


}
