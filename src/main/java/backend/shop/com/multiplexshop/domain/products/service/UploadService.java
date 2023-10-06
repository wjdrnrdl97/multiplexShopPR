package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.exception.BadImageException;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.products.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadFileRepository uploadFileRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir+filename;
    }

    public void uploadImageByRequest(UploadFileDTOs request) throws IOException {
        UploadFile storedImageFile = storeImageFileByRequest(request)
                .orElseThrow(() -> new BadImageException("잘못된 이미지입니다."));
        uploadFileRepository.save(storedImageFile);
    }

    private Optional<UploadFile> storeImageFileByRequest(UploadFileDTOs request) throws IOException {
        MultipartFile multipartFile = request.getMultipartFile();

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String productName = request.getProductName();

        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return Optional.of(new UploadFile(originalFilename,storeFileName,productName));
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String extractedExtensionName = extractExt(originalFilename);
        return uuid +"."+ extractedExtensionName;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public UrlResource fileResource(String filename) throws IOException {
        return new UrlResource("file:"+getFullPath(filename));
    }

}
