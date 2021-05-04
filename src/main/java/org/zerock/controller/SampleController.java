package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.TodoDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @RequestMapping("")
    public void basic() {
        log.info("basic..........");
    }

    @RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
    public void basicGet() {
        log.info("basic get..........");

    }


    @GetMapping("/basicOnlyGet/")
    public void basicGet2() {
        log.info("basic get only get..........");

    }


    /*
    p130
    SampleDTO를 파라미터로 사용
    -> Lombok의 @Data로 자동 setter메서드
    -> 파라미터 자동수집
     */
    @GetMapping
    public String ex01(SampleDTO dto){
        log.info(""+dto);

        //http://localhost:8001/sample/ex01?name=AA&age=10
//        INFO : org.zerock.controller.SampleController - SampleDTO(name=AA, age=10)
        return "ex01";

    }

    /*
     * 원래 Controller가 파라미터 수집하는 방식 ==> 파라미터 자료형에 따라 자동변환
     * 예 ) int형 age는 자동으로 숫자로 변환됨
     * */


    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age){

        log.info("name : "+name);   //INFO : org.zerock.controller.SampleController - name : YINA
        log.info("age : "+age);     //INFO : org.zerock.controller.SampleController - age : 20

        return "ex02";

    }

    // List파라미터
    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids")ArrayList<String> ids){

                                     //http://localhost:8001/sample/ex02List?ids=111&ids=222&ids=333
        log.info("ids : "+ids);     //INFO : org.zerock.controller.SampleController - ids : [111, 222, 333]
        return "ex02List";
    }

    //배열(Array) 파라미터 : @RequestParam으로 배열을 받은 예
    @GetMapping("/ex02Array")
    public String ex02Array(@RequestParam("ids") String[] ids){
                                                        //http://localhost:8001/sample/ex02Array?&ids=AA&ids=BBBB
        log.info("arr ids : "+ Arrays.toString(ids));   //INFO : org.zerock.controller.SampleController - arr ids : [AA, BBBB]
        return "ex02Array";
    }

    // Bean 파라미터 : 객체 리스트를 멤버변수로 갖는 List객체를 파라미터로 받아옴 -> 배열로 들어옴
    @GetMapping("ex02Bean")
    public String ex02Bean(SampleDTOList list){

        /*
        테스트 URI)  /sample/ex02Bean?list[0].name=가가가&list[1].name=나나나

        ERROR 메세지 : java.lang.IllegalArgumentException: 요청 타겟에서 유효하지 않은 문자가 발견되었습니다. 유효한 문자들은 RFC 7230과 RFC 3986에 정의되어 있습니다.
        Tomcat버전에 따라 [ ] 를 특수문자로 허용하지 않음
        방법1) js이용하는 경우 encodeURIComponent() 활용
        방법2) 이스케이프시퀀스 사용
            [ ====> %5B
            ] ====> %5D

        테스트 URI) /sample/ex02Bean?list%5B0%5D.name=가가가&list%5B1%5D.name=나나나
        테스트 결과) INFO : org.zerock.controller.SampleController - list dtos : SampleDTOList(list=[SampleDTO(name=가가가, age=0), SampleDTO(name=나나나, age=0)])
        */
        log.info("list dtos : "+list);
        return "ex02Bean";
    }

    @InitBinder // 파라미터를 직접 변환해서 사용해야 할 경우 ( '2021-01-01'을 받아서 Date형으로 넣어야 할 경우)
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        /*
        public abstract void registerCustomEditor(Class<?> aClass, java.beans.PropertyEditor propertyEditor)

        PropertyEditorRegistry 인터페이스를 상속받는 DataBinder의 메서드
        => 요청받는 데이터타입(requiredType)에 맞는
        사용자가 주어진 유형의 속성 값을 편집 할 수 있도록하려는 GUI를 지원합니다
        속성 값을 표시하고 업데이트하는 다양한 방법을 지원합니다.
        대부분의 PropertyEditor는이 API에서 사용할 수있는 다양한 옵션의 하위 집합 만 지원하면됩니다.
        */


    }


    // 1. initBinder 사용하여 String파라미터를 java.util.Date형으로 변환
    @GetMapping("ex03")
    public String ex03(TodoDTO todo2){
        /* 테스트 URI)  /sample/ex03?title=숙제&dueDate=2021-05-01

        INFO : org.zerock.controller.SampleController - todo2 : TodoDTO(title=숙제, dueDate=Sat May 01 00:00:00 KST 2021
        */
       log.info("todo2: "+todo2);
        return "ex03";
    }


    // 2. initBinder 사용하지 않고 @DateTimeFormat 만으로 날짜형으로 변환
    @GetMapping("ex03dtf")
    public String ex03dtf(TodoDTO todo3){
        /* 테스트 URL)  /sample/ex03dtf?title=내생일&dueDate2=1991/10/10

       */
        log.info("todo3: "+todo3);
        return "ex03dtf";
    } //-> 이거 안됨, 다시해보기 400에러남 (요청주소 확인)


    // Model에 담아보내기
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto,@ModelAttribute("page") int page){
        log.info("dto : "+dto);
        log.info("page : "+page);

        /*
        테스트 URI 1)  /sample/ex04?name=aaa&age=11&page=9
        브라우저) SAMPLEDTO SampleDTO(name=aaa, age=11)
                PAGE
        log결과) INFO : org.zerock.controller.SampleController - dto : SampleDTO(name=aaa, age=11)
                INFO : org.zerock.controller.SampleController - page : 9

        테스트 URI 2)
        (SampleDTO dto, int page) ->  (SampleDTO dto, @ModelAttribute("page")int page)
            ModelAttribute에 담아서 받음 .
        [브라우저 결과]
        SAMPLEDTO SampleDTO(name=aaa, age=11)
        PAGE 9
        [log결과]
        위와 동일
        */
        return "/sample/ex04";
    }

    // JSON 데이터로 반환 => {"name":"홍길동","age":10}
    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06(){
        log.info("/ex06..........ResponseBody SampleDTO");

        SampleDTO dto = new SampleDTO();
        dto.setAge(10);
        dto.setName("홍길동");

        return dto;
    }


    // ResponseEntity - 원하는 HTTP 헤더메세지 가공
    @GetMapping("ex07")
    public ResponseEntity<String> ex07(){
        log.info("/ex07....................");

        String msg = "{\"name\":\"홍길동\"}"; // {"name":"홍길동"}

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","application/json;charset=UTF-8");

        return new ResponseEntity<>(msg,header, HttpStatus.OK);
        // =>HttpStatus.OK : Status Code 200
    }

    // 파일업로드 테스트
    @GetMapping("/exUpload")
    public void exUpload(){
        log.info("exUpload...................");
    }

    //한번에 여러 파일 업로드 --> 지금은 로그로 확인만, 실제 업로드 처리는 part5에서
    //@RequestMapping(value = "/exUploadPost",method = RequestMethod.POST)
    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files){
                                //동일이름으로 여러개 파라미터 존재  ==> 배열로 처리가능
        files.forEach(file -> {
            log.info("*************************************");
            log.info("name : "+file.getOriginalFilename());
            log.info("name : "+file.getSize());
        });

        /*
        [1차에러]
        로그에는 아무것도 안나오고 URI에는 http://localhost:8001/sample/"/sample/exUploadPost" 라고뜸
        java.lang.ClassNotFoundException: org.apache.commons.io.IOUtils

        -> 멍청하게 GetMapping 함 ㅋㅋㅋㅋㅋ PostMapping

        [2차에러]
        jsp에서 경로 고쳐도  http://localhost:8001/sample/"exUploadPost" 뜨고 노매핑 떠서
        action='' 로 따옴표 바꾸니까 됨....왜? 왜 큰따옴표 못읽어?
        INFO : org.zerock.controller.SampleController - *************************************
        INFO : org.zerock.controller.SampleController - name : 스크린샷 2021-05-01 오전 2.22.02.png
        INFO : org.zerock.controller.SampleController - name : 183816
        INFO : org.zerock.controller.SampleController - *************************************
        * */
    }

}