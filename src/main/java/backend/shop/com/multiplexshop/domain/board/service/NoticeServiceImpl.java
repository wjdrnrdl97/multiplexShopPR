package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.Notice;
import backend.shop.com.multiplexshop.domain.board.entity.UserBoard;
import backend.shop.com.multiplexshop.domain.board.repository.NoticeRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Service("notice")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements BoardService<Notice>{

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    @Override
    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board Not Found"));
    }

    @Override
    public List<Notice> findByAll() {
        return noticeRepository.findAll();
    }

    @Override
    public Notice save(BoardRequestDTO BoardRequestDTO) {
        Notice notice = dtoToBoardEntity(BoardRequestDTO);
        return noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public Notice update(Long boardId, BoardRequestDTO updateRequest) {
        Notice updateNotice = noticeRepository.findById(boardId)
                                        .orElseThrow(()-> new IllegalArgumentException("Board Not Found"));
        updateNotice.update(updateRequest.getBoardTitle(),updateRequest.getBoardContent());
        return noticeRepository.save(updateNotice);
    }

    @Override
    public void delete(Long boardId) {
        Notice updateNotice = noticeRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("Board Not Found"));
        noticeRepository.deleteById(boardId);
    }

    @Override
    public Notice dtoToBoardEntity(BoardRequestDTO boardRequestDTO) {
        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("Member not found"));
        return Notice.builder()
                .boardTitle(boardRequestDTO.getBoardTitle())
                .boardContent(boardRequestDTO.getBoardContent())
                .member(member)
                .memberName(member.getMemberName())
                .build();
    }
}
