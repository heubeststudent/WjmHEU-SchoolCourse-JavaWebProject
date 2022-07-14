package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.zr.service.sys.AuthService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/sys/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @RequestMapping("/selectById")
    public String selectById(@RequestParam("id") String id){
        Long idT = Long.valueOf(id);
        Auth auth = authService.selectById(idT);
        return JSON.toJSONString(AjaxResult.success("查询成功",auth));
    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("mark") String mark, @RequestParam("seq") String seq){
        Long idT = Long.valueOf(id);
        authService.update(idT, name, mark, seq);
        return JSON.toJSONString(AjaxResult.success("更新成功",null));
    }

    @RequestMapping("/create")
    public String create(@RequestParam("name") String name, @RequestParam("url") String url, @RequestParam("mark") String mark, @RequestParam("seq") Short seq,
                         @RequestParam("parent") String parent ){
//        Long pid = Long.parseLong(parent);
//        Short seqd = Short.parseShort(seq);
//        parent = parent.substring(0, parent.length() - 5);
        System.out.println(parent);
        Auth auth = new Auth();
        auth.setName(name);
        auth.setUrl(url);
        auth.setMark(mark);
        auth.setPid(Long.parseLong(parent));
        auth.setSeq(seq);
        auth.setCreateTime(new Date());
        authService.create(auth);
        return JSON.toJSONString(AjaxResult.success("插入成功",null));
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") String id){
        Long idT = Long.valueOf(id);
        authService.delete(idT);
        return JSON.toJSONString(AjaxResult.success("更新成功",null));
    }

    @RequestMapping("/list")
    public String list(){
        String res = "";
        Auth[] parents = authService.selectParents();
        Auth[] childs = authService.selectChildren();

        res = "{parent:" +
                "[";
        for(Auth a : parents){
            res += "{name:\'" + a.getName() + "\',pid:" + a.getPid() + ",id:" + a.getId() +  "},";
        }
        res = res.substring(0, res.length() - 1);
        res += "],child:[";

        for(Auth a : childs){
            res += "{name:\'" + a.getName() + "\',pid:" + a.getPid() + ",id:" + a.getId() +  "},";
        }

        res = res.substring(0, res.length() - 1);
        res += "]}";

        return JSON.toJSONString(AjaxResult.success("success", JSON.toJSON(res)));
    }
}
