package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/28 -- 11:56
 */

@Data
public class CourseDO {
    private int courseId;
    private String courseName;
    private String teacherName;
    private String teacherNumber;
    private String type; //课程类别
    private int count; //选课人数
    private int countLimit; //选课的人数限制
    private int isCheck; //是否被审核，0代表未审核，1代表审核通过，2代表审核不通过
    private int isOpen; //选修课 是否开放选课


    public CourseDO() {}

    public CourseDO(int courseId, String courseName, String teacherName, String teacherNumber, String type, int count, int countLimit, int isCheck, int isOpen) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.type = type;
        this.count = count;
        this.countLimit = countLimit;
        this.isCheck = isCheck;
        this.isOpen = isOpen;
    }

}
