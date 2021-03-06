package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import java.util.List;


//어노테이션은 패키지를 읽어들이는 시점에 처리됨

@Service // 계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시
@Log4j
@AllArgsConstructor //  모든 파라미터를 이용하는 생성자를 만드는 어노테이션 : 실제 코드는 BoardMapper를 주입받는 생성자가 만들어짐
public class BoardServiceImpl implements BoardService{

    // @Autowired -> 이것도 OK, 근데 Lombok사용할 때는 @Setter를 권장
    @Setter(onMethod_ = @Autowired)
    private BoardMapper mapper;

    @Override
    public void register(BoardVO board) {
        log.info("register.........."+board);
        mapper.insertSelectKey(board);
        //BoardService는 void register , insertSelectKey는 int 리턴
        // => OK, 필요하다면 예외처리나 int리턴으로 사용가능 : 실행 후 생성된 게시물 벊를 확인할 수 있음
    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get.........."+bno);
        return mapper.read(bno);
    }

    @Override
    public boolean modify(BoardVO board) {
        log.info("modify......"+board);
        return mapper.update(board) == 1;       //  삭제/수정은 void로 설계할 수 도 있지만 성공여부를 정확히 처리하기 위해 boolean으로
    }

    @Override
    public boolean remove(Long bno) {
        log.info("remove......"+bno);
        return mapper.delete(bno) == 1;       //  삭제/수정은 void로 설계할 수 도 있지만 성공여부를 정확히 처리하기 위해 boolean으로

    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        log.info("#########getList with criteria : "+ cri);
        return mapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        log.info(".......getTotal_ROW");
        return mapper.getTotal(cri);
    }

/*
페이징처리안된 CRUD테스트용 getList

    @Override
    public List<BoardVO> getList() {
        log.info("getList.............");
        return mapper.getList();
    }
*/



}
