package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
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

@SpringBootTest
@ActiveProfiles("test")
public class ValidationTest {
    @Autowired
    protected Validator validator;

    @Test
    @DisplayName("게시물 등록 시 필수 항목 유효성 검사")
    public void validateHandling(){
        //given
        BoardRequestDTO dto = BoardRequestDTO.builder().build();
        //when
        Set<ConstraintViolation<BoardRequestDTO>> validate = validator.validate(dto);
        //then
        Iterator<ConstraintViolation<BoardRequestDTO>> iterator = validate.iterator();
        ArrayList<String> messageList = new ArrayList<>();
        while (iterator.hasNext()){
            String message = iterator.next().getMessage();
            messageList.add(message);
        }
        Assertions.assertThat(messageList).contains("제목을 입력해주세요.", "내용을 입력해주세요.");
    }
}
