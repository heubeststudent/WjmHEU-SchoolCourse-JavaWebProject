package com.zr.service.car.Impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.car.WorkMapper;
import com.zr.service.car.WorkService;
import com.zr.util.TokenUtil;
import com.zr.vo.car.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Create by zhangweilei
 * Date 2022/7/11 下午1:39
 * Description
 **/
@Service
public class WorkServiceImpl implements WorkService {
    @Resource
    private WorkMapper workMapper;
    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public List<Work> list(Work work) {
        PageHelper.startPage(work.getPageNum(), work.getPageSize());
        return workMapper.list(work);
    }

    @Override
    public Work selectTotal(Long id) {
        return workMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(int id, Double p, String msg) {
        Work work = new Work();
        work.setTotal(new BigDecimal(p));
        work.setMark(msg);
        work.setInTime(new Date());
        work.setInUserId(tokenUtil.getUserId());
        work.setStatus("已回车");
        work.setId(new Long(id));
        workMapper.update(work);
    }

    @Override
    public void carOut(int id, String carNo) {
        Work work = new Work();
        work.setId((long) id);
        work.setStatus("已出车");
        work.setOutUserId(tokenUtil.getUserId());
        work.setOutTime(new Date());
        work.setCarNo(carNo);
        workMapper.carOut(work);
    }

    @Override
    public void fit(long id) {
        Work work = new Work();
        work.setId(id);
        work.setOutFitTime(new Date());
        work.setOutFitUserId(tokenUtil.getUserId());
        work.setStatus("已整备");
        workMapper.fit(work);
    }


    @Override
    public List<Work> worklist(Work work) {
        PageHelper.startPage(work.getPageNum(), work.getPageSize());
        return workMapper.worklist(work);
    }

    @Override
    public List<Work> selectwork(Work work) {
        return workMapper.selectwork(work);
    }


}
