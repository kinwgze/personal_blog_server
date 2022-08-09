package zeee.blog.display.service;

import java.util.List;

/**
 * @author wz
 * @date 2022/8/9
 */
public interface DisplayService {
    /**
     * 查询文件名称列表
     * @param category category
     * @return nameList
     */
    List<String> queryNameListByCategory(Integer category);
}
