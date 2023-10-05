package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@Controller
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductsService productsService;
    private final UploadService uploadService;

    @GetMapping
    public String getAllProductsView(){

        return "product/allProductView";
    }

    @GetMapping("/food")
    public String getFoodProductsView(){

        return "product/foodProduct";
    }

    @GetMapping("/stuff")
    public String getStuffProductsView(){

        return "product/stuffProduct";
    }

    @GetMapping("/createProducts")
    public String getCreateProductsView(){

        return "product/createProduct";
    }

    @GetMapping("/{productsId}")
    public String getProductsDetailView(@PathVariable Long productsId, Model model){
        Products responseProduct = productsService.findProductById(productsId);
        List<UploadFile> uploadFile = productsService.findUploadFile(productsId);

        model.addAttribute("img",new UploadFileDTOs(uploadFile.get(0)));
        model.addAttribute("product", ProductsResponseDTO.of(responseProduct));

        return "product/productDetailView";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws IOException {
        return uploadService.fileResource(filename);
    }



    @GetMapping("uploadImage")
    public String getUploadImageModal(){

        return "product/uploadModal";
    }
}
