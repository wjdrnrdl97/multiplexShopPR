package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileDTOs {

    @Getter
    @NoArgsConstructor
    public static class UploadFileResponseDTO {

        private Long id;
        private Products products;
        private String originFileName;
        private String storeFileName;

        @Builder
        public UploadFileResponseDTO(Long id, Products products, String originFileName, String storeFileName) {
            this.id = id;
            this.products = products;
            this.originFileName = originFileName;
            this.storeFileName = storeFileName;
        }

        public UploadFileResponseDTO of(UploadFile uploadFile) {
            return UploadFileResponseDTO.builder()
                    .id(uploadFile.getId())
                    .products(uploadFile.getProducts())
                    .originFileName(uploadFile.getOriginalFileName())
                    .storeFileName(uploadFile.getStoreFileName())
                    .build();
        }
    }
    private String productName;
    private String storeFileName;
    private MultipartFile multipartFile;

    @Builder
    public UploadFileDTOs(String productName, MultipartFile multipartFile) {
        this.productName = productName;
        this.multipartFile = multipartFile;
    }

    public UploadFileDTOs(UploadFile uploadFile) {
        this.storeFileName = uploadFile.getStoreFileName();
    }
}
