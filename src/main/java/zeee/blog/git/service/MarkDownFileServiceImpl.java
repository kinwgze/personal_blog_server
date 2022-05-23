package zeee.blog.git.service;

import org.springframework.stereotype.Service;
import zeee.blog.git.entity.MarkDownFile;
import zeee.blog.git.dao.MarkDownFileDO;

import javax.annotation.Resource;

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
    public void addMarkDownFile(MarkDownFile markDownFile) {
        markDownFileDO.addMarkDownFile(markDownFile);
    }
}
