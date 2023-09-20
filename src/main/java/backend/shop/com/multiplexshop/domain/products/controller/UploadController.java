package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        return "이미지.png";
    }

}
