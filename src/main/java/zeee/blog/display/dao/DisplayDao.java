package zeee.blog.display.dao;

import zeee.blog.display.entity.MdNamePathVO;

import java.util.List;

/**
 * @author wz
 * @date 2022/8/9
 */
public interface DisplayDao {
    /**
     * 查询文件名称列表
     * @param category category
     * @return nameList
     */
    List<MdNamePathVO> queryNameAndPathListByCategory(Integer category);

    /**
     * 通过文件路径和分类查询文件内容
     */
    String getContentByPathAndCategory(String filePath, Integer category);
}
