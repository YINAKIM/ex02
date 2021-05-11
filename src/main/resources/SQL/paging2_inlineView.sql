-- ROWNUM으로 가져온 데이터에서 1페이지분량 빼고 2페이지만 출력하기 : 인라인뷰 사용
-- SELECT문 안쪽 FROM에 다시 SELECT :
/*
 SELECT
   FROM (SELECT ROWNUM으로 가져온 1,2페이지 전체데이터)
  WHERE ROWNUM > 10 ; ------> ROWNUM은 반드시 언제나 1을 포함해야하므로 전체where조건(제일바깥족 where)에 넣음
*/

select bno,title,content,writer
from
    (SELECT /*+ INDEX_DESC(TBL_BOARD PK_BOARD)*/
            ROWNUM rn, BNO, TITLE, CONTENT, WRITER
       FROM TBL_BOARD
      WHERE ROWNUM <= 20)
where rn > 10 ;






--[1] 나옴 ( ROWNUM 11 ~ 20 )
select alias_rn, bno,title,content,writer
from
    (SELECT /*+ INDEX_DESC(TBL_BOARD PK_BOARD)*/
            ROWNUM alias_rn, BNO, TITLE, CONTENT, WRITER
       FROM TBL_BOARD
      WHERE ROWNUM <= 20)
where alias_rn > 10 ;


--[2] 안나옴
select ROWNUM, bno,title,content,writer
from
    (SELECT /*+ INDEX_DESC(TBL_BOARD PK_BOARD)*/
         ROWNUM, BNO, TITLE, CONTENT, WRITER
     FROM TBL_BOARD
     WHERE ROWNUM <= 20)
where ROWNUM > 10 ;

/*
    인라인뷰 안에서 검색한 ROWNUM범위중에 바깥쪽 where 조건을 걸러야되는데
    ROWNUM이라는 말은 오라클이 이미 알고있는 변수다

    → 인라인뷰안의 ROWNUM결과가 아닌 오라클이 알고있는 ROWNUM으로 인식한 것 !

    그렇기때문에 인라인뷰 안쪽에서 alias를 주고 그 alias로 where조건을 검색하면
    alias_rn이라고 별명이 붙은 바로 그 ROWNUM 범위 중에서 where조건을 걸러내는 것
*/