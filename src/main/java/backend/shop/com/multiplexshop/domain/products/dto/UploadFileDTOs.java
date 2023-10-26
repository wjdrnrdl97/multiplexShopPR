package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class UploadFileDTOs {

    @Getter
    @NoArgsConstructor
    public static class UploadFileResponseDTO {

        private Long id;
        private String originFileName;
        private String storeFileName;

        @Builder
        public UploadFileResponseDTO(Long id, Products products, String originFileName, String storeFileName) {
            this.id = id;
            this.originFileName = originFileName;
            this.storeFileName = storeFileName;
        }

        public static UploadFileResponseDTO of(UploadFile uploadFile) {
            return UploadFileResponseDTO.builder()
                    .id(uploadFile.getId())
                    .originFileName(uploadFile.getOriginalFileName())
                    .storeFileName(uploadFile.getStoreFileName())
                    .build();
        }
    }
    private String productName;
    private String storeFileName;
    private MultipartFile multipartFile;


    public UploadFileDTOs(UploadFile uploadFile) {
        this.storeFileName = uploadFile.getStoreFileName();
    }
}
