package com.daria.bean.query;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 15:08
 */
@Data
public class BookQuery {
    private int bookId; //书的id
    private String bookName; //书名

    public BookQuery() {}

    public BookQuery(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }
}
