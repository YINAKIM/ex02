package org.zerock.controller;

import lombok.Data;
import org.zerock.domain.SampleDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class SampleDTOList {
    private List<SampleDTO> list;

    public SampleDTOList(){
        list = new ArrayList<>();
    }

}
