package zeee.blog.operlog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperlogDO {

    /**
     * 添加日志
     */
    @Insert("INSERT INTO operlog(loginName, userName, operTime, address, category, description, result, failureReason) " +
            "VALUES(#{loginName}, #{userName}, #{operTime}, #{address}, #{category}, #{description}, #{result}, #{failureReason})")
    void addLog(@Param("loginName") String loginName,
                @Param("userName") String userName,
                @Param("operTime") String operTime,
                @Param("address") String address,
                @Param("category") Integer category,
                @Param("description") String description,
                @Param("result") Integer result,
                @Param("failureReason") String failureReason);

}
