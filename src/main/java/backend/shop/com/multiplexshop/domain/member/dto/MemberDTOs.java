package backend.shop.com.multiplexshop.domain.member.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    @AllArgsConstructor
    @Builder
    public static class MemberResponseDTO{
        private String memberEmailId;
        private String password;
        private String memberName;
        private String memberAddress;
        private String phoneNumber;
    }
}
