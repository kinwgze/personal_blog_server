package zeee.blog.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ：wz
 * @date ：Created in 2022/5/11 20:03
 * @description：
 */
public class ErrorStreamHandlerThread extends Thread{
    /**
     * 日志记录实体
     */
    private static Logger log = LoggerFactory.getLogger(ErrorStreamHandlerThread.class);

    private StringBuffer stderr;
    private Process process;

    public ErrorStreamHandlerThread(Process process) {
        stderr = new StringBuffer();
        this.process = process;
    }

    boolean getStdError() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            do {
                // 这里需要看一下ready的用法
                if (!bufferedReader.ready()) {
                    try {
                        // 如果拿到了exitValue，说明执行完毕，直接跳出循环
                        process.exitValue();
                        break;
                    } catch (IllegalThreadStateException exp1) {
                        try {
                            Thread.sleep(100L);
                        }catch (InterruptedException exp2) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } else {
                    String s = bufferedReader.readLine();
                    stderr.append(s).append("\n");
                }
            } while (true);
        } catch (IOException ioException){
            return false;
        } finally {
            // 关闭链接
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error(null, e);
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        if (process == null) {
            return;
        }

        getStdError();
    }


}
