package zeee.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zeee.blog.common.ErrorStreamHandlerThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ：wz
 * @date ：Created in 2022/5/11 19:53
 * @description：工具类
 */
public class FuncUtil {

    private static Logger log = LoggerFactory.getLogger(FuncUtil.class);

    /**
     * 在本地运行一个命令
     * @param command 命令行
     * @param evps 环境
     * @param dir  目录
     * @param timeOutMax 最大的超时时长，单位ms
     * @return 结果
     */
    public static String runCommandThrowException(String[] command, String[] evps, File dir, int timeOutMax) {
        String outString = null;
        // 创建一个新的进程
        BufferedReader outResult = null;
        Process cmProcess = null;
        StringBuffer outBuf = new StringBuffer();
        ErrorStreamHandlerThread errorStreamHandlerThread = null;
        try {
//            if (command.length > 2 && "-c".equals(command[1])) {
//                command[2] = "sudo -E" + command[2];
//            }
            cmProcess = Runtime.getRuntime().exec(command, evps, dir);
            outResult = new BufferedReader(new InputStreamReader(cmProcess.getInputStream()));
            errorStreamHandlerThread = new ErrorStreamHandlerThread(cmProcess);
            errorStreamHandlerThread.start();
            int exitValue = 1;
            int timeOut = 0;
            // 在timeOutMax范围内
            while (timeOut <= timeOutMax) {
                if (outResult.ready()) {
                    String strTmp = outResult.readLine();
                    while (strTmp != null && strTmp.trim().endsWith("")) {
                        outBuf.append(strTmp).append("\n");
                        strTmp= outResult.readLine();
                    }
                }

                try {
                    exitValue = cmProcess.exitValue();
                } catch (IllegalThreadStateException ex) {
                    Thread.sleep(100);
                    timeOut += 100;
                    continue;
                } catch (Exception e) {
                    timeOut = timeOutMax + 1;
                    log.error(null, e);
                }
                break;
            }

            outString = outBuf.toString();
            if (outResult != null) {
                outResult.close();
            }

            try {
                if (timeOut > timeOutMax || exitValue != 0) {
                    cmProcess.destroy();
                } else {
                    cmProcess.exitValue();
                }
            } catch (Exception e) {
                log.error(null, e);
            }
        } catch (IOException e) {
            log.error(null, e);
            try {
                if (null != cmProcess) {
                    cmProcess.destroy();
                }
            } catch (Exception ex) {
                log.error(null, ex);
            }
            // throw new Exception(ErrorCodes.UNKNOWN_ERROR);
        } catch (InterruptedException ie) {
            log.error(null, ie);
            // throw new Exception(ErrorCodes.UNKNOWN_ERROR);
        } finally {
            try {
                errorStreamHandlerThread.interrupt();
                // 等待线程停止运行
                try {
                    errorStreamHandlerThread.join();
                } catch (InterruptedException ine) {
                    log.error(null, ine);
                }
                errorStreamHandlerThread = null;

                if (outResult != null) {
                    outResult.close();
                }
            } catch (Exception e) {
                log.error(null, e);
            }
        }

        return outString;
    }
}
