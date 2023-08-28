package backend.shop.com.multiplexshop.domain.board.exception;

public class DifferentMemberIdException extends RuntimeException{

    DifferentMemberIdException(){

    }
    public DifferentMemberIdException(String message){
        super(message);
    }
}
