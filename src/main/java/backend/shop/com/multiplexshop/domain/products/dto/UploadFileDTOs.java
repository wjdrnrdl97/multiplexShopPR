package backend.shop.com.multiplexshop.domain.products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadFileDTOs {

    private String itemName;
    private MultipartFile multipartFile;

    @Builder
    public UploadFileDTOs(String itemName, MultipartFile multipartFile) {
        this.itemName = itemName;
        this.multipartFile = multipartFile;
    }
}
