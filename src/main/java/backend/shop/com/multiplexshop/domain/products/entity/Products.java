package backend.shop.com.multiplexshop.domain.products.entity;


/*
 * 셀렉트 태그 최종 컬럼명은 추후에 반영 예정.
 * notNull : 가격 수량 이름 카테고리
 */



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Products(String productName, Integer productPrice, Integer stockQuantity, String selectTag1,
                    String selectTag2, Categories categories, String imagePath, String detailImagePath,
                    String productScript) {

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
}
