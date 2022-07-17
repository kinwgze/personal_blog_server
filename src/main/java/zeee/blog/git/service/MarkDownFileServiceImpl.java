package zeee.blog.git.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.git.dao.MarkDownFileDO;
import zeee.blog.git.entity.MarkDownFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:26
 * @description：
 */
@Service("MarkDownFileService")
public class MarkDownFileServiceImpl implements MarkDownFileService{

    @Resource
    private MarkDownFileDO markDownFileDO;


    @Override
    public void insert(MarkDownFile markDownFile){
        markDownFileDO.insert(markDownFile);
    }

    @Override
    public void updateBySourceFilePath(MarkDownFile markDownFile) {
        QueryWrapper<MarkDownFile> wapper = new QueryWrapper<>();
        wapper.eq("source_file_path", markDownFile.getSourceFilePath());
        markDownFileDO.update(markDownFile, wapper);
    }

    @Override
    public List<MarkDownFile> selectByCategory(Integer category) {
        //首先构造QueryWrapper来进行条件的添加
        QueryWrapper<MarkDownFile> wrapper = new QueryWrapper<>();
        wrapper.eq("category",0);
        return markDownFileDO.selectList(wrapper);
    }
}
