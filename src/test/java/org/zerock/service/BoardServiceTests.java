package org.zerock.service;


import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)   // (이걸로)스프링을 실행시킬꺼다
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext.xml") // ("file:여기경로/경로/설정파일")을 참고할꺼다
@Log4j
public class BoardServiceTests {

    @Setter(onMethod_ = @Autowired)
    private BoardService service;

    @Test
    public void testExist(){
        log.info(service);
        assertNotNull(service); //  BoardService가 제대로 주입이 가능한지 테스트
        //INFO : org.zerock.service.BoardServiceTests - org.zerock.service.BoardServiceImpl@4455f57d -> 주입 OK
    }

    @Test
    public void testRegister(){

        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글 서비스테스트");
        board.setContent("새로 작성하는 내용 서비스테스트");
        board.setWriter("newbi");

        service.register(board);

        log.info("생성된 게시물의 번호 : "+board.getBno());
        //INFO : org.zerock.service.BoardServiceTests - 생성된 게시물의 번호 : 15
    }

    @Test
    public void testGetList(){
        service.getList().forEach(board -> log.info(board)); //OK
    }

    @Test
    public void testGet(){
        log.info(service.get(1L)); //OK
    }

    @Test
    public void testUpdate(){

        BoardVO board = service.get(1L);            //  1. 게시물을 먼저 조회

        if(board==null){
            return;
        }                                           //  null일경우 종료

        board.setTitle("제목수정!!");                 // 수정할 값 세팅
        log.info("MODIFY RESULT : "+service.modify(board));     //  수정
    }

    @Test
    public void testDelete(){

        //게시물의 존재 여부를 확인하고 테스트할 것 -> INFO : org.zerock.service.BoardServiceTests - REMOVE RESULT : true
        log.info("REMOVE RESULT : "+service.remove(6L)); //OK
    }
}
