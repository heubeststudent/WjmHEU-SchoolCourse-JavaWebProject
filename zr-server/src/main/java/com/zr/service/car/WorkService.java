package com.zr.service.car;

import com.zr.vo.car.Work;

import java.util.List;

/**
 * Create by zhangweilei
 * Date 2022/7/11 下午1:38
 * Description
 **/
public interface WorkService {
    List<Work> list(Work work);

    Work selectTotal(Long id);

    void update(int id, Double p, String msg);

    void carOut(int id, String carNo);

    void fit(long id);

    List<Work> worklist(Work work);

    List<Work> selectwork(Work work);
}
