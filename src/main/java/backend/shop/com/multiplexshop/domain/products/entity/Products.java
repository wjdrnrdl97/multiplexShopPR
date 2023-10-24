package backend.shop.com.multiplexshop.domain.products.entity;

import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer productPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(length = 20)
    private String selectTag1;

    @Column(length = 20)
    private String selectTag2;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Categories categories;

    @Column(length = 100)
    private String imagePath;

    @Column(length = 100)
    private String detailImagePath;

    @Column(length = 200)
    private String productScript;


    @Builder
    public Products(String productName, Integer productPrice, Integer stockQuantity, String selectTag1,
                    String selectTag2, Categories categories, String imagePath, String detailImagePath,
                    String productScript, Long id) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
        this.selectTag1 = selectTag1;
        this.selectTag2 = selectTag2;
        this.categories = categories;
        this.imagePath = imagePath;
        this.detailImagePath = detailImagePath;
        this.productScript = productScript;
    }

    public void addImagePath(UploadFile uploadFile){
        this.imagePath = uploadFile.getStoreFileName();
    }

    public void updateByRequest(ProductsRequestDTO requestDTO){
            this.categories = requestDTO.getCategories();
            this.productName = requestDTO.getProductName();
            this.productPrice = requestDTO.getProductPrice();
            this.productScript = requestDTO.getProductScript();
     }

    public void decreaseStockQuantity(Integer count){
        Integer checkStock = this.stockQuantity = stockQuantity - count;
        if (checkStock < 0){
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.stockQuantity = checkStock;
    }
    public void increaseStockQuantity(Integer count){
        this.stockQuantity += count;
    }

}
