package com.zr.service.report;

import com.zr.vo.report.ReportVo;

import java.util.List;

public interface ReportService {
    /**
     * 车型统计报表service
     * @param param
     * @return
     */
    public List<ReportVo> selectCarTypeList(ReportVo param);

    /**
     * 财务统计报表service
     * @param param
     * @return
     */
    public List<ReportVo> selectFinanceList(ReportVo param);
}
