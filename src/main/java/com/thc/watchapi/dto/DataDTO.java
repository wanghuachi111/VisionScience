package com.thc.watchapi.dto;


import com.thc.watchapi.model.Systemstatus;
import lombok.Data;

import java.util.List;

@Data
public class DataDTO {

    private List<Systemstatus> list;
    private Double max;
    private Double average;
    private Double fangcha;

}
