package zeee.blog.git.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MarkDownFileDO {

    /**
     * 添加markDown到zeroMdFile表
     */
    @Insert("INSERT INTO zeroMdFile(title, date, sourceFilePath, htmlFilePath, text, category) " +
            "VALUES(#{title}, #{date}, #{sourceFilePath}, #{htmlFilePath}, #{text}, #{category})")
    void addZeroMdFile(@Param("title") String title,
                       @Param("date") String date,
                       @Param("sourceFilePath") String sourceFilePath,
                       @Param("htmlFilePath") String htmlFilePath,
                       @Param("text") String text,
                       @Param("category") Integer category);

    /**
     * 添加markDown到dailyMdFile表
     */
    @Insert("INSERT INTO dailyMdFile(title, date, sourceFilePath, htmlFilePath, text, category) " +
            "VALUES(#{title}, #{date}, #{sourceFilePath}, #{htmlFilePath}, #{text}, #{category})")
    void addDailyMdFile(@Param("title") String title,
                        @Param("date") String date,
                        @Param("sourceFilePath") String sourceFilePath,
                        @Param("htmlFilePath") String htmlFilePath,
                        @Param("text") String text,
                        @Param("category") Integer category);

}
