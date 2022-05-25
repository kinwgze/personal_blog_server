package zeee.blog.git.entity;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:00
 * @description：md结尾的文件
 */
public class MarkDownFile {

    /**
     * 数据库中的id
     */
    private Integer id;

    /**
     * 文件的标题
     */
    private String title;

    /**
     * 最后修改时间
     */
    private String date;

    /**
     * 源文件的地址
     */
    private String sourceFilePath;

    /**
     * 生成的HTML文件地址
     */
    private String htmlFilePath;

    /**
     * 文件内容
     */
    private String text;

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

    public String getHtmlFilePath() {
        return htmlFilePath;
    }

    public void setHtmlFilePath(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
