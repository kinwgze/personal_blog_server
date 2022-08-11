package zeee.blog.display.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import zeee.blog.display.entity.MdNamePathVO;
import zeee.blog.git.dao.MarkDownFileDO;
import zeee.blog.git.entity.MarkDownFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<MdNamePathVO> queryNameAndPathListByCategory(Integer category) {
        QueryWrapper<MarkDownFile> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category).select("title", "source_file_path");
        List<MarkDownFile> files = markDownFileDO.selectList(wrapper);
        List<MdNamePathVO> result = new ArrayList<>();
        for (MarkDownFile file : files) {
            MdNamePathVO mdNamePathVO = new MdNamePathVO();
            mdNamePathVO.setPath(file.getSourceFilePath());
            mdNamePathVO.setTitle(file.getTitle());
            result.add(mdNamePathVO);
        }
        return result;
    }
}
