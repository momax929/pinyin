package com.zzz.pinyin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zzz.pinyin.entity.Jour;
import com.zzz.pinyin.entity.PageWord;

@Service
public interface JourService {

    public List<Jour> getJourByPage_id(int page_id);

    public List<PageWord> getJourWord(int page_id);
}
