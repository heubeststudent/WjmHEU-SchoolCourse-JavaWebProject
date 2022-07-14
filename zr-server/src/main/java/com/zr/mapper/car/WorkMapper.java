package com.zr.mapper.car;

import com.zr.vo.car.Work;

import java.util.List;

public interface WorkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Work record);

    int insertSelective(Work record);

    Work selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Work record);

    int updateByPrimaryKey(Work record);

    List<Work> list(Work work);

    void update(Work work);

    void carOut(Work work);

    void fit(Work work);

    List<Work> worklist(Work work);

    List<Work> selectwork(Work work);
}