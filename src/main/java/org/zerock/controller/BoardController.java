package org.zerock.controller;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor // 이 클래스에 선언된 필드의 생성자를 만들고 자동으로 주입한다
public class BoardController {


    private BoardService service;

    @GetMapping("/list")
    public void list(Model model){
        log.info("list");
        model.addAttribute("list",service.getList());
    }

    //수정작업을 위해 입력받을 화면을 보여주는건 GET
    @GetMapping("/register")
    public void register(){

    }

    // 수정 작업은 POST
    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr){
        log.info("register : "+board);
        service.register(board);
        rttr.addFlashAttribute("result",board.getBno());
        return "redirect:/board/list";
    }

    @GetMapping("/get")
    public void get(@RequestParam("bno")Long bno, Model model){
        log.info("/get");
        model.addAttribute("board",service.get(bno));
    }

    //수정 처리 : 수정작업 시작화면은 GET, 실제 수정작업은 POST
    @PostMapping("board/modify")
    public String modify(BoardVO board, RedirectAttributes rttr){
        log.info("modify : "+board);

        //수정 성공하면 true리턴, int로 받을 수 있지만 정확히 하려고 bool로 리턴하도록 했음
        if (service.modify(board)){
            rttr.addFlashAttribute("result","success"); //
        }
        return "redirect:/board/list"; //수정 성공 후 list로 redirect
    }


    // 삭제는 반드시 POST처리!
    @PostMapping("/remove")
    public String remove(@RequestParam("bno")Long bno, RedirectAttributes rttr){
        log.info("remove..."+bno);
        if(service.remove(bno)){
            rttr.addFlashAttribute("result","success");
            //삭제 후 페이지 이동이 필요하므로 RedirectAttributes rttr 사용`
        }
        return "redirect:/board/list";
    }

    /*
        addFlashAttribute
        redirect할 때 데이터를 전달할 수 있음, String, Object 다 올수있음

        addAttribute와 다른 점
        일회성 값 전달, URL에 붙지 않고 세션 후 재지정 요청이 들어오면 값은 사라진다.

        redirect시 RedirectAttributes 클래스를 이용해 효과적으로 alert창을 띄울수도 있다.
    */
}
