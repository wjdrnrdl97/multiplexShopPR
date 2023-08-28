package backend.shop.com.multiplexshop.domain.notice.entity;

import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(length = 30,nullable = false)
    private String noticeTitle;

    @Column(length = 500,nullable = false)
    private String noticeContent;

    @Column(length = 20,nullable = false)
    private String memberName;

    @Column(columnDefinition = "integer default 0")
    private Long noticeViewCount;


}
