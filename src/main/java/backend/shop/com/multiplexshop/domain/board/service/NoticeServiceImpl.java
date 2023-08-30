package backend.shop.com.multiplexshop.domain.board.service;

import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs;
import backend.shop.com.multiplexshop.domain.board.entity.Notice;
import backend.shop.com.multiplexshop.domain.board.repository.NoticeRepository;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;

@Service
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
        Notice notice =
        return null;
    }

    @Override
    public Notice update(Long boardId, BoardRequestDTO boardRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long boardId) {

    }
    public
}
