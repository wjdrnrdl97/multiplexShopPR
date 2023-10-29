package backend.shop.com.multiplexshop.domain;


import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.products.repository.UploadFileRepository;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import backend.shop.com.multiplexshop.domain.products.service.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ProductsService productsService;
    @Autowired
    protected UploadService uploadService;
    @Autowired
    protected ProductsRepository productsRepository;
    @Autowired
    protected UploadFileRepository uploadFileRepository;

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
