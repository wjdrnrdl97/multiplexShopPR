package backend.shop.com.multiplexshop.domain;

import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.delivery.service.DeliveryService;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.member.service.MemberService;
import backend.shop.com.multiplexshop.domain.orders.repository.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.products.repository.UploadFileRepository;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import backend.shop.com.multiplexshop.domain.products.service.UploadService;
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
    @Autowired
    protected CartRepository cartRepository;
    @Autowired
    protected CartProductsRepository cartProductsRepository;

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected OrdersRepository ordersRepository;
    @Autowired
    protected OrderProductsRepository orderProductsRepository;
    @Autowired
    protected DeliveryRepository deliveryRepository;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected BoardRepository boardRepository;
    @Autowired
    protected BoardService boardService;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected DeliveryService deliveryService;





}
