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
        private Integer orderQuantity;

        @Builder
        public ProductsRequestDTO(String productName, Integer productPrice, Integer stockQuantity,
                                  String selectTag1, String selectTag2, Categories categories,
                                  String imagePath, String detailImagePath, String productScript,
                                  Integer orderQuantity, Long id) {
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
        private Products products;

        @Builder
        public ProductsResponseDTO(Products products,String productName, Integer productPrice, Integer stockQuantity, Categories categories, String productScript) {
            this.products = products;
            this.productName = productName;
            this.productPrice = productPrice;
            this.stockQuantity = stockQuantity;
            this.categories = categories;
            this.productScript = productScript;
        }

        public static ProductsResponseDTO of(Products entity){
            return ProductsResponseDTO.builder()
                    .productName(entity.getProductName())
                    .productPrice(entity.getProductPrice())
                    .stockQuantity(entity.getStockQuantity())
                    .categories(entity.getCategories())
                    .products(entity)
                    .productScript(entity.getProductScript())
                    .build();
        }

    }
}
