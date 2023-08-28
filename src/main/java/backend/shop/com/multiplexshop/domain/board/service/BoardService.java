package backend.shop.com.multiplexshop.domain.board.service;


import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 */
@Service
public interface BoardService {
    /**
     *  게시물 상세조회
     * @param id / BoardId
     * @return Board / exception
     */
    public UserBoard getBoard(Long id);

    /**
     *  게시물목록 조회
     * @return List<Board>
     */
    public List<UserBoard> getBoardList();

    /**
     *  게시물 등록
     * @param boardRequestDTO
     * @return Board
     */
    public UserBoard postBoard(BoardRequestDTO boardRequestDTO);

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    public UserBoard updateBoard(Long boardId, BoardRequestDTO boardRequestDTO);

    public void deleteBoard(Long boardId);

    /**
     * 게시물 DTO -> Entity 변환하기
     * @param boardRequestDTO
     * @return board
     */
    public UserBoard DtoToBoardEntity(BoardRequestDTO boardRequestDTO);
}
