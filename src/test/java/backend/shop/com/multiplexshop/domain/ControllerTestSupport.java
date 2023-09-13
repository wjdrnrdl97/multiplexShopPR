package backend.shop.com.multiplexshop.domain;

import backend.shop.com.multiplexshop.domain.products.controller.ProductsAPIController;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductsAPIController.class)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected ProductsService productsService;

}
