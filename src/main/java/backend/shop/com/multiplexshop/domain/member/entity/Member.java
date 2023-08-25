package backend.shop.com.multiplexshop.domain.member.entity;

import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 30, nullable = false)
    private String memberEmailId;

    @Column(nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 100)
    private String memberAddress;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;
}
