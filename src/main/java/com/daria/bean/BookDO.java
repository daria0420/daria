package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 17:11
 */
@Data
public class BookDO {
    private int bookId; //书的id
    private String bookName; //书名
    private String author; //作者
    private String publishingHouse; //出版社
    private String category; //类别
    private double price; //价格
    private String putTime; //上架时间
    private int borrowedNumber; //借出数量
    private int remainderNumber; //剩余数量

    public BookDO(int bookId, String bookName, String author, String publishingHouse, String category, double price, String putTime,
                int borrowedNumber, int remainderNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.category = category;
        this.price = price;
        this.putTime = putTime;
        this.borrowedNumber = borrowedNumber;
        this.remainderNumber = remainderNumber;
    }
    public BookDO() {

    }


}
