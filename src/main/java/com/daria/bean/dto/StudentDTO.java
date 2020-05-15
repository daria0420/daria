package com.daria.bean.dto;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 22:10
 */
@Data
public class StudentDTO {
    private String studentNumber;
    private String srcPassword;
    private String newPassword;

    public StudentDTO(String studentNumber, String srcPassword, String newPassword) {
        this.studentNumber = studentNumber;
        this.srcPassword = srcPassword;
        this.newPassword = newPassword;
    }

    public StudentDTO() {}
}
