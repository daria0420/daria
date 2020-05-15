package com.daria.bean.vo;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 15:03
 */

@Data
public class BookVO {
    private int bookId; //书的id
    private String bookName; //书名
    private String author; //作者
    private String publishingHouse; //出版社
    private String category; //类别
    private double price; //价格
    private int borrowedNumber; //借出数量
    private int remainderNumber; //剩余数量

    public BookVO() {}

    public BookVO(int bookId, String bookName, String author, String publishingHouse, String category,
                  double price, int borrowedNumber, int remainderNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.category = category;
        this.price = price;
        this.borrowedNumber = borrowedNumber;
        this.remainderNumber = remainderNumber;
    }

}
