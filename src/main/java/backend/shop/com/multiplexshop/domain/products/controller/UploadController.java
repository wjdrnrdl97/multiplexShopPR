package backend.shop.com.multiplexshop.domain.Products.controller;

import backend.shop.com.multiplexshop.domain.Products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.Products.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/image-upload")
    public String imageUploadByUser(UploadFileDTOs uploadFileDTOs) throws IOException {
        uploadService.uploadImageByRequest(uploadFileDTOs);

        return "이미지 업로드 완료.";
    }

}
