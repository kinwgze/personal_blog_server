package zeee.blog.git.service;

import org.springframework.stereotype.Service;
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
    public void addZeroMdFile(String title, String date, String sourceFilePath, String htmlFilePath, String text, Integer category) {
        markDownFileDO.addZeroMdFile(title, date, sourceFilePath, htmlFilePath, text, category);
    }

    @Override
    public void addDailyMdFile(String title, String date, String sourceFilePath, String htmlFilePath, String text, Integer category) {
        markDownFileDO.addDailyMdFile(title, date, sourceFilePath, htmlFilePath, text, category);
    }
}
