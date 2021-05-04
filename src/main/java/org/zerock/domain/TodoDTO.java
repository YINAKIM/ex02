package org.zerock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TodoDTO {
    private String title;

    // 1. initBinder 사용하여 String파라미터를 java.util.Date형으로 변환
    private Date dueDate;

    // 2. initBinder 사용하지 않고 @DateTimeFormat 만으로 날짜형으로 변환
   /* @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date dueDate2;
    이거 안됨 다시해보기
    */
}
