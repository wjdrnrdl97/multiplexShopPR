package backend.shop.com.multiplexshop.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

    private String memberEmailId;
    private String password;

}
