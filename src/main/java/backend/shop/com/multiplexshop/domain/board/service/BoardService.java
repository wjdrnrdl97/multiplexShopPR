package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     *  게시물 상세 조회
     * @param boardId (조회할 게시물 번호)
     * @return Board(조회 상세정보) + 조회수 증가
     */
    public Board searchById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("Board not Found" + boardId));
    }


    /**
     * Cookie를 사용한 조회수 증가
     * @param boardId (게시물 번호)
     * @param request
     * @param response
     * @return 조회수 증가
     */
    @Transactional
    public Board viewCountValidation(Long boardId, HttpServletRequest request, HttpServletResponse response){
        Cookie myCookie = findCookie(request.getCookies(), "boardView");
        if (myCookie!=null && !myCookie.getValue().contains("[" + boardId.toString() + "]")){
                increaseViewCount(boardId,response,myCookie);
        }
        if(myCookie == null){
            newCookieAndIncreaseViewCount(boardId,response);
        }
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Board Not Found"));

    }

    /**
     *  특정 cookie 찾기
     * @param cookies
     * @param cookieName
     * @return
     */
    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        if(cookies !=null){
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     *  특정 게시물 조회수 증가 및 Cookie Value 변경
     * @param boardId
     * @param response
     * @param cookie
     */
    private void increaseViewCount(Long boardId, HttpServletResponse response, Cookie cookie){
        boardRepository.updateCount(boardId);
        cookie.setValue(cookie.getValue() + "_[" + boardId + "]");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
    }

    /**
     *  쿠키 생성 및 Cookie Setting
     * @param boardId
     * @param response
     */
    private void newCookieAndIncreaseViewCount(Long boardId, HttpServletResponse response){
        Cookie cookie = new Cookie("boardView","["+ boardId + "]");
        boardRepository.updateCount(boardId);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
    }

    /**
     *  공지사항 게시물 목록 조회
     * @return List<BoardResponseDTO>
     */
    public List<BoardResponseDTO> findByNotice() {
        List<Board> notice = boardRepository.findByNotie();
        return notice.stream()
                .map(BoardResponseDTO::new)
                .toList();
    }

    /**
     * 일반 게시물 목록 조회
     * @param page
     * @return Page<BoardResponseDTO>
     */
    public Page<BoardResponseDTO> findByPost(int page) {
        int pageNum = (page == 0)? 0 : page - 1;
        PageRequest pageAble = PageRequest.of(pageNum, 10, Sort.by("boardId").descending());
        Page<Board> postPage = boardRepository.findByPost(pageAble);

        return postPage.map(BoardResponseDTO::new);
    }

    /**
     *  게시물 등록
     * @param boardRequestDTO (등록할 게시물 정보)
     * @return Board
     */
    public Board save(BoardRequestDTO boardRequestDTO) {
        Board board = dtoToBoardEntity(boardRequestDTO);
        return boardRepository.save(board);
    }

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    @Transactional
    public Board update(Long boardId, BoardRequestDTO boardRequestDTO){
        Board getBoard = boardRepository.findById(boardId).get();
        getBoard.updateBoard(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContent());
        return boardRepository.save(getBoard);
    }

    /**
     * 게시물 삭제
     * @param boardId (삭제할 게시물 번호)
     */

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalStateException("Board not Found" + boardId));
        boardRepository.deleteById(boardId);
    }

    /**
     * 요청한 dto 정보 Entity 변환
     * @param boardRequestDTO (요청정보)
     * @return Board(Entity)
     */
    public Board dtoToBoardEntity(BoardRequestDTO boardRequestDTO) {
        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
            .orElseThrow(()->new IllegalArgumentException("Member not found"));
        return Board.builder()
                .boardTitle(boardRequestDTO.getBoardTitle())
                .boardContent(boardRequestDTO.getBoardContent())
                .member(member)
                .memberName(member.getMemberName())
                .build();
    }

    }



