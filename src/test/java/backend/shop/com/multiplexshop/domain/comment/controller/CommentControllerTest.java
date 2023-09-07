package backend.shop.com.multiplexshop.domain.comment.controller;

import backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs;
import backend.shop.com.multiplexshop.domain.comment.entity.Comment;
import backend.shop.com.multiplexshop.domain.comment.repository.CommentRepository;
import backend.shop.com.multiplexshop.domain.comment.service.CommentService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.net.http.HttpRequest;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.comment.dto.CommentDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentRepository commentRepository;
    @BeforeEach
    void setMockMvc(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8",true)).build();
    }

    @Test
    @DisplayName("댓글 삭제 버튼을 눌렀을때 댓글 삭제 요청이 보내져 삭제에 성공해야한다.")
    public void test2 ()throws Exception{
        //given
        final String commentContent = "comment Content~~~";
        final String url = "/api/comment/{id}";
        Member member = memberRepository.findById(1L).get();

        Comment comment = Comment.builder()
                .commentContent(commentContent)
                .memberName(member.getMemberName())
                .build();

        commentRepository.save(comment);

        //when
        ResultActions result = mockMvc.perform(delete(url, comment.getCommentId()))
                .andExpect(status().isOk());
        //then

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();

    }
    @Test
    @DisplayName("댓글 등록 버튼을 눌렀을 때 댓글이 등록에 성공해야한다.")
    public void postComment()throws Exception{
        //given
            final String url = "/api/comment";
            Member member = memberRepository.findById(1L).get();
            final String commentContent = "new Content";
            CommentRequestDTO dto = CommentRequestDTO.builder()
                .commentContent(commentContent)
                .memberName(member.getMemberName())
                .build();
            String requestBody = objectMapper.writeValueAsString(dto);
        //when
        ResultActions perform = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                                                        .content(requestBody));
        ResultActions resultActions = perform.andExpect(status().isCreated());
        //then
        Comment comment = commentRepository.findById(1L).get();
        assertThat(comment).isNotNull()
                .extracting("commentContent","memberName")
                .contains(commentContent,member.getMemberName());
    }
    @Test
    @DisplayName("게시물 조회 시 댓글목록이 조회되어야한다.")
    public void getCommentList() throws Exception{
        //given
        final String url = "/api/comment/{boardId}";
        Member member = memberRepository.findById(1L).get();
        String commentContent1 = "content1";
        String commentContent2 = "content2";
        CommentRequestDTO dto1 = CommentRequestDTO.builder()
                .commentContent(commentContent1)
                .memberName(member.getMemberName())
                .build();
        CommentRequestDTO dto2 = CommentRequestDTO.builder()
                .commentContent(commentContent2)
                .memberName(member.getMemberName())
                .build();
        commentService.save(dto1);
        commentService.save(dto2);
        //when
        ResultActions perform = mockMvc.perform(get(url, 1));
        perform.andDo(MockMvcResultHandlers.print())
                 .andExpect(status().isOk());

        //then
    }

    @Test
    @DisplayName("사용자에게서 댓글 수정 요청이 왔을 때 댓글 수정에 성공해야한다.")
    public void updateComment() throws Exception{
        //given
        final String url = "/api/comment/{id}";
        final String originalContent = "Content";
        final String newContent = "new Content~~~~";

        Member member = getMember(1L);
        Comment originalComment = Comment.builder()
                .commentContent(originalContent)
                .memberName(member.getMemberName())
                .build();

        commentRepository.save(originalComment);

        CommentRequestDTO requestDTO = CommentRequestDTO.builder()
                .commentContent(newContent)
                .memberName(member.getMemberName())
                .build();

        //when
        ResultActions result = mockMvc.perform(put(url, originalComment.getCommentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        );
        //then
        result.andExpect(status().isOk());
        Comment updatedComment = commentRepository.findById(1L).get();
        assertThat(updatedComment.getCommentContent()).isEqualTo(newContent);
    }

    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return member;
    }
}