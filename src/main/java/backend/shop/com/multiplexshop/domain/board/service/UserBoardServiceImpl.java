package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.repository.UserBoardRepository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userBoard")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserBoardServiceImpl implements BoardService<UserBoard>{

    private final UserBoardRepository userBoardRepository;
    private final MemberRepository memberRepository;


    @Override
    public UserBoard findById(Long id) {
        return userBoardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board not Found" + id));
    }

    @Override
    public List<UserBoard> findByAll() {
        return userBoardRepository.findAll();
    }

    @Override
    public UserBoard save(BoardRequestDTO boardRequestDTO) {
        UserBoard userBoard = dtoToBoardEntity(boardRequestDTO);
        return userBoardRepository.save(userBoard);
    }


    @Override
    @Transactional
    public UserBoard update(Long boardId, BoardRequestDTO boardRequestDTO){
        UserBoard getUserBoard = userBoardRepository.findById(boardId).get();
        getUserBoard.updateBoard(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContent());
        return userBoardRepository.save(getUserBoard);
    }

    @Override
    @Transactional
    public void delete(Long boardId) {
        UserBoard userBoard = userBoardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalStateException("Board not Found" + boardId));
        userBoardRepository.deleteById(boardId);
    }

    @Override
    public UserBoard dtoToBoardEntity(BoardRequestDTO boardRequestDTO) {
        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
            .orElseThrow(()->new IllegalArgumentException("Member not found"));
        return UserBoard.builder()
                .boardTitle(boardRequestDTO.getBoardTitle())
                .boardContent(boardRequestDTO.getBoardContent())
                .member(member)
                .memberName(member.getMemberName())
                .build();
    }

    }



