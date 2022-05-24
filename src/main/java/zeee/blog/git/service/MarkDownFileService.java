package zeee.blog.git.service;

/**
 * @author ：wz
 * @date ：Created in 2022/5/23 20:24
 * @description：
 */
public interface MarkDownFileService {

    /**
     * 保存MarkDown文件到ZeroMdFile
     */
    void addZeroMdFile(String title, String date, String sourceFilePath, String htmlFilePath, String text);

    /**
     * 保存MarkDown文件到数据库DailyMdFile
     */
    void addDailyMdFile(String title, String date, String sourceFilePath, String htmlFilePath, String text);
}
