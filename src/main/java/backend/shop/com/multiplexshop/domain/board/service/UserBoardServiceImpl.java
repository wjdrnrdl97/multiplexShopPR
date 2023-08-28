package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.UserBoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.exception.DifferentMemberIdException;
import backend.shop.com.multiplexshop.domain.board.repository.UserBoardRepository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserBoardServiceImpl implements BoardService{
    private final UserBoardRepository userBoardRepository;
    private final MemberRepository memberRepository;


    @Override
    public UserBoard getBoard(Long id) {
        return userBoardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board not Found" + id));
    }

    @Override
    public List<UserBoard> getBoardList() {
        return userBoardRepository.findAll();
    }

    @Override
    @Transactional
    public UserBoard postBoard(BoardRequestDTO boardRequestDTO) {
        UserBoard userBoard = DtoToBoardEntity(boardRequestDTO);
        return userBoardRepository.save(userBoard);
    }


    @Override
    @Transactional
    public UserBoard updateBoard(Long boardId, BoardRequestDTO boardRequestDTO){
        UserBoard getUserBoard = userBoardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("Board not Found" + boardId));
        UserBoard updateUserBoard = DtoToBoardEntity(boardRequestDTO);
        // MemberId가 일치하지 않을 때
        if(!getUserBoard.getMember().getMemberId().equals(updateUserBoard.getMember().getMemberId())){
            throw new DifferentMemberIdException("Not Match MemberID");
        }
            getUserBoard.updateBoard(updateUserBoard.getBoardTitle(), updateUserBoard.getBoardContent());
        return userBoardRepository.save(getUserBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        UserBoard userBoard = userBoardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalStateException("Board not Found" + boardId));
        userBoardRepository.deleteById(boardId);
    }

    @Override
    public UserBoard DtoToBoardEntity(BoardRequestDTO boardRequestDTO) {
            Member member = memberRepository.findById(boardRequestDTO.getMemberId())
                    .orElseThrow(()->new IllegalArgumentException("Member not found"));
            return UserBoard.builder()
                    .boardId(boardRequestDTO.getBoardId())
                    .boardTitle(boardRequestDTO.getBoardTitle())
                    .boardContent(boardRequestDTO.getBoardContent())
                    .member(member)
                    .memberName(member.getMemberName())
                    .build();
        }
    }



