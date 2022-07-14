package com.zr.service.report.impl;

import com.zr.mapper.report.ReportMapper;
import com.zr.service.report.ReportService;
import com.zr.vo.report.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    /**
     * 获取车型统计service
     * @param param
     * @return
     */
    public List<ReportVo> selectCarTypeList(ReportVo param) {
        List<ReportVo> list = new ArrayList<ReportVo>();
        list = reportMapper.selectCarTypeList(param);
        if (list == null) {
            list = new ArrayList<ReportVo>();
        }
        return list;
    }

    /**
     * 财务统计报表service
     * @param param
     * @return
     */
    public List<ReportVo> selectFinanceList(ReportVo param) {
        List<ReportVo> list = new ArrayList<ReportVo>();
        list = reportMapper.selectFinanceList(param);
        if (list == null) {
            list = new ArrayList<ReportVo>();
        }
        return list;
    }
}
