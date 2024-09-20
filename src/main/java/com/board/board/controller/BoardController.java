package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") // localhost:9999/board/write
    public String boardWrite() {
        return "boardWrite"; // 어떤 html 페이지로 보낼지 알려주는거 (맵핑처럼 / 쓰면 안댐. 왜냐면, html 페이지랑 이름이 같아야함)
    }

    @PostMapping("/board/writePro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception { // 이렇게 쓰면 간단함 + 예외처리 해줘야함. boardWrite 가 에러 나서.
        boardService.boardWrite(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message"; // 여기에 값이 없기 때문에 오류 발생. 임시로 LIST 로 매꿔둠.
    }

    @GetMapping("/board/list")
    public String boardList (Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) { // 데이터를 받아서 보여주기 위함.
        Page<Board> list = boardService.boardList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1; // 0부터 시작하기 때문에 1을 더해줘야함.
        int startPage = Math.max(nowPage - 4, 1); // -3을 방지하기 위한 math 메서드 사용.
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // 최대 페이지 갯수를 찾기 위해 total 로 잡아줌.
        model.addAttribute("list", boardService.boardList(pageable)); // 리스트라는 이름으로 보내는데, 보드 리스트에 반환할게.
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardList"; // 여기 값 HTML 이름과 동일해야함!!!!! 매우 중요. 그리고 오타 주의!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @GetMapping("/board/view") // localhost:9999/board/view?id=1 (쿼리스트링)
    public String boardView (Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id)); // addAttribute  이름 잘못됨.
        return "boardView";
    }

    // 특정 게시글 삭제
    @GetMapping("/board/delete")
    public String boardDelete (Integer id, Model model) {
        boardService.boardDelete(id);
        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        //return "redirect:/board/list";
        return "message";
    }

    // 특정 게시글 수정
    @GetMapping("/board/modify/{id}") // 쿼리스트링보다 깔끔하게 넘어감.
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id)); // 내용이 같기에 view 와 같음.
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception {
        Board boardTemp = boardService.boardView(id); // 기존 내용
        boardTemp.setTitle(board.getTitle()); // 새로 입력한 내용 덮어씌우기
        boardTemp.setContent(board.getContent());
        boardService.boardWrite(boardTemp, file); ///받은데이터 값을 덮어 씌워줌.
        System.out.println("boardTemp : " + boardTemp);
        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
        //return "redirect:/board/list";
    }

}
