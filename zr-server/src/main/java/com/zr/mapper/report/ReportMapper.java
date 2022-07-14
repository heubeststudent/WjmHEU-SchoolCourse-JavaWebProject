package com.zr.mapper.report;

import com.zr.vo.report.ReportVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    /**
     * 统计车型报表
     * @param param
     * @return
     */
    List<ReportVo> selectCarTypeList(ReportVo param);

    List<ReportVo> selectFinanceList(ReportVo param);
}
