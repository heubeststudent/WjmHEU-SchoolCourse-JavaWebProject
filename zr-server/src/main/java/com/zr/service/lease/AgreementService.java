package com.zr.service.lease;

import com.zr.vo.lease.Agreement;

import java.util.List;

public interface AgreementService {
    List<Agreement> list(Agreement agreement);

    void add(Agreement agreement);

    void edit(Agreement agreement);

    void delAllName(String name);
}
