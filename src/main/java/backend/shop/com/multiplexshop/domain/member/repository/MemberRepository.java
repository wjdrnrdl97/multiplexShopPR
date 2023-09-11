package backend.shop.com.multiplexshop.domain.member.repository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m where m.memberEmailId = :userEmail")
    public List<Member> findByEmail(@Param("userEmail") String userEmail);
}
