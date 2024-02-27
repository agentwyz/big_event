package com.fnmain.mapper;

import com.fnmain.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);


    @Update("update article set title=#{title}, content=#{content}, cover_img=#{coverImg}, state=#{state}, " +
            "category_id=#{categoryId}, update_time=now() where id=#{id}")
    void update(Article article);

    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    @Delete("delete from article where id=#{id}")
    void delete(Integer id);


    List<Article> list(@Param("userId") Integer userId, @Param("categoryId") Integer categoryId, @Param("state") String state);
}
