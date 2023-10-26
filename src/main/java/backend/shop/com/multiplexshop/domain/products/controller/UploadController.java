package backend.shop.com.multiplexshop.domain.products.controller;


import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import static backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.*;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/api/image-upload")
    public ResponseEntity<UploadFileResponseDTO> postUploadFileByRequest(@RequestPart("request") MultipartFile request)
                                                                                                    throws IOException {
        UploadFileResponseDTO responseUploadFile = uploadService.createUploadFileByRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUploadFile);
    }
    @PutMapping("/api/image-upload")
    public ResponseEntity<UploadFileResponseDTO> putUploadFileByImageId(@RequestParam(value = "id") Long id,
                                            @RequestPart(value = "request") MultipartFile request)throws IOException {
        UploadFileResponseDTO responseUploadFile = uploadService.changeUploadFileNameByRequest(id, request);
        return ResponseEntity.ok(responseUploadFile);
    }
}
