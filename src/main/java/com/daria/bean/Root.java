package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 21:45
 */

@Data
public class Root {
    private int rid; //管理员id
    private String rootName;
    private String password;

    public Root(int rid, String rootName, String password) {
        this.rid = rid;
        this.rootName = rootName;
        this.password = password;
    }

    public Root() {

    }

}
