package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.exception.DifferentMemberIdException;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Board not Found" + id));
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional
    public Board postBoard(BoardRequestDTO boardRequestDTO) {
        Board board = toBoard(boardRequestDTO);
        return boardRepository.save(board);
    }

    public Board toBoard(BoardRequestDTO boardRequestDTO) {
        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return Board.builder()

                .boardTitle(boardRequestDTO.getBoardTitle())
                .boardContent(boardRequestDTO.getBoardContent())
                .member(member)
                .memberName(member.getMemberName())
                .build();
    }

    @Override
    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDTO boardRequestDTO) {
        Board getBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not Found" + boardId));
        Board updateBoard = toBoard(boardRequestDTO);
        if (!getBoard.getMember().getMemberId().equals(updateBoard.getMember().getMemberId())) {
            throw new DifferentMemberIdException("Not Match MemberID");
        }

        getBoard.updateBoard(updateBoard.getBoardTitle(), updateBoard.getBoardContent());


        return boardRepository.save(getBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not Found" + boardId));
        boardRepository.deleteById(boardId);
    }
}


