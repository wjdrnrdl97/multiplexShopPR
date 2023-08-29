package backend.shop.com.multiplexshop.domain.board.service;


import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.UserBoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
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
    public UserBoard findById(Long id);

    /**
     *  게시물목록 조회
     * @return List<Board>
     */
    public List<UserBoard> findByAll();

    /**
     *  게시물 등록
     * @param userBoardRequestDTO
     * @return Board
     */
    public UserBoard save(UserBoardRequestDTO userBoardRequestDTO);

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param userBoardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    public UserBoard update(Long boardId, UserBoardRequestDTO userBoardRequestDTO);

    public void delete(Long boardId);

    /**
     * 게시물 DTO -> Entity 변환하기
     * @param userBoardRequestDTO
     * @return board
     */
    public UserBoard DtoToBoardEntity(UserBoardRequestDTO userBoardRequestDTO);
}
