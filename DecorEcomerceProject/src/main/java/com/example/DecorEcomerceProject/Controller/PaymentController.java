package com.example.DecorEcomerceProject.Controller;

import com.example.DecorEcomerceProject.Config.PaymentConfig;
import com.example.DecorEcomerceProject.Entities.DTO.PaymentDTO;
import com.example.DecorEcomerceProject.Entities.DTO.PaymentResultsDTO;
import com.example.DecorEcomerceProject.ResponseAPI.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) throws IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        long amount = paymentDTO.getAmount() * 100L;
        String bankCode = "";

        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_IpAddr = paymentDTO.getIpAddr();
        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);

        String locate = paymentDTO.getLocate();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

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
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus("");
        responseObject.setMessage("");
        responseObject.setData(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
    @GetMapping ("/results")
    public ResponseEntity<?> transaction_results (
            @RequestParam(value = "vnp_TmnCode") String vnp_TmnCode,
            @RequestParam(value = "vnp_Amount") String vnp_Amount,
            @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
            @RequestParam(value = "vnp_BankTranNo") String vnp_BankTranNo,
            @RequestParam(value = "vnp_CardType") String vnp_CardType,
            @RequestParam(value = "vnp_PayDate") String vnp_PayDate,
            @RequestParam(value = "vnp_OrderInfo") String vnp_OrderInfo,
            @RequestParam(value = "vnp_TransactionNo") String vnp_TransactionNo,
            @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam(value = "vnp_TransactionStatus") String vnp_TransactionStatus,
            @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef,
            @RequestParam(value = "vnp_SecureHash") String vnp_SecureHash
    ) throws UnsupportedEncodingException {
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
        if (signValue.equals(vnp_SecureHash)){
            signValue = "";
        }
        PaymentResultsDTO paymentResultsDTO = new PaymentResultsDTO();
        paymentResultsDTO.setAmount(vnp_Amount);
        paymentResultsDTO.setBankCode(vnp_BankCode);
        paymentResultsDTO.setBankTranNo(vnp_BankTranNo);
        paymentResultsDTO.setOrderInfo(vnp_OrderInfo);
        paymentResultsDTO.setPayDate(vnp_PayDate);
        paymentResultsDTO.setResponseCode(vnp_ResponseCode);
        paymentResultsDTO.setTransactionNo(vnp_TransactionNo);
        paymentResultsDTO.setTransactionStatus(vnp_TransactionStatus);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus("");
        responseObject.setMessage("");
        responseObject.setData(paymentResultsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}
