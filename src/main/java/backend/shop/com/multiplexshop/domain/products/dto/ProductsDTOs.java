package backend.shop.com.multiplexshop.domain.products.dto;

import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductsDTOs {

    /**
     * 개발 초기 단계에서는 가격 수량 이름 카테고리만 받고 후에 나머지 데이터 빌더에 추가 예정.
     */
    @Getter
    @NoArgsConstructor
    public static class ProductsRequestDTO{
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
        public ProductsRequestDTO(String productName, Integer productPrice, Integer stockQuantity,
                                  String selectTag1, String selectTag2, Categories categories,
                                  String imagePath, String detailImagePath, String productScript) {
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



        public Products toEntity(ProductsRequestDTO requestDTO){
            return Products.builder()
                    .productName(requestDTO.getProductName())
                    .productPrice(requestDTO.getProductPrice())
                    .stockQuantity(requestDTO.getStockQuantity())
                    .categories(requestDTO.getCategories())
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class ProductsResponseDTO{
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
        public ProductsResponseDTO(String productName, Integer productPrice, Integer stockQuantity, Categories categories) {
            this.productName = productName;
            this.productPrice = productPrice;
            this.stockQuantity = stockQuantity;
            this.categories = categories;
        }

        public static ProductsResponseDTO of(Products entity){
            return ProductsResponseDTO.builder()
                    .productName(entity.getProductName())
                    .productPrice(entity.getProductPrice())
                    .stockQuantity(entity.getStockQuantity())
                    .categories(entity.getCategories())
                    .build();
        }

    }
}
