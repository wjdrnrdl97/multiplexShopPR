package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.entity.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {


    @Query("select b from Board b where b.boardType = 'NOTICE'")
    List<Board> findByNotie();
    @Query("select b from Board b where b.boardType = 'POST'")
    Page<Board> findByPost(PageRequest pageable);
}
