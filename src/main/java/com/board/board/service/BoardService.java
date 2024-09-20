package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired // 이걸 쓰면 new 없이도 자동으로 만들어줌. 디펜던시 인덱션
    private BoardRepository boardRepository;

    // 글쓰기 처리
    public void boardWrite(Board board, MultipartFile file) throws IOException {
        String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";; // 파일 저장할 위치
        UUID fileUuid = UUID.randomUUID(); // 랜덤한 무작위 식별자.
        String fileName = fileUuid + file.getOriginalFilename(); // 식별자 + 원래이름.
        File savedFile = new File(filepath, fileName); // 빈공간 생성
        file.transferTo(savedFile); // 변경해줌.
        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }

    // 게시글 리스트(자바에서 지원하는 List 메서드를 이용해) 처리
    public Page<Board> boardList(Pageable pageable) {
        //List<Board> list = boardRepository.findAll();
        return boardRepository.findAll(pageable);
        //System.out.println("list = " + list);
    }

    public Board boardView(Integer id) { // 자료형 꼭 맞춰줘야함.
        System.out.println("board = " + id); // boardView 쓰면안됨. 없는거니까.
        return boardRepository.findById(id).get();
    }

    //  특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
