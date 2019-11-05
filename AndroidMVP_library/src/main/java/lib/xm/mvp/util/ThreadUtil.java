package lib.xm.mvp.util;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XMclassmate
 * @date 2018/1/29
 */

public class ThreadUtil {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int THREAD_COUNT = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    private static final Executor M_THREAD_POOL_EXECUTOR;

    private static final BlockingDeque<Runnable> M_BLOCKING_DEQUE = new LinkedBlockingDeque<>(128);

    private static final ThreadFactory M_THREAD_FAFACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "AppThread #" + mCount.getAndIncrement());
        }
    };

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(THREAD_COUNT, CPU_COUNT * 2 + 1, 30, TimeUnit.SECONDS, M_BLOCKING_DEQUE, M_THREAD_FAFACTORY);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        M_THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    /**
     * 调用线程池执行runable
     *
     * @param runnable
     */
    public static synchronized void start(Runnable runnable) {
        M_THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public static Executor getmThreadPoolExecutor() {
        return M_THREAD_POOL_EXECUTOR;
    }
}
