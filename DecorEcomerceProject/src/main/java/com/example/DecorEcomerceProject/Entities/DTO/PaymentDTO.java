package com.example.DecorEcomerceProject.Entities.DTO;

import lombok.Data;

@Data
public class PaymentDTO {
    private int amount;
    private String IpAddr;
    private String locate;
    public String getIpAddr() {
        return IpAddr;
    }
    public void setIpAddr(String ipAddr) {
        IpAddr = ipAddr;
    }
    public String getLocate() {
        return locate;
    }
    public void setLocate(String locate) {
        this.locate = locate;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
