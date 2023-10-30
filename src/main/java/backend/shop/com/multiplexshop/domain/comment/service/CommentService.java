package backend.shop.com.multiplexshop.domain.comment.service;

import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.repository.BoardRepository;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDTO findCommentById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("등록되지않는 번호입니다." + commentId));
        return CommentResponseDTO.of(findComment);
    }

    public List<CommentResponseDTO> findAllByBoard(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게시물 번호입니다."));
        List<Comment> commentsByBoard = commentRepository.findAllByBoard(board);

        return commentsByBoard.stream()
                .map(CommentResponseDTO::of)
                .toList();
    }

    public CommentResponseDTO createCommentByRequest(CommentRequestDTO commentRequestDTO){
        Member member = getMemberByMemberId(commentRequestDTO);
        Board board = getBoardByBoardId(commentRequestDTO);

        Comment comment = Comment.dtoToCommentEntity(commentRequestDTO, member, board);
        Comment createComment = commentRepository.save(comment);
        return CommentResponseDTO.of(createComment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("등록되지않는 번호입니다." + commentId));
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponseDTO updateCommentByRequest(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("등록되지않는 번호입니다." + commentId));
        comment.update(commentRequestDTO.getCommentContent());
        Comment updateComment = commentRepository.save(comment);
        return CommentResponseDTO.of(updateComment);
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

