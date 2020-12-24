package com.iocoder.demo01.springdemo.data;

import java.util.Date;

public class Stock {
    private long id;

    public long getId() {
        return id;
    }

    public String getStockName() {
        return stockName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    private String stockName;
    private String recordDate;
    private String unitPrice;

    public Stock(String sName, String d, String uPrice){
        this.stockName=sName;
        this.recordDate=d;
        this.unitPrice=uPrice;
        this.id=new Date().getTime();
    }

    public Stock(long id,String sName, String d, String uPrice){
        this.stockName=sName;
        this.recordDate=d;
        this.unitPrice=uPrice;
        this.id=id;
    }
}
