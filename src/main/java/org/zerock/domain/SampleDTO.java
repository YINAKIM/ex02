package org.zerock.domain;

import lombok.Data;

@Data   // getter, setter, toString 자동생성하는 Lombok의 어노테이션
public class SampleDTO {
    private String name;
    private int age;
}
