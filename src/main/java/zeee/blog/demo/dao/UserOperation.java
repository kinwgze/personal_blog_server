package zeee.blog.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeee.blog.demo.entity.User;

/**
 * @author zeee
 * 2022/4/6
 */

@Mapper
public interface UserOperation {

    @Insert("INSERT INTO USER(username, level, phonenumber) VALUES (#{username}, #{level}, #{phonenumber}) ")
    int insert (@Param("username") String username,
                 @Param("level") Integer level,
                 @Param("phonenumber") String phoneNumber);

    @Insert("INSERT INTO USER(username, level, phonenumber) VALUES (#{username}, #{level}, #{phonenumber})  ")
    int insert2(User user);


    User findByName(@Param("username") String username);



}
