package backend.shop.com.multiplexshop.domain.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Bag;

@Getter
@NoArgsConstructor
public class GlobalCommonResponseDTO<T> {

    private int code;
    private String message;
    private T data;

    @Builder
    public GlobalCommonResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
