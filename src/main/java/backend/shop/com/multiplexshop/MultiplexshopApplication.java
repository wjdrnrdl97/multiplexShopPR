package backend.shop.com.multiplexshop;


import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MultiplexshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultiplexshopApplication.class, args);
		//git feat_product branch init

	}

}
