package backend.shop.com.multiplexshop.domain.products.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    @GetMapping
    public String getAllProductsView(Model model){

        return "product/allProductView";
    }

    @GetMapping("/food")
    public String getFoodProductsView(Model model){

        return "product/foodProduct";
    }

    @GetMapping("/stuff")
    public String getStuffProductsView(Model model){

        return "product/stuffProduct";
    }

    @GetMapping("/post")
    public String getPostProductsView(Model model){

        return "product/createProduct";
    }

    @GetMapping("/{productsId}")
    public String getProductsDetailView(Model model){

        return "product/productDetailView";
    }


}
