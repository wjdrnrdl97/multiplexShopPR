package backend.shop.com.multiplexshop.domain;


import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.repository.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
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
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartRepository cartRepository;
    @Autowired
    protected CartProductsRepository cartProductsRepository;

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected OrdersRepository ordersRepository;
    @Autowired
    protected OrderProductsRepository orderProductsRepository;
    @Autowired
    protected DeliveryRepository deliveryRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
