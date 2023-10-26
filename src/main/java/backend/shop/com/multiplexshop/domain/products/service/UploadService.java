package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.exception.BadImageException;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.products.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.*;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadFileRepository uploadFileRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir+filename;
    }

    public UploadFileResponseDTO createUploadFileByRequest(MultipartFile request) throws IOException {
        UploadFile createUploadFile = storeImageFileByRequest(request)
                .orElseThrow(() -> new BadImageException("잘못된 이미지입니다."));
        UploadFile storedUploadFile = uploadFileRepository.save(createUploadFile);
        return new UploadFileResponseDTO().of(storedUploadFile);
    }
    private Optional<UploadFile> storeImageFileByRequest(MultipartFile request) throws IOException {
        String originalFilename = request.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        request.transferTo(new File(getFullPath(storeFileName)));
        return Optional.of(new UploadFile(originalFilename,storeFileName));
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
    @Transactional
    public UploadFileResponseDTO changeUploadFileNameByRequest(Long id, MultipartFile request) throws IOException{
        UploadFile findUploadFile = uploadFileRepository.findById(id)
                .orElseThrow(() -> new BadImageException("잘못된 이미지입니다."));

        String originalFilename = request.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        request.transferTo(new File(getFullPath(storeFileName)));

        findUploadFile.changeFilenames(originalFilename,storeFileName);
        UploadFile changeFileName = uploadFileRepository.save(findUploadFile);
        return new UploadFileResponseDTO().of(changeFileName);
    }
    public List<UploadFileResponseDTO> findAllUploadFileByProductId(Long id){
        List<UploadFile> findAllByProducts = uploadFileRepository.findByProductsId(id);
        return findAllByProducts.stream().map(UploadFileResponseDTO::of).toList();
    }
}
