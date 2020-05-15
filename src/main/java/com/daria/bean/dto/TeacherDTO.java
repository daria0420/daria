package com.daria.bean.dto;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 22:21
 */
@Data
public class TeacherDTO {
    private String teacherNumber;
    private String srcPassword;
    private String newPassword;

    public TeacherDTO(String teacherNumber, String srcPassword, String newPassword) {
        this.teacherNumber = teacherNumber;
        this.srcPassword = srcPassword;
        this.newPassword = newPassword;
    }

    public TeacherDTO() {}
}
