package zeee.blog.utils.commandutil;

import java.io.File;

/**
 * @author wz
 * @date 2022/8/24
 */
public interface CommandUtil {

    /**
     * 在本地运行一个命令
     * @param command 命令行
     * @param evps 环境
     * @param dir  目录
     * @param timeOutMax 最大的超时时长，单位ms
     * @return 结果
     */
    String runCommandThrowException(String[] command, String[] evps, File dir, int timeOutMax);

}
