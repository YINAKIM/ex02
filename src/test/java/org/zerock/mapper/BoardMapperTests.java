package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)   // (이걸로)스프링을 실행시킬꺼다
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext.xml") // ("file:여기경로/경로/설정파일")을 참고할꺼다
@Log4j
public class BoardMapperTests {

    @Setter(onMethod_=@Autowired)
    private BoardMapper mapper;

    // BoardMapper.xml - 조회 테스트
    @Test
    public void testGetList(){
        mapper.getList().forEach(board -> log.info(board));
    }

    // BoardMapper.xml - 등록테스트 : 1.단순 insert
    @Test
    public void testInsert(){
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용내용");
        board.setWriter("newbi");

        mapper.insert(board);
        log.info(board);    // Lombok이 만들어주는 toString()을 이용해서 bno멤버변수의 값을 알아보기 위함
        // INFO :
        // BoardVO(bno=null, title=새로 작성하는 글, content=새로 작성하는 내용내용, writer=newbi, regdate=null, updateDate=null)
        // => 등록은 되었지만 bno=null이고, default 설정되어있는 regdate, updatedate 값도 null로 확인됨
    }

    // 2. selectKey이용한 insert
    @Test
    public void testInsertSelectKey(){
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글selectKey3");
        board.setContent("새로 작성하는 내용내용selectKey3");
        board.setWriter("newbi");

        mapper.insertSelectKey(board);
        log.info(board);  // 자동으로 추가되는 selectKey확인 - bno멤버변수의 값
        // INFO : org.zerock.mapper.BoardMapperTests -
        // BoardVO(bno=9, title=새로 작성하는 글selectKey3, content=새로 작성하는 내용내용selectKey3, writer=newbi, regdate=null, updateDate=null)

    }


    // 3. PK를 파라미터로 하는 read
    @Test
    public void testread(){

        //존재하는 게시물 번호로 테스트
        BoardVO board = mapper.read(3L); // private Long bno;
        log.info(board);
    }


    // 4. delete 처리
    @Test
    public void testDelete(){
        log.info("DELETE COUNT: " + mapper.delete(3L));
    }

    // 5. update 처리
    @Test
    public void testUpdate(){

        BoardVO board = new BoardVO();

        //실행 전 존재하는 번호인지 확인할 것.
        board.setBno(2L);
        board.setTitle("update제목");
        board.setContent("update내용");
        board.setWriter("user00");

        int count = mapper.update(board);
        log.info("UPDATE COUNT : "+count);
    }



    // 페이징처리를 위한 mapper테스트
    @Test
    public void testPaging(){
        Criteria cri = new Criteria(); // 기본생성자에 테스트용으로 (페이지번호 1,한페이지로우 10)으로 설정해둠

        //  WHERE ROWNUM <= #{pageNum} * #{amount}
        //  ( #{pageNum}-1 ) * #{amount} 으로 바꾸고 테스트
        cri.setPageNum(4);
        cri.setAmount(10);

        List<BoardVO> list = mapper.getListWithPaging(cri);
        list.forEach(board -> log.info(board) );
    }


}
