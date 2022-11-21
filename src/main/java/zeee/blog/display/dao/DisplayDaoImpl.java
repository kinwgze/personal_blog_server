package zeee.blog.display.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import zeee.blog.display.entity.MdNamePathVO;
import zeee.blog.git.mapper.MarkDownFileMapper;
import zeee.blog.git.entity.MarkDownFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wz
 * @date 2022/8/9
 */
@Service("displayService")
public class DisplayDaoImpl implements DisplayDao {

    @Resource
    private MarkDownFileMapper markDownFileMapper;

    /**
     * 查询文件名称列表
     *
     * @param category category
     * @return nameList
     */
    @Override
    public List<MdNamePathVO> queryNameAndPathListByCategory(Integer category) {
        QueryWrapper<MarkDownFile> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category).select("title", "source_file_path");
        List<MarkDownFile> files = markDownFileMapper.selectList(wrapper);
        List<MdNamePathVO> result = new ArrayList<>();
        for (MarkDownFile file : files) {
            MdNamePathVO mdNamePathVO = new MdNamePathVO();
            mdNamePathVO.setPath(file.getSourceFilePath());
            mdNamePathVO.setTitle(file.getTitle());
            result.add(mdNamePathVO);
        }
        return result;
    }

    /**
     * 通过文件路径和分类查询文件内容
     *
     * @param filePath 文件路径
     * @param category 文件内容
     */
    @Override
    public String getContentByPathAndCategory(String filePath, Integer category) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("category",category);
        map.put("source_file_path", filePath);
        List<MarkDownFile> files = markDownFileMapper.selectByMap(map);
        if (CollectionUtils.isNotEmpty(files)) {
            return files.get(0).getMdFile();
        }
        return null;
    }
}
