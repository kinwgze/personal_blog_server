package zeee.blog.git.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.git.mapper.MarkDownFileMapper;
import zeee.blog.git.entity.MarkDownFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:26
 * @description：
 */
@Service("markDownFileService")
public class MarkDownFileDaoImpl implements MarkDownFileDao {

    @Resource
    private MarkDownFileMapper markDownFileMapper;


    @Override
    public void insert(MarkDownFile markDownFile){
        markDownFileMapper.insert(markDownFile);
    }

    @Override
    public void updateBySourceFilePath(MarkDownFile markDownFile) {
        QueryWrapper<MarkDownFile> wapper = new QueryWrapper<>();
        wapper.eq("source_file_path", markDownFile.getSourceFilePath());
        markDownFileMapper.update(markDownFile, wapper);
    }

    @Override
    public List<MarkDownFile> selectByCategory(Integer category) {
        //首先构造QueryWrapper来进行条件的添加
        QueryWrapper<MarkDownFile> wrapper = new QueryWrapper<>();
        wrapper.eq("category",0);
        return markDownFileMapper.selectList(wrapper);
    }
}
