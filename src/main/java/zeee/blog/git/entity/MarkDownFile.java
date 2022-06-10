package zeee.blog.git.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:00
 * @description：md结尾的文件
 */
@TableName("TBL_MARKDOWN_FILE")
public class MarkDownFile {

    /**
     * 数据库中的id
     */
    @TableId
    private Integer id;

    /**
     * 文件的标题
     */
    @TableField("title")
    private String title;

    /**
     * 最后修改时间
     */
    @TableField("date")
    private String date;

    /**
     * 源文件的地址
     */
    @TableField("source_file_path")
    private String sourceFilePath;

    /**
     * 文件内容
     */
    @TableField("md_file")
    private String mdFile;

    /**
     * 生成的HTML文件地址
     */
    @TableField("html_file")
    private String htmlFile;

    /**
     * 分类
     */
    private Integer category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getMdFile() {
        return mdFile;
    }

    public void setMdFile(String mdFile) {
        this.mdFile = mdFile;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
