package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.Voucher;
import com.example.DecorEcomerceProject.Service.IVoucherService;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements IVoucherService {
    @Override
    public Voucher createVoucher(Voucher voucher){
        return voucher;
    }
}
