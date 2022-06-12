package zeee.blog.operlog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zeee.blog.operlog.entity.OperationLog;

@Mapper
public interface OperlogDO extends BaseMapper<OperationLog> {

}
