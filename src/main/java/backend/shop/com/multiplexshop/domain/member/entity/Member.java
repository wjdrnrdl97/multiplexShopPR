package backend.shop.com.multiplexshop.domain.member.entity;


import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 30, nullable = false)
    private String memberEmailId;

    @Column(length = 30,nullable = false)
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


    @Builder
    public Member(Long memberId, String memberEmailId, String password, String memberName, String memberAddress, String phoneNumber, Role role) {
        this.memberId = memberId;
        this.memberEmailId = memberEmailId;
        this.password = password;
        this.memberName = memberName;
        this.memberAddress = memberAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

}
