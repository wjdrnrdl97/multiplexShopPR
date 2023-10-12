package backend.shop.com.multiplexshop.domain;

import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.Products.repository.UploadFileRepository;
import backend.shop.com.multiplexshop.domain.Products.service.ProductsService;
import backend.shop.com.multiplexshop.domain.Products.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {

    @Autowired
    protected ProductsService productService;
    @Autowired
    protected ProductsRepository productsRepository;
    @Autowired
    protected UploadFileRepository uploadFileRepository;
    @Autowired
    protected UploadService uploadService;




}
