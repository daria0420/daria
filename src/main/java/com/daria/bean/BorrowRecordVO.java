package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/15 -- 23:02
 */
@Data
public class BorrowRecordVO {
    private String bookName;
    private int bookId;
    private String borrowerType;
    private String borrowerName;
    private String borrowerNumber;
    private String borrowTime;

    public BorrowRecordVO(String bookName, int bookId, String borrowerType, String borrowerName, String borrowerNumber, String borrowTime) {
        this.bookName = bookName;
        this.bookId = bookId;
        this.borrowerType = borrowerType;
        this.borrowerName = borrowerName;
        this.borrowerNumber = borrowerNumber;
        this.borrowTime = borrowTime;
    }

    public BorrowRecordVO() {}
}
