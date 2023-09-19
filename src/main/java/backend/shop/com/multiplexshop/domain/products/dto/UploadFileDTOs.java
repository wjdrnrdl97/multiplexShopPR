package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadFileDTOs {

    private String productName;
    private MultipartFile multipartFile;

    @Builder
    public UploadFileDTOs(String productName, MultipartFile multipartFile) {
        this.productName = productName;
        this.multipartFile = multipartFile;
    }

}
