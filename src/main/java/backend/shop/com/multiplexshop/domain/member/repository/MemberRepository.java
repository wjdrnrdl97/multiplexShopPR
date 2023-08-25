package backend.shop.com.multiplexshop.domain.member.repository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
