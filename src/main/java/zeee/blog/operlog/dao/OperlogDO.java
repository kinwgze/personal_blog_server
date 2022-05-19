package zeee.blog.operlog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import zeee.blog.operlog.entity.OperationLog;

@Mapper
public interface OperlogDO {

    /**
     * 添加日志
     */
    @Insert("INSERT INTO OPERATIONLOG(id, loginName, userName, operTime, address, category, description, result, failureReason) " +
            "VALUES(#{id}, #{loginName}, #{userName}, #{operTime}, #{address}, #{category}, #{description}, #{result}, #{failureReason})")
    void addLog(OperationLog operationLog);

}
