package backend.shop.com.multiplexshop.domain.comment.service;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Comment searchById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("등록되지않는 번호입니다." + commentId));
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<CommentResponseDTO> findAllByBoard(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게시물 번호입니다."));
        List<Comment> commentsByBoard = commentRepository.findAllByBoard(board);

        return commentsByBoard.stream()
                .map(CommentResponseDTO::of)
                .toList();
    }

    public Comment save(CommentRequestDTO commentRequestDTO){
        Member member = getMemberByMemberId(commentRequestDTO);
        Board board = getBoardByBoardId(commentRequestDTO);

        Comment comment = Comment.dtoToCommentEntity(commentRequestDTO, member, board);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        Comment comment = searchById(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment update(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment updateComment = searchById(commentId);
        updateComment.update(commentRequestDTO.getCommentContent());

        return commentRepository.save(updateComment);
    }

    private Board getBoardByBoardId(CommentRequestDTO commentRequestDTO) {
        return boardRepository.findById(commentRequestDTO.getBoardId())
                .orElseThrow(() ->
                        new IllegalArgumentException("잘못된 게시물 번호입니다 :" + commentRequestDTO.getBoardId()));
    }

    private Member getMemberByMemberId(CommentRequestDTO commentRequestDTO) {
        return memberRepository.findById(commentRequestDTO.getMemberId())
                .orElseThrow(() ->
                        new IllegalArgumentException("잘못된 게시물 번호입니다 :" + commentRequestDTO.getMemberId()));
    }
}

