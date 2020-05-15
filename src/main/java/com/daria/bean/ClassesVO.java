package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/15 -- 23:30
 */

@Data
public class ClassesVO {
    private String classId;
    private String academy; //学院
    private String major; //专业

    public ClassesVO(String classId, String academy, String major) {
        this.classId = classId;
        this.academy = academy;
        this.major = major;
    }

    public ClassesVO() {}
}
