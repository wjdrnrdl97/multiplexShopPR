package backend.shop.com.multiplexshop.domain.member.entity;



import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

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
    public Member(Long id, String memberEmailId, String password, String memberName, String memberAddress, String phoneNumber, Role role) {
        this.id = id;
        this.memberEmailId = memberEmailId;
        this.password = password;
        this.memberName = memberName;
        this.memberAddress = memberAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void updateMember(String memberAddress, String phoneNumber){
        this.memberAddress = memberAddress;
        this.phoneNumber = phoneNumber;
    }

}
