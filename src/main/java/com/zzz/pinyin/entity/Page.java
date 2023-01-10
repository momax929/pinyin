package com.zzz.pinyin.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Page {
    private String page_id;
    private int count;
    private int count_error;
    private int count_new;
    private Date print_date;
    private Date answer_date;
    private int answer_right;
    private int answer_error;
}
