package com.baixs.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = 8841433872811285796L;
    private Integer id;
    private String uid;
    private String uname;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private Integer taketime;
    private String deltag;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifytime;
    private List<Object> menus;
}
