package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileDTOs {

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
