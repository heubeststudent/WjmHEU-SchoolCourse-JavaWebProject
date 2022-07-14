package com.zr.controller.report;

import com.alibaba.fastjson.JSON;
import com.zr.service.report.ReportService;
import com.zr.util.AjaxResult;
import com.zr.vo.report.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计车型报表
     * @param param
     * @return
     */
    @ResponseBody
    @PostMapping("/selectCarTypeList")
    public String selectCarTypeList(@RequestBody ReportVo param) {
        List<ReportVo> res = reportService.selectCarTypeList(param);
        return JSON.toJSONString(AjaxResult.success("查询成功", res));
    }

    /**
     * 统计财务报表
     * @param param
     * @return
     */
    @PostMapping("/selectFinanceList")
    @ResponseBody
    public String selectFinanceList(@RequestBody ReportVo param) {
       List<ReportVo> res = reportService.selectFinanceList(param);
        return JSON.toJSONString(AjaxResult.success("查询成功",res));
    }
}
