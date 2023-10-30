package backend.shop.com.multiplexshop.domain.board.controller;


import backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.BoardRequestDTO;
import backend.shop.com.multiplexshop.domain.board.entity.Board;
import backend.shop.com.multiplexshop.domain.board.service.BoardService;
import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseCode;
import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static backend.shop.com.multiplexshop.domain.board.dto.BoardDTOs.*;


@RestController
@RequiredArgsConstructor
public class BoardAPIController {

    private final BoardService boardService;

    /**
     * 게시물 등록
     * @param boardRequestDTO (등록할 게시물 정보)
     * @return Http 201 created, body(postBoard(new Board))
     */
    @PostMapping("/api/support")
    public ResponseEntity<GlobalCommonResponseDTO> postUserBoard(@RequestBody @Valid BoardRequestDTO boardRequestDTO,
                                                                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            HashMap<String, String> errors = boardService.validateHandling(bindingResult);
            GlobalCommonResponseDTO errorResponse = GlobalCommonResponseDTO.builder()
                    .code(GlobalCommonResponseCode.BAD_REQUEST.getCode())
                    .message("validation error")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        BoardResponseDTO postBoard = boardService.createBoardByRequest(boardRequestDTO);
        GlobalCommonResponseDTO successResponse = GlobalCommonResponseDTO.builder()
                .code(GlobalCommonResponseCode.SUCCESS.getCode())
                .message("create Board")
                .data(postBoard)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * 게시물 수정
     * @param
     * @param boardRequestDTO (수정할 게시물 내용)
     */
    @PutMapping("/api/support/{id}")
    public ResponseEntity<GlobalCommonResponseDTO> updateUserBoard(@PathVariable("id") Long boardId,
                                    @RequestBody @Valid BoardRequestDTO boardRequestDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            HashMap<String, String> errors = boardService.validateHandling(bindingResult);
            GlobalCommonResponseDTO errorResponse = GlobalCommonResponseDTO.builder()
                    .code(GlobalCommonResponseCode.BAD_REQUEST.getCode())
                    .message("validation error")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        BoardResponseDTO updateBoardByRequest = boardService.updateBoardInfoByRequest(boardId, boardRequestDTO);
        GlobalCommonResponseDTO successResponse = GlobalCommonResponseDTO.builder()
                .code(GlobalCommonResponseCode.SUCCESS.getCode())
                .message(GlobalCommonResponseCode.SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping("/api/support/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") Long boardId ){
        boardService.deleteByRequest(boardId);
        return ResponseEntity.noContent().build();
    }
}
