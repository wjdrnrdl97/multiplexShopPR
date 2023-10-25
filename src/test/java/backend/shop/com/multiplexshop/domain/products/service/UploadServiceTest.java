package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.*;
import static org.assertj.core.api.Assertions.*;


class UploadServiceTest extends IntegrationTestSupport {

    @Test
    @DisplayName("유저의 이미지 등록 요청에서 업로드할 이미지 이름을 uuid 변환 후에 저장폴더의 경로와 함께 DB 저장에 성공한다.")
    public void uploadImageByRequest() throws IOException {
        //given
        byte[] testByte = null;
        MultipartFile mockMultipartFile = new MockMultipartFile("테스트","test.png",null, testByte);
        //when
        uploadService.uploadImageByRequest(mockMultipartFile);
        //then
        UploadFile result = uploadFileRepository.findById(1L).orElse(null);

        assertThat(result).isNotNull();
        assertThat(result.getStoreFileName().contains("png")).isEqualTo(true);
    }
    @Test
    @DisplayName("파일번호를 통해 업로드파일을 조회하고 해당 파일의 이름을 변경하고 저장하는데 성공한다.")
    @Transactional
    public void changeUploadFileNameByRequest() throws IOException {
        //given
        UploadFile uploadFile = UploadFile.builder()
                .originalFileName("원본이름")
                .storeFileName("저장이름")
                .build();
        UploadFile savedFile = uploadFileRepository.save(uploadFile);
        byte[] testByte = null;
        MultipartFile mockMultipartFile = new MockMultipartFile("테스트","test.png",null, testByte);
        //when
        UploadFileResponseDTO result = uploadService.changeUploadFileNameByRequest(1L, mockMultipartFile);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStoreFileName().contains("png")).isEqualTo(true);
    }
}