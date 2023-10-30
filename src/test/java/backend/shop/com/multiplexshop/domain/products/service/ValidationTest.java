package backend.shop.com.multiplexshop.domain.products.service;

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

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@SpringBootTest
@ActiveProfiles("test")
public class ValidationTest {
    @Autowired
    protected Validator validator;

    @Test
    @DisplayName("상품등록 시 필수 항목 유효성 검사")
    public void validateHandling(){
        //given
        ProductsRequestDTO dto = ProductsRequestDTO.builder().build();
        //when
        Set<ConstraintViolation<ProductsRequestDTO>> validate = validator.validate(dto);
        //then
        Iterator<ConstraintViolation<ProductsRequestDTO>> iterator = validate.iterator();
        ArrayList<String> messageList = new ArrayList<>();
        while (iterator.hasNext()){
            String message = iterator.next().getMessage();
            messageList.add(message);
        }
        Assertions.assertThat(messageList).contains("상품의 이름을 기입하세요", "상품의 가격을 기입하세요","상품설명을 기입하세요."
                                                    , "상품의 수량을 기입하세요.", "이미지를 등록하세요.", "이미지를 등록하세요.");
    }
}
