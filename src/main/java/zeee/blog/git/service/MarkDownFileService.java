package zeee.blog.git.service;

import zeee.blog.git.entity.MarkDownFile;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:24
 * @description：
 */
public interface MarkDownFileService {

    /**
     * 保存MarkDown文件到markDownFile
     */
    void insert(MarkDownFile markDownFile);

}
