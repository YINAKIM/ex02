package org.zerock.mapper;



import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;

import java.util.List;

public interface BoardMapper {

    //@Service 패키지 주의! org.apache.ibatis.annotations.Select
//    @Select("SELECT * FROM TBL_BOARD WHERE BNO>0")
    public List<BoardVO> getList();


    // 1. insert만 처리, 생성된 PK값을 알 필요가 없는 경우
    public void insert(BoardVO board);

    // 2. insert실행 후 생성된 PK값을 알아야 하는 경우
    public void insertSelectKey(BoardVO board);


}

