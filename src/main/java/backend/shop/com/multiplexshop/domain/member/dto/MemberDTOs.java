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
        private Role role;

        @Builder
        public MemberResponseDTO(Long memberId, String memberEmailId, String password, String memberName, String memberAddress, String phoneNumber, Role role) {
            this.memberId = memberId;
            this.memberEmailId = memberEmailId;
            this.password = password;
            this.memberName = memberName;
            this.memberAddress = memberAddress;
            this.phoneNumber = phoneNumber;
            this.role = role;
        }
        public static MemberResponseDTO of(Member member){
            return MemberResponseDTO.builder()
                    .memberId(member.getMemberId())
                    .memberEmailId(member.getMemberEmailId())
                    .password(member.getPassword())
                    .memberName(member.getMemberName())
                    .memberAddress(member.getMemberAddress())
                    .phoneNumber(member.getPhoneNumber())
                    .role(member.getRole())
                    .build();
        }
        public static MemberResponseDTO withoutPassword(Member member){
            return MemberResponseDTO.builder()
                    .memberId(member.getMemberId())
                    .memberEmailId(member.getMemberEmailId())
                    .memberName(member.getMemberName())
                    .memberAddress(member.getMemberAddress())
                    .phoneNumber(member.getPhoneNumber())
                    .role(member.getRole())
                    .build();
        }
    }

}
