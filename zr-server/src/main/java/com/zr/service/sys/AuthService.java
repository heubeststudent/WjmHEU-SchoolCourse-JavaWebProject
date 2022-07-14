package com.zr.service.sys;

import com.zr.vo.sys.Auth;

public interface AuthService {
    Auth[] selectParents();

    Auth[] selectChildren();

    Auth selectById(Long idT);

    void update(Long idT, String name, String mark, String seq);

    void delete(Long idT);

    void create(Auth auth);
}
