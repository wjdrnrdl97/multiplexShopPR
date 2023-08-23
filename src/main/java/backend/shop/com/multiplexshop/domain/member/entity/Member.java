package backend.shop.com.multiplexshop.domain.member.entity;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member",cascade = CascadeType.PERSIST)
    private List<Board> boards = new ArrayList<>();
}
