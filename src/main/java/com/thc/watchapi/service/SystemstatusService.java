package com.thc.watchapi.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.watchapi.dto.DataDTO;
import com.thc.watchapi.dto.SystemstatusDTO;
import com.thc.watchapi.dto.WeiyiDTO;
import com.thc.watchapi.mapper.AirbagvalueMapper;
import com.thc.watchapi.mapper.SystemstatusMapper;
import com.thc.watchapi.model.Airbagvalue;
import com.thc.watchapi.model.Systemstatus;
import com.thc.watchapi.model.WatchData;
import com.thc.watchapi.model.Weiyivalue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SystemstatusService {

    @Autowired
    private SystemstatusMapper systemstatusMapper;

    public List<Systemstatus> queryDataPeriod(String time) {
        QueryWrapper<Systemstatus> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(time)){
            wrapper.like("DateTime", time).orderByAsc("id");
        }
        List<Systemstatus> systemstatusList = systemstatusMapper.selectList(wrapper);
        return systemstatusList;
    }


    public SystemstatusDTO queryDataPeriod2(String startTime, String endTime) {
        QueryWrapper<Systemstatus> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            wrapper.between("DateTime", startTime, endTime).orderByAsc("ID");
        }
        List<Systemstatus> systemstatusList = systemstatusMapper.selectList(wrapper);

        Double XoffsetAverage;
        Double YoffsetAverage;
        Double HorAngleAverage;
        Double VerAngelAverage;
//        Double average5;




        Double nums1=0.0;
        Double nums2=0.0;
        Double nums3=0.0;
        Double nums4=0.0;
//        Double nums5=0.0;


        List<Double> value1=new ArrayList<>();
        List<Double> value2=new ArrayList<>();
        List<Double> value3=new ArrayList<>();
        List<Double> value4=new ArrayList<>();
//        List<Double> value5=new ArrayList<>();




        for (Systemstatus systemstatus : systemstatusList) {
            nums1=nums1+Double.parseDouble(systemstatus.getXOffset());
            nums2=nums2+Double.parseDouble(systemstatus.getYOffset());
            nums3=nums3+Double.parseDouble(systemstatus.getHorAngle());
            nums4=nums4+Double.parseDouble(systemstatus.getVerAngle());
//            nums5=nums5+Double.parseDouble(systemstatus.getS());


            value1.add(Double.parseDouble(systemstatus.getXOffset()));
            value2.add(Double.parseDouble(systemstatus.getYOffset()));
            value3.add(Double.parseDouble(systemstatus.getHorAngle()));
            value4.add(Double.parseDouble(systemstatus.getVerAngle()));
//            value5.add(Double.parseDouble(systemstatus.getWeiyi5()));

        }

        //获取峰值
        Double maxofXoffset = Collections.max(value1);
        Double maxofYoffset = Collections.max(value2);
        Double maxofHorAngle = Collections.max(value3);
        Double maxofVerAngle = Collections.max(value4);
//        Double max5 = Collections.max(value5);


        //获取均值
        XoffsetAverage = nums1/systemstatusList.size();
        YoffsetAverage = nums2/systemstatusList.size();
        HorAngleAverage = nums3/systemstatusList.size();
        VerAngelAverage = nums4/systemstatusList.size();
//        average5 = nums5/systemstatusList.size();


        SystemstatusDTO dto = new SystemstatusDTO();
        dto.setList(systemstatusList);
        dto.setXoffsetAverage(XoffsetAverage);
        dto.setYoffsetAverage(YoffsetAverage);
        dto.setHorAngleAverage(HorAngleAverage);
        dto.setVerAngelAverage(VerAngelAverage);
//        dto.setAverage5(average5);


        dto.setMaxofXoffset(maxofXoffset);
        dto.setMaxofYoffset(maxofYoffset);
        dto.setMaxofHorAngle(maxofHorAngle);
        dto.setMaxofVerAngle(maxofVerAngle);
