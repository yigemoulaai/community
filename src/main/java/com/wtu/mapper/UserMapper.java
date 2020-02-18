package com.wtu.mapper;

import com.wtu.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_uri) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUri})")
    public  void insertUser(User user);



    @Select("select * from user where token=#{token}")
    User findMapper(@Param("token") String token);

    @Select("select * from user where account_id=#{creator} limit 0,1")
    User findById(String creator);

    @Update("update user set name=#{name},token=#{token},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},avatar_uri=#{avatarUri} where account_id=#{accountId}")
    void update(User user);
}
