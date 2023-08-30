package backend.shop.com.multiplexshop.domain.board.repository;

import backend.shop.com.multiplexshop.domain.board.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
