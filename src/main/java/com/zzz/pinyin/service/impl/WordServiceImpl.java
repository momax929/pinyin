package com.zzz.pinyin.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzz.pinyin.entity.Word;
import com.zzz.pinyin.entity.WordCount;
import com.zzz.pinyin.mapper.WordMapper;
import com.zzz.pinyin.service.WordService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper mapper;
    
    @Override
    public List<Word> getAll() {
        return mapper.getAll();
    }

    @Override
    public void initDict() {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("d:\\temp\\pinyin.txt"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\|");
                if (lines.length >= 3) {
                    String id = lines[0].trim();
                    int lession = Integer.valueOf(lines[2].trim());
                    if (lession >= 60200)
                        continue;

                    if (mapper.getWordByWord_id(id) == null) {
                        // 看见|kàn jiàn|20101
                        Word word = new Word();
                        word.setWord_id(lines[0].trim());
                        word.setAnswer(lines[0].trim());
                        word.setLession(lines[2].trim());
                        word.setQuestion(lines[1].trim());
                        word.setPrinted(0);
                        word.setFlag(0);
                        mapper.insert(word);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            log.error("读取初始化字典失败！");
            e.printStackTrace();
        }
    }

    @Override
    public WordCount getCount() {
        WordCount wc = new WordCount();
        wc.setTotal_word(mapper.getCount());
        wc.setError_word(mapper.getCountByFlag(2));
        wc.setRight_word(mapper.getCountByFlag(1));
        wc.setPrint_word(mapper.getCountByPrinted(1));
        return wc;
    }

    @Override
    public int getCountByFlag(int flag) {
        return mapper.getCountByFlag(flag);
    }

    
}
