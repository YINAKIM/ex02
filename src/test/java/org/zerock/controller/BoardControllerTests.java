package org.zerock.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet의 ServletContext를 이용하기 위해 스프링의 WebAppConfiguration 을 이용하겠다
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/applicationContext.xml"
                      ,"file:src/main/webapp/WEB-INF/spring/appServlet/dispatcher-servlet.xml"})  //  ({"루트xml","서블릿xml"}) => 주의
@Log4j
public class BoardControllerTests {

    @Setter(onMethod_ = {@Autowired})
    private WebApplicationContext ctx;

    private MockMvc mockMvc;    // 가짜mvc : 가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것 처럼 만들어서 Controller를 실행해볼 수 있게 함

        /*
        스프링이 제공하는 WebApplicationContext.class

        Tomcat실행하지 않고도 Controller자체 테스트를 하기 위해 Servlet의 ServletContext가 필요한데,
        이 클래스에서 Servlet을 생성해서 제공함
        ------- >> 웹서버 실행하지 않고도 스프링 내부에서 Servlet을 생성하여 Controller테스트 가능한 것
         @org.springframework.lang.Nullable
            javax.servlet.ServletContext getServletContext();

        /Users/kim-yina/Desktop/ex_ws/ex02/lib/spring-web-5.2.3.RELEASE.jar!/org
                                    /springframework/web/context/WebApplicationContext.class
        프로젝트 내부에서 실제 경로는 여기고
        package org.springframework.web.context;
        프로젝트 내부 lib/spring-web-5.2.3.RELEASE.jar안에 들어가있음
        pom.xml에는 spring-web
        */

                // @Before가 적용된 setUp() : import할 때 JUnit을 이용해야함,
    @Before     // 모든 테스트 전에 매번 실행되는 메서드라는 뜻
    public  void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
                //servlet.setup.MockMvcBuilder"s"; -> s주의!! setup.MockMvcBuilders;
                // 가짜mvc : 가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것 처럼 만들어서 Controller를 실행해볼 수 있게 함
    }

    @Test
    public void testList() throws Exception {

        // MockMvcRequestBuilders => Get방식 호출할 수 있게 함
        log.info(
        mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                .andReturn()
                .getModelAndView()
                .getModelMap()
        );
    }

    @Test
    public void testRegister() throws Exception{
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
                .param("title","테스트 새글 제목")
                .param("content","테스트 새글 내용")
                .param("writer","user00")
        ).andReturn().getModelAndView().getViewName();

        log.info(resultPage);

    }

//파라미터가 제대로 수집되는지 테스트
    @Test
    public void testGet()throws Exception{
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
                    .param("bno","2")).andReturn()
                .getModelAndView().getModelMap()        );
    }

    @Test
    public void testModify()throws Exception{
        String resultPage = mockMvc.perform(MockMvcRequestBuilders
                .post("/board/modify")
                .param("bno","1")
                .param("title","컨트롤러 테스트 제목")
                .param("content","컨트롤러 테스트 내용")
                .param("writer","user00")
                ).andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }

    @Test
    public void testRemove()throws Exception{

        //삭제 테스트 전 DB 게시물 번호 먼저 확인할 것
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
                .param("bno","2")
        ).andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }

    /*

    org.springframework.test.web.servlet;
    public class MockMvc
    가짜mvc : 가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것 처럼 만들어서
             실제 서버를 돌리지 않고도 서블릿을 생성,
             Controller를 실행해볼 수 있게 함

    => MockMvc 로 파라미터 전달 시 반드시 String으로 전달할 것
    */
}
