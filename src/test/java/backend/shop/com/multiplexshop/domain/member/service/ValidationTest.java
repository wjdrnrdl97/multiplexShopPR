package backend.shop.com.multiplexshop.domain.member.service;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.*;

@SpringBootTest
@ActiveProfiles("test")
public class ValidationTest{
    @Autowired protected Validator validator;

    @Test
    @DisplayName("필수항목 유효성 검사 테스트")
    public void validateUserIsRequired(){
        //given
        MemberRequestDTO dto = MemberRequestDTO.builder().build();
        //when
        Set<ConstraintViolation<MemberRequestDTO>> validate = validator.validate(dto);
        //then
        Iterator<ConstraintViolation<MemberRequestDTO>> iterator = validate.iterator();
        ArrayList<String> messageList = new ArrayList<>();
        while (iterator.hasNext()){
            String message = iterator.next().getMessage();
            messageList.add(message);
        }
        Assertions.assertThat(messageList).contains("이메일은 필수 입력 값입니다.", "비밀번호는 필수 입력 값입니다." ,
                                     "이름은 필수 입력 값입니다.", "주소는 필수 입력 값입니다.", "전화번호는 필수 입력 값입니다.");
    }
    @Test
    @DisplayName("조건에 맞지않는 비밀번호를 입력 시 유효성 검사")
    public void validateUserIsRequiredByPassword(){
        //given
        MemberRequestDTO dto = MemberRequestDTO.builder()
                .memberEmailId("test@naver.com")
                .password("wkrwjs12#")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .memberAddress("서울 강남구 언주로")
                .build();
        //when
        Set<ConstraintViolation<MemberRequestDTO>> validate = validator.validate(dto);
        //then
        Iterator<ConstraintViolation<MemberRequestDTO>> iterator = validate.iterator();
        ArrayList<String> messageList = new ArrayList<>();
        while (iterator.hasNext()){
            String message = iterator.next().getMessage();
            messageList.add(message);
        }
        Assertions.assertThat(messageList).contains("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
    }
    @Test
    @DisplayName("조건에 맞지않는 이메일를 입력 시 유효성 검사")
    public void validateUserIsRequiredByEmail(){
        //given
        MemberRequestDTO dto = MemberRequestDTO.builder()
                .memberEmailId("테스트")
                .password("test1234!@#$")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .memberAddress("서울 강남구 언주로")
                .build();
        //when
        Set<ConstraintViolation<MemberRequestDTO>> validate = validator.validate(dto);
        //then
        Iterator<ConstraintViolation<MemberRequestDTO>> iterator = validate.iterator();
        ArrayList<String> messageList = new ArrayList<>();
        while (iterator.hasNext()){
            String message = iterator.next().getMessage();
            messageList.add(message);
        }
        Assertions.assertThat(messageList).contains("이메일 형식이 올바르지 않습니다.");
    }
}
