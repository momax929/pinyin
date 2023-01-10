package com.zzz.pinyin.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zzz.pinyin.entity.Page;

@Mapper
public interface PageMapper {

    @Select("select * from page order by page_id desc")
    public List<Page> getAll();

    @Select("select * from page order by page_id desc limit #{start}, #{count}")
    public List<Page> getAllByPage(int start, int count);

    @Select("select * from page where page_id = #{page_id}")
    public Page getPageByPage_id(int page_id);

    @Select("select max(page_id) from page")
    public int getMaxPage_id();

    @Select("select count(1) from page")
    public int getCount();

    @Insert("insert into page(page_id, count, count_error, count_new, print_date, answer_date, answer_right, answer_error) "
            + "values(#{page_id}, #{count}, #{count_error}, #{count_new}, #{print_date}, #{answer_date}, #{answer_right}, #{answer_error})")
    public void insert(Page page);

    @Insert("insert into page_his select * from page where page_id = #{page_id}; ")
    public void insertToHis(int page_id);

    @Update("update page set answer_date = #{answer_date}, answer_right = #{answer_right}, answer_error = #{answer_error}  where page_id = #{page_id}")
    public void updateFlag(int page_id, Date answer_date, int answer_right, int answer_error);
    
    @Delete("delete from page where page_id = #{page_id}; ")
    public void delByPage_id(int page_id);

}
