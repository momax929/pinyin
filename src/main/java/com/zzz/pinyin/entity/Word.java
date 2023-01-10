package com.zzz.pinyin.entity;

import lombok.Data;

@Data
public class Word {
    private String word_id;
    private String question;
    private String lession;
    private String answer;
    private int flag;
    private int printed;
}
