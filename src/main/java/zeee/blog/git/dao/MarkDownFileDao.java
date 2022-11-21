package zeee.blog.git.dao;

import zeee.blog.git.entity.MarkDownFile;

import java.util.List;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:24
 * @description：
 */
public interface MarkDownFileDao {

    /**
     * 保存MarkDown文件到markDownFile
     */
    void insert(MarkDownFile markDownFile);

    /**
     * update
     */
    void updateBySourceFilePath(MarkDownFile markDownFile);

    /**
     * 根据category查询
     */
    List<MarkDownFile> selectByCategory(Integer category);

}
