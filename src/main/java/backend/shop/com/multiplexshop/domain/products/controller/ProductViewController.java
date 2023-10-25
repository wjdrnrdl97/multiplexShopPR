package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.member.dto.MemberDTOs.MemberResponseDTO;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.*;

@Controller
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductsService productsService;
    private final UploadService uploadService;

    @GetMapping("/{productsId}")
    public String getProductsDetailView(@PathVariable Long productsId, Model model){

        ProductsResponseDTO productByRequest = productsService.findProductByRequest(productsId);
        model.addAttribute("product", productByRequest);
        return "product/productDetailView";
    }

    @GetMapping
    public String getAllProductsView(@RequestParam(defaultValue = "0")int page, HttpSession session, Model model){

        MemberResponseDTO loginUser = (MemberResponseDTO) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        Page<ProductsResponseDTO> allStuffProducts =
                productsService.findAllProductsByCategories(Categories.STUFF,page);

        Page<ProductsResponseDTO> allFoodProducts =
                productsService.findAllProductsByCategories(Categories.FOOD,page);

        model.addAttribute("stuff", allStuffProducts);
        model.addAttribute("food", allFoodProducts);
        return "product/allProductView";
    }

    @GetMapping("/food")
    public String getFoodProductsView(@RequestParam(defaultValue = "0")int page, Model model){
        Page<ProductsResponseDTO> allFoodProducts =
                productsService.findAllProductsByCategories(Categories.FOOD, page);

        model.addAttribute("food", allFoodProducts);
        return "product/foodProduct";
    }

    @GetMapping("/stuff")
    public String getStuffProductsView(@RequestParam(defaultValue = "0")int page,Model model){
        Page<ProductsResponseDTO> allStuffProducts =
                productsService.findAllProductsByCategories(Categories.STUFF,page);

        model.addAttribute("stuff", allStuffProducts);
        return "product/stuffProduct";
    }

    @GetMapping("/searchAll")
    public String getSearchAllProductsView
                                (@RequestParam(defaultValue = "0")int page,@RequestParam String keyword, Model model){
        Page<ProductsResponseDTO> productNameContaining = productsService.findAllByProductNameContaining(keyword, page);
        model.addAttribute("search", productNameContaining);
        model.addAttribute("keyword", keyword);
        return "product/searchAll";
    }
    @GetMapping("/search")
    public String getSearchCategoryProductsView(@RequestParam(defaultValue = "0")int page,
                                    @RequestParam Categories categories, @RequestParam String keyword, Model model){
        Page<ProductsResponseDTO> byCategoriesAndProductNameContaining =
                                productsService.findAllByCategoriesAndProductNameContaining(categories, keyword, page);
        model.addAttribute("search",byCategoriesAndProductNameContaining);
        model.addAttribute("keyword",keyword);
        model.addAttribute("categories",categories);
        return "product/searchCategory";
    }
    @GetMapping("/createProducts")
    public String getCreateProductsView(){

        return "product/createProduct";
    }

    @GetMapping("/modifyProducts/{productId}")
    public String getUpdateProductsView(Model model, @PathVariable Long productId){
        ProductsResponseDTO productByRequest = productsService.findProductByRequest(productId);
        List<UploadFileResponseDTO> allUploadFileByRequest = uploadService.findAllUploadFileByProductId(productId);

        model.addAttribute("product",productByRequest);
        model.addAttribute("img",allUploadFileByRequest);
        return "product/modifyProduct";
    }

    @GetMapping("uploadImage")
    public String getUploadImageModal(){
        return "product/uploadModal";
    }
}
