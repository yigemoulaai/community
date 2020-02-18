package com.wtu.mapper;

import com.wtu.model.Question;
import com.wtu.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;
import sun.awt.SunHints;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modify,creator,comment_count,view_count,like_count,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public  void  insertQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> getQuestionList(@Param(value = "offset") Integer offset,
                                   @Param(value = "size") Integer size);


    @Select("select count(1) from question")
    public  Integer getCount();

    @Select("select * from question where creator=#{UserId} limit #{offset},#{size}")
    List<Question> getQuestionForMe(@Param(value = "UserId") String UserId,
                          @Param(value = "offset") Integer offset,
                          @Param(value = "size")Integer size);

    @Select("select count(1) from question where creator=#{UserId}")
    public  Integer getCountForMe(@Param(value = "UserId") String UserId);

    @Select("select * from question where id=#{id}")
    Question getQuestionById(@Param(value = "id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modify=#{gmtModify} where id=#{id}")
    void updateQuestion(Question question);
}
