package backend.shop.com.multiplexshop.domain.member.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDTOs {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class MemberRequestDTO{
        private String memberEmailId;
        private String password;
        private String memberName;
        private String memberAddress;
        private String phoneNumber;
        private Role role;

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
