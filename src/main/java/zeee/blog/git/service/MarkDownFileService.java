package zeee.blog.git.service;

import zeee.blog.git.entity.MarkDownFile;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:24
 * @description：
 */
public interface MarkDownFileService {

    /**
     * 保存MarkDown文件到数据库
     * @param markDownFile 文件
     */
    public void addMarkDownFile(MarkDownFile markDownFile);
}
