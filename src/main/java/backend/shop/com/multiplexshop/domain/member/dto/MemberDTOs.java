package backend.shop.com.multiplexshop.domain.member.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import lombok.*;

public class MemberDTOs {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberRequestDTO{
        private String memberEmailId;
        private String password;
        private String memberName;
        private String memberAddress;
        private String phoneNumber;
        private Role role;

        @Builder
        public MemberRequestDTO(String memberEmailId, String password, String memberName, String memberAddress, String phoneNumber, Role role) {
            this.memberEmailId = memberEmailId;
            this.password = password;
            this.memberName = memberName;
            this.memberAddress = memberAddress;
            this.phoneNumber = phoneNumber;
            this.role = role;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberResponseDTO{
        private Long memberId;
        private String memberEmailId;
        private String password;
        private String memberName;
        private String memberAddress;
        private String phoneNumber;

        public MemberResponseDTO(Member member) {
            this.memberId = member.getMemberId();
            this.memberEmailId = member.getMemberEmailId();
            this.password = member.getPassword();
            this.memberName = member.getMemberName();
            this.memberAddress = member.getMemberAddress();
            this.phoneNumber = member.getPhoneNumber();
        }
    }

}
