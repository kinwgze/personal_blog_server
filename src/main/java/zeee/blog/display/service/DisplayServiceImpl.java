package zeee.blog.display.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.git.dao.MarkDownFileDO;
import zeee.blog.git.entity.MarkDownFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wz
 * @date 2022/8/9
 */
@Service("DisplayService")
public class DisplayServiceImpl implements DisplayService{

    @Resource
    private MarkDownFileDO markDownFileDO;

    /**
     * 查询文件名称列表
     *
     * @param category category
     * @return nameList
     */
    @Override
    public List<String> queryNameListByCategory(Integer category) {
        QueryWrapper<MarkDownFile> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category).select("title");
        List<MarkDownFile> files = markDownFileDO.selectList(wrapper);
        return files.stream().map(MarkDownFile::getTitle).collect(Collectors.toList());
    }
}
