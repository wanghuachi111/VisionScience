package com.thc.watchapi.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thc.watchapi.response.BaseResult;
import com.thc.watchapi.service.SystemstatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "可视化科研-系统参数表")
@RestController
@RequestMapping("/system")
public class SystemstatusController {

    @Autowired
    private SystemstatusService systemstatusService;

    @ApiOperation(value ="按时间点进行单个数据查询")
    @GetMapping("query")
    public BaseResult<Object> queryUnderTime(@RequestParam(value = "time") String time){

//        return R.ok();
        return BaseResult.success( systemstatusService.queryDataPeriod(time));
    }


    @ApiOperation(value = "对中量-时间")
    @GetMapping("query1")
    public BaseResult<Object> queryDuringTime(@RequestParam(value = "startTime") String startTime,
                                              @RequestParam(value = "endTime") String endTime) {
        return BaseResult.success(systemstatusService.queryDataPeriod2(startTime,endTime));


    }


    @ApiOperation(value = "扭矩-时间")
    @GetMapping("query2")
    public BaseResult<Object> queryNiuJu(@RequestParam(value = "startTime") String startTime,
                                         @RequestParam(value = "endTime") String endTime) {
        return BaseResult.success(systemstatusService.queryNiuJu(startTime, endTime));
    }


    @ApiOperation(value = "X倾角-时间")
    @GetMapping("query3")
    public BaseResult<Object> queryQinJiaoX(@RequestParam(value = "startTime") String startTime,
                                         @RequestParam(value = "endTime") String endTime) {
        return BaseResult.success(systemstatusService.queryQinJiaoX(startTime, endTime));
    }

    @ApiOperation(value = "Y倾角-时间")
    @GetMapping("query4")
    public BaseResult<Object> queryQinJiaoY(@RequestParam(value = "startTime") String startTime,
                                            @RequestParam(value = "endTime") String endTime) {
        return BaseResult.success(systemstatusService.queryQinJiaoY(startTime, endTime));
    }
}
