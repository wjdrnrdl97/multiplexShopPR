package backend.shop.com.multiplexshop.domain.products.entity;


/*
 * 셀렉트 태그 최종 컬럼명은 추후에 반영 예정.
 * notNull : 가격 수량 이름 카테고리
 */



import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import jakarta.persistence.*;
import lombok.*;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Products{

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

    private Integer orderQuantity;

    @Builder
    public Products(String productName, Integer productPrice, Integer stockQuantity, String selectTag1,
                    String selectTag2, Categories categories, String imagePath, String detailImagePath,
                    String productScript, Long id, Integer orderQuantity) {
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
        this.orderQuantity = orderQuantity;
    }

    public void addImagePath(UploadFile uploadFile){
        this.imagePath = uploadFile.getStoreFileName();
    }

    public void updateByRequest(ProductsRequestDTO requestDTO){
            this.productName = requestDTO.getProductName();
            this.productScript = requestDTO.getProductScript();
            this.stockQuantity = requestDTO.getStockQuantity();
            this.productPrice = requestDTO.getProductPrice();
     }
}
