package com.stock.stockapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Stocks Detail")
public class StockInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String stockPrice;
    @Transient
    private String rate;
    @Transient
    private String previousClose;
    @Transient
    private String marketCap;
    @Transient
    private String dayrange;
    @Transient
    private String timestamp;

    public StockInfo(String name, String stockPrice, String rate, String previousClose, String marketCap, String dayrange, String timestamp) {
        this.name = name;
        this.stockPrice = stockPrice;
        this.rate = rate;
        this.previousClose = previousClose;
        this.marketCap = marketCap;
        this.dayrange = dayrange;
        this.timestamp = timestamp;
    }

    public StockInfo(String name, String stockPrice) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDayrange() {
        return dayrange;
    }

    public void setDayrange(String dayrange) {
        this.dayrange = dayrange;
    }
}
