package zeee.blog.git.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:00
 * @description：md结尾的文件
 */
@Data
@TableName("markdown_file")
public class MarkDownFile implements Serializable {

    private static final long serialVersionUID = -8672828564157030914L;
    /**
     * 数据库中的id
     */
    @TableId(type = IdType.AUTO)
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
    @TableField("category")
    private Integer category;

}
