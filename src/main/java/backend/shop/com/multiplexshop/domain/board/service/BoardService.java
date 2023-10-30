package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;

import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
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
import org.springframework.validation.BindingResult;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public BoardResponseDTO findBoardByboardId(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not Found" + boardId));
        return BoardResponseDTO.of(findBoard);
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

    public List<BoardResponseDTO> findAllByNotice() {
        List<Board> notice = boardRepository.findByNotice();
        return notice.stream()
                .map(BoardResponseDTO::of)
                .toList();
    }

    public Page<BoardResponseDTO> findAllByPost(int page) {
        int pageNum = (page == 0)? 0 : page - 1;
        PageRequest pageAble = PageRequest.of(pageNum, 10, Sort.by("boardId").descending());
        Page<Board> postPage = boardRepository.findByPost(pageAble);

        return postPage.map(BoardResponseDTO::of);
    }

    public BoardResponseDTO createBoardByRequest(BoardRequestDTO request) {
        Board dtoToBoardEntity = dtoToBoardEntity(request);
        Board saveBoard = boardRepository.save(dtoToBoardEntity);
        return BoardResponseDTO.of(saveBoard);
    }

    public HashMap<String,String> validateHandling(BindingResult bindingResult){
        HashMap<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }

    /**
     *  게시물 수정
     * @param boardId (수정할 게시물 번호)
     * @param boardRequestDTO (수정한 게시물 정보)
     * @return Board(수정된 게시물 정보)
     */
    @Transactional
    public BoardResponseDTO updateBoardInfoByRequest(Long boardId, BoardRequestDTO boardRequestDTO){
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not Found" + boardId));
        findBoard.updateBoard(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContent());
        Board updateBoard = boardRepository.save(findBoard);
        return BoardResponseDTO.of(updateBoard);
    }

    /**
     * 게시물 삭제
     * @param boardId (삭제할 게시물 번호)
     */

    @Transactional
    public void deleteByRequest(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalStateException("Board not Found" + boardId));
        commentRepository.deleteAllByBoard(board);
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
                .boardType(boardRequestDTO.getBoardType())
                .build();

    }

}






