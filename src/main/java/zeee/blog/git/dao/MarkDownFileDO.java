package zeee.blog.git.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import zeee.blog.git.entity.MarkDownFile;

@Mapper
public interface MarkDownFileDO {

    /**
     * 添加markDown
     */
    @Insert("INSERT INTO OPERATIONLOG(id, title, date, sourceFilePath, htmlFilePath, text) " +
            "VALUES(#{id}, #{title}, #{date}, #{sourceFilePath}, #{htmlFilePath}, #{text})")
    void addMarkDownFile(MarkDownFile markDownFile);

}
