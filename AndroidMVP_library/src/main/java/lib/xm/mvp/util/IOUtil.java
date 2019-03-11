package lib.xm.mvp.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by boka_lyp on 2015/10/22.
 */
public class IOUtil {

    private IOUtil() {
    }

    public static void closeQuietly(Closeable closeables) {
        if (closeables != null) {
            try {
                closeables.close();
            } catch (Throwable var2) {
            }
        }

    }

    public static void closeIO(Closeable... closeables) {
        if (null != closeables && closeables.length > 0) {
            Closeable[] var1 = closeables;
            int var2 = closeables.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Closeable cb = var1[var3];

                try {
                    if (null != cb) {
                        cb.close();
                    }
                } catch (IOException var6) {
                    throw new RuntimeException(IOUtil.class.getClass().getName(), var6);
                }
            }

        }
    }

}
