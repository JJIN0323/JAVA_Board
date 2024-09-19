package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired // 이걸 쓰면 new 없이도 자동으로 만들어줌. 디펜던시 인덱션
    private BoardRepository boardRepository;

    // 글쓰기 처리
    public void boardWrite(Board board) {
        boardRepository.save(board);
    }

    // 게시글 리스트(자바에서 지원하는 List 메서드를 이용해) 처리
    public List<Board> boardList() {
        List<Board> list = boardRepository.findAll();
        System.out.println("list = " + list);
        return list;
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
