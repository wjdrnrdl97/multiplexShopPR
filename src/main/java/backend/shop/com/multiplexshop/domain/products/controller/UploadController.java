package backend.shop.com.multiplexshop.domain.products.controller;


import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import static backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.*;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/api/image-upload")
    public ResponseEntity<UploadFileResponseDTO> postUploadFileByRequest
                                            (@RequestParam("request") MultipartFile request) throws IOException {
        UploadFileResponseDTO responseUploadFile = uploadService.createUploadFileByRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUploadFile);
    }
}
