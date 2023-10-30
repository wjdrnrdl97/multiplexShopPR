package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductsDTOs {
    @Getter
    @NoArgsConstructor
    public static class ProductsRequestDTO{
        private Long id;
        @NotBlank(message = "상품의 이름을 기입하세요")
        private String productName;

        @NotNull(message = "상품의 가격을 기입하세요")
        @Max(value = 1000000, message = "최대 가격은 1,000,000원 입니다.")
        @Min(value = 100, message = "최소 가격은 100원입니다.")
        private Integer productPrice;

        @NotNull(message = "상품의 수량을 기입하세요.")
        @Min(value = 1,message = "최소 수량은 1개입니다.")
        @Max(value = 1000,message = "최대 수량은 1000개입니다.")
        private Integer stockQuantity;

        private String selectTag1;
        private String selectTag2;
        private Categories categories;

        @NotBlank(message = "이미지를 등록하세요.")
        private String imagePath;

        @NotBlank(message = "이미지를 등록하세요.")
        private String detailImagePath;

        @NotBlank(message = "상품설명을 기입하세요.")
        private String productScript;
        private Integer orderQuantity;


        @Builder
        public ProductsRequestDTO(Long id, String productName, Integer productPrice, Integer stockQuantity,
                                  String selectTag1, String selectTag2, Categories categories, String imagePath,
                                  String detailImagePath, String productScript,Integer orderQuantity) {
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

        public Products toEntity(ProductsRequestDTO requestDTO){
            return Products.builder()
                    .productName(requestDTO.getProductName())
                    .productPrice(requestDTO.getProductPrice())
                    .stockQuantity(requestDTO.getStockQuantity())
                    .categories(requestDTO.getCategories())
                    .productScript(requestDTO.getProductScript())
                    .imagePath(requestDTO.imagePath)
                    .detailImagePath(requestDTO.detailImagePath)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class ProductsResponseDTO{
        private Long id;
        private String productName;
        private Integer productPrice;
        private Integer stockQuantity;
        private String selectTag1;
        private String selectTag2;
        private Categories categories;
        private String imagePath;
        private String detailImagePath;
        private String productScript;

        @Builder
        public ProductsResponseDTO(String productName, Integer productPrice, Integer stockQuantity,
                                   Categories categories, String productScript, Long id,
                                   String imagePath, String detailImagePath) {
            this.productName = productName;
            this.productPrice = productPrice;
            this.stockQuantity = stockQuantity;
            this.categories = categories;
            this.productScript = productScript;
            this.id = id;
            this.imagePath = imagePath;
            this.detailImagePath = detailImagePath;

        }

        public static ProductsResponseDTO of(Products entity){
            return ProductsResponseDTO.builder()
                    .id(entity.getId())
                    .productName(entity.getProductName())
                    .productPrice(entity.getProductPrice())
                    .stockQuantity(entity.getStockQuantity())
                    .categories(entity.getCategories())
                    .productScript(entity.getProductScript())
                    .imagePath(entity.getImagePath())
                    .detailImagePath(entity.getDetailImagePath())
                    .build();
        }

    }
}
