package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   // (이걸로)스프링을 실행시킬꺼다
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext.xml") // ("file:여기경로/경로/설정파일")을 참고할꺼다
@Log4j
public class BoardMapperTests {

    @Setter(onMethod_=@Autowired)
    private BoardMapper mapper;

    @Test
    public void testGetList(){
        mapper.getList().forEach(board -> log.info(board));
    }



}
