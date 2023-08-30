package backend.shop.com.multiplexshop.domain.board.entity;

public enum BoardType {
    POST("POST"),
    NOTICE("NOTICE");

    private final String label;

    BoardType(String label){
        this.label=label;
    }
    public String label(){
        return label;
    }
}
