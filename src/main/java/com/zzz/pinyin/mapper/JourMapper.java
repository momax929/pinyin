package com.zzz.pinyin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zzz.pinyin.entity.Jour;
import com.zzz.pinyin.entity.PageWord;

@Mapper
public interface JourMapper {

    @Select("select count(1) from jour")
    public int getCount();

    @Select("select * from jour order by page_id")
    public List<Jour> getAll();

    @Select("select count(distinct word_id) from jour")
    public int getCountWord();

    @Select("select count(1) from jour where page_id = #{page_id} and flag = #{flag}")
    public int getCountByPage_idFlag(int page_id, int flag);

    @Select("select * from jour where page_id = #{page_id} order by order_no ")
    public List<Jour> getJourByPage_id(int page_id);

    @Select("select a.*,b.question, b.answer from jour a, word b where a.word_id = b.word_id and a.page_id = #{page_id} order by order_no ")
    public List<PageWord> getJourWord(int page_id);

    @Insert("insert into jour(page_id, word_id, flag, line_no, order_no) "
            + " values(#{page_id}, #{word_id}, #{flag}, #{line_no}, #{order_no}); ")
    public void insert(Jour jour);

    @Insert("insert into jour_his select * from jour where page_id = #{page_id}; ")
    public void insertToHis(int page_id);

    @Update("update jour set flag = #{flag} where page_id = #{page_id} and word_id = #{word_id} ;")
    public void updateFlag(int page_id, String word_id, int flag);
    
    @Delete("delete from jour where page_id = #{page_id}; ")
    public void deleteByPage_id(int page_id);
}
