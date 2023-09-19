package backend.shop.com.multiplexshop.domain.products.repository;

import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UploadFileRepositoryTest {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Test
    @DisplayName("업로드파일 DB에서 상품 이름이 같은 업로드파일들을 전부 조회한다.")
    public void findAllByProductName(){
        //given
        String productName = "향수";

        UploadFile uploadFile = UploadFile.builder()
                .productName(productName)
                .build();
        UploadFile uploadFile2 = UploadFile.builder()
                .productName(productName)
                .build();
        uploadFileRepository.saveAll(List.of(uploadFile, uploadFile2));
        //when
        List<UploadFile> uploadFilesByProductName = uploadFileRepository.findAllByProductName(productName);
        //then
        assertThat(uploadFilesByProductName.get(0).getProductName()).isEqualTo(productName);
        assertThat(uploadFilesByProductName).hasSize(2);
    }
}