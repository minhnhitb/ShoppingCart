package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Config.PaymentConfig;
import com.example.DecorEcomerceProject.Entities.DTO.PaymentResultsDTO;
import com.example.DecorEcomerceProject.Entities.Enum.OrderStatus;
import com.example.DecorEcomerceProject.Entities.Enum.PaymentType;
import com.example.DecorEcomerceProject.Entities.Order;
import com.example.DecorEcomerceProject.Repositories.OrderRepository;
import com.example.DecorEcomerceProject.ResponseAPI.ResponseObject;
import com.example.DecorEcomerceProject.Service.IPaymentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final OrderRepository orderRepository;
    public PaymentServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public Object createPayment(Order order) throws IOException {
        ResponseObject responseObject = new ResponseObject();
        if (order.getPaymentType() == PaymentType.COD) {
            responseObject.setStatus("");
            responseObject.setMessage("");
            responseObject.setData(order);
            return responseObject;
        }
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        long amount = (long) (order.getAmount() * 100L);
        String vnp_TxnRef = String.valueOf(order.getId());
        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        responseObject.setStatus("");
        responseObject.setMessage(paymentUrl);
        responseObject.setData(order);
        return responseObject;
    }
    @Override
    public Object getResult(String vnp_TmnCode,
                            String vnp_Amount,
                            String vnp_BankCode,
                            String vnp_BankTranNo,
                            String vnp_CardType,
                            String vnp_PayDate,
                            String vnp_OrderInfo,
                            String vnp_TransactionNo,
                            String vnp_ResponseCode,
                            String vnp_TransactionStatus,
                            String vnp_TxnRef,
                            String vnp_SecureHash) throws IOException {
        Map<String, String> fields = new HashMap<>();
        fields.put("vnp_TmnCode", URLEncoder.encode(vnp_TmnCode, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_Amount", URLEncoder.encode(vnp_Amount, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_BankCode", URLEncoder.encode(vnp_BankCode, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_BankTranNo", URLEncoder.encode(vnp_BankTranNo, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_CardType", URLEncoder.encode(vnp_CardType, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_PayDate", URLEncoder.encode(vnp_PayDate, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_OrderInfo", URLEncoder.encode(vnp_OrderInfo, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_TransactionNo", URLEncoder.encode(vnp_TransactionNo, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_ResponseCode", URLEncoder.encode(vnp_ResponseCode, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_TransactionStatus", URLEncoder.encode(vnp_TransactionStatus, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_TxnRef", URLEncoder.encode(vnp_TxnRef, StandardCharsets.US_ASCII.toString()));

        String signValue = PaymentConfig.hashAllFields(fields);
        ResponseObject responseObject = new ResponseObject();
        Order order = orderRepository.findById(Long.valueOf(vnp_TxnRef)).get();
        PaymentResultsDTO paymentResultsDTO = new PaymentResultsDTO();
        paymentResultsDTO.setAmount(vnp_Amount);
        paymentResultsDTO.setBankCode(vnp_BankCode);
        paymentResultsDTO.setBankTranNo(vnp_BankTranNo);
        paymentResultsDTO.setOrderInfo(vnp_OrderInfo);
        paymentResultsDTO.setPayDate(vnp_PayDate);
        paymentResultsDTO.setResponseCode(vnp_ResponseCode);
        paymentResultsDTO.setTransactionNo(vnp_TransactionNo);
        paymentResultsDTO.setTransactionStatus(vnp_TransactionStatus);
        paymentResultsDTO.setTxnRef(vnp_TxnRef);
        if (signValue.equals(vnp_SecureHash)) {
            if (vnp_ResponseCode.equalsIgnoreCase("00") && vnp_TransactionStatus.equalsIgnoreCase("00")) {
                order.setStatus(OrderStatus.PAID);
                order = orderRepository.save(order);
                paymentResultsDTO.setOrder(order);
                responseObject.setStatus("Ok");
                responseObject.setMessage("Payment Success");
                responseObject.setData(paymentResultsDTO);
            }else {
                paymentResultsDTO.setOrder(order);
                responseObject.setStatus("Error");
                responseObject.setMessage("Error");
                responseObject.setData(paymentResultsDTO);
            }
            return responseObject;
        }
        responseObject.setStatus("Error");
        responseObject.setMessage("Sign not valid!");
        responseObject.setData(null);
        return responseObject;
    }
}