//        dto.setMax5(max5);

        return dto;
    }


    public DataDTO queryNiuJu(String startTime, String endTime) {


        QueryWrapper<Systemstatus> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            wrapper.between("DateTime", startTime, endTime).orderByAsc("ID");
        }

        List<Systemstatus> systemstatusList = systemstatusMapper.selectList(wrapper);


        List<Double> niujuList = new ArrayList<>();
        Double num =0.0;

        for (Systemstatus systemstatus : systemstatusList) {
            Double speed = Double.parseDouble(systemstatus.getSpeed());
            Double gonglv;
            if (speed <= 55) {
                gonglv = 315.0;
            } else if(speed < 115){
                gonglv = 800.0;
            } else {
                gonglv = 3500.0;
            }

            Double niuju = 30*gonglv/(speed*Math.PI);
            num = num +niuju;

            niujuList.add(niuju);
        }

        Double maxNiuju = Collections.max(niujuList);
        Double averageNiuju = num/niujuList.size();


        Double numfangcha = 0.0;
        for (Double niuju : niujuList) {
            numfangcha = numfangcha + (niuju - averageNiuju)*(niuju - averageNiuju);
        }

        Double fangcha = Math.sqrt(numfangcha);
        DataDTO dataDTO = new DataDTO();
        dataDTO.setAverage(averageNiuju);
        dataDTO.setFangcha(fangcha);
        dataDTO.setMax(maxNiuju);

        return dataDTO;

    }

    public DataDTO queryQinJiaoX(String startTime, String endTime) {


        QueryWrapper<Systemstatus> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            wrapper.between("DateTime", startTime, endTime).orderByAsc("ID");
        }

        List<Systemstatus> systemstatusList = systemstatusMapper.selectList(wrapper);


        List<Double> QinJiaoXList = new ArrayList<>();
        Double num =0.0;

        for (Systemstatus systemstatus : systemstatusList) {
            Double qinjiaoX = Double.parseDouble(systemstatus.getXDeflection());

            num = num +qinjiaoX;

            QinJiaoXList.add(qinjiaoX);
        }

        Double maxQinJiao = Collections.max(QinJiaoXList);
        Double averageQinJiao = num/QinJiaoXList.size();


        Double numfangcha = 0.0;
        for (Double qinjiao : QinJiaoXList) {
            numfangcha = numfangcha + (qinjiao - averageQinJiao)*(qinjiao - averageQinJiao);
        }

        Double fangcha = Math.sqrt(numfangcha);
        DataDTO dataDTO = new DataDTO();
        dataDTO.setAverage(averageQinJiao);
        dataDTO.setFangcha(fangcha);
        dataDTO.setMax(maxQinJiao);

        return dataDTO;

    }


    public DataDTO queryQinJiaoY(String startTime, String endTime) {


        QueryWrapper<Systemstatus> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            wrapper.between("DateTime", startTime, endTime).orderByAsc("ID");
        }

        List<Systemstatus> systemstatusList = systemstatusMapper.selectList(wrapper);


        List<Double> QinJiaoYList = new ArrayList<>();
        Double num =0.0;

        for (Systemstatus systemstatus : systemstatusList) {
            Double qinjiaoY = Double.parseDouble(systemstatus.getXDeflection());

            num = num +qinjiaoY;

            QinJiaoYList.add(qinjiaoY);
        }

        Double maxQinJiao = Collections.max(QinJiaoYList);
        Double averageQinJiao = num/QinJiaoYList.size();


        Double numfangcha = 0.0;
        for (Double qinjiao : QinJiaoYList) {
            numfangcha = numfangcha + (qinjiao - averageQinJiao)*(qinjiao - averageQinJiao);
        }

        Double fangcha = Math.sqrt(numfangcha);
        DataDTO dataDTO = new DataDTO();
        dataDTO.setAverage(averageQinJiao);
        dataDTO.setFangcha(fangcha);
        dataDTO.setMax(maxQinJiao);

        return dataDTO;

    }




}
