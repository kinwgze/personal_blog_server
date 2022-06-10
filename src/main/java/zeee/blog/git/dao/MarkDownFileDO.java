package zeee.blog.git.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zeee.blog.git.entity.MarkDownFile;

@Mapper
public interface MarkDownFileDO extends BaseMapper<MarkDownFile> {

}
