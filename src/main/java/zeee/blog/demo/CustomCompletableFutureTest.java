package zeee.blog.demo;

import io.swagger.models.auth.In;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import zeee.blog.common.loghttp.LogHttp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author zeeew
 * @Date 2023/3/23 17:25
 * @Description
 */
public class CustomCompletableFutureTest {

    private static void run() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        // 自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                new CustomizableThreadFactory("custom-thread-"),
                new ThreadPoolExecutor.AbortPolicy());


        long start = System.currentTimeMillis();
        CompletableFuture.allOf(list.stream().map(num ->
                CompletableFuture.runAsync(() -> sleep(num), executor)
                ).toArray(CompletableFuture[]::new)).join();
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void sleep(Integer i) {
        System.out.println(Thread.currentThread() + "======" + i);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }
}
