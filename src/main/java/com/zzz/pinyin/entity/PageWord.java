package com.zzz.pinyin.entity;

import lombok.Data;

@Data
public class PageWord {
    private int page_id;
    private String word_id;
    private int flag;
    private int line_no;
    private int order_no;
    private String question;
    private String answer;
}
