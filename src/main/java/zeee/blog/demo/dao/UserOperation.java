package zeee.blog.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zeee.blog.demo.entity.User;

/**
 * @author zeee
 * 2022/4/6
 */

@Mapper
public interface UserOperation {

    @Insert("INSERT INTO USER(username, level, phonenumber) VALUES (#{username}, #{level}, #{phonenumber}) ")
    void insert (@Param("username") String username,
                 @Param("level") Integer level,
                 @Param("phonenumber") String phoneNumber);

    @Insert("INSERT INTO USER(username, level, phonenumber) VALUES (#{username}, #{level}, #{phonenumber})  ")
    void insert2(User user);


    @Select("SELECT * FROM USER WHERE USERNAME=#{username}")
    User findByName(@Param("username") String username);



}
