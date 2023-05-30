package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.Order;

import java.io.IOException;

public interface IPaymentService {
    Object createPayment(Order order) throws Exception;
    Object getResult(String vnp_TmnCode, String vnp_Amount, String vnp_BankCode, String vnp_BankTranNo, String vnp_CardType, String vnp_PayDate, String vnp_OrderInfo, String vnp_TransactionNo, String vnp_ResponseCode, String vnp_TransactionStatus, String vnp_TxnRef, String vnp_SecureHash) throws IOException;
    boolean refund(Order order) throws IOException;
}
