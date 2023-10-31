package backend.shop.com.multiplexshop.domain.member.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


public class MemberDTOs {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberRequestDTO {

        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String memberEmailId;

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,6}$", message = "이름은 한글 2~6자리 이여야 합니다.")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String memberName;

        @NotBlank(message = "주소는 필수 입력 값입니다.")
        private String memberAddress;

        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
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
        public static class MemberResponseDTO {
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

            public static MemberResponseDTO of(Member member) {
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

            public static MemberResponseDTO withoutPassword(Member member) {
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

