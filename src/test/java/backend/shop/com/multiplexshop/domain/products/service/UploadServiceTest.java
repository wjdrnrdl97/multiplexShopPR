package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
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

        UploadFileRequestDTO request = UploadFileRequestDTO.builder().multipartFile(mockMultipartFile).build();
        //when
        uploadService.uploadImageByRequest(request);
        //then
        UploadFile result = uploadFileRepository.findById(1L).orElse(null);

        assertThat(result).isNotNull();
        assertThat(result.getStoreFileName().contains("png")).isEqualTo(true);
    }
}