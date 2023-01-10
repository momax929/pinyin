package com.zzz.pinyin.service;

import java.util.List;

import com.zzz.pinyin.entity.Word;
import com.zzz.pinyin.entity.WordCount;

public interface WordService {
    List<Word> getAll();

    void initDict();

    WordCount getCount();

    int getCountByFlag(int flag);
}
