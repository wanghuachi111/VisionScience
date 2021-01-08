package com.thc.watchapi.utils;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thc.watchapi.dto.PageDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.commonutils
 * @Description: 分页生成map对象用于存储
 * @date 2020/8/12 5:17 下午
 */
public class ResultPageUtil<T> {

    public static Map<String, Object> pageToMap(Page page){
        long total = page.getTotal();
        List<Object> records = page.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);
        return result;
    }

    public static <T> PageDto<T> pageToDto(Page page){
        Long total = page.getTotal();
        List<T> records = page.getRecords();
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setRecords(records);
        pageDto.setTotal(total);
        return pageDto;
    }
}
