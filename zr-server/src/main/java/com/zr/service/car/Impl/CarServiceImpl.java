package com.zr.service.car.Impl;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zr.mapper.car.CarMapper;
import com.zr.service.car.CarService;
import com.zr.vo.car.Car;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {


    @Resource
    private CarMapper carMapper;


    /**
     * 查询所有车
     * @return
     */
    @Override
    public List<Car> selectList() {
        return carMapper.selectAll();
    }


    @Override
    public List<Car> list(Car car) {
        PageHelper.startPage(car.getPageNum(), car.getPageSize());
        return carMapper.list(car);
    }

    @Override
    public void add(Car car) {
        carMapper.insertSelective(car);
        car.setModifyTime(new Date());
    }

    @Override
    public void edit(Car car) {
        carMapper.updateByPrimaryKeySelective(car);
        car.setModifyTime(new Date());
    }

    @Override
    public void del(Long id) {
        carMapper.deleteByPrimaryKey(id);

    }

    @Override
    public String uploadImg(String imgUrl) {
        if (StringUtils.isNullOrEmpty(imgUrl))return "";
        byte[] b = new byte[0];
        File file = new File(imgUrl);
        try(FileInputStream fis = new FileInputStream(file)){
            b =  new byte[(int)file.length()];//强转成int
            fis.read(b);
        }catch (Exception e){}
        return Base64.getEncoder().encodeToString(b);//将数组b转化成Base64规定的形式
    }
}
