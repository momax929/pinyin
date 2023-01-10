package com.zzz.pinyin.entity;

import lombok.Data;

@Data
public class WordCount {
    int total_word;
    int error_word;
    int right_word;
    int print_word;
}
