package lib.xm.mvp.util;


import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2018/5/17.
 */

public class SSLUtil {

    public static SSLSocketFactory getSocketFactory() {
        List<String> certificateFileName = new ArrayList<>();
        try {
            for (String fileName : AbstractApplication.getAppContext().getAssets().list("")) {
                if (fileName.endsWith(".cer")) {
                    certificateFileName.add(fileName);
                    LogUtils.e("fileName1111=" + fileName);
                }
            }
            LogUtils.e("app内一共预埋了1111" + certificateFileName.size() + "个证书!");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int i = 0;
            for (String fileName : certificateFileName) {
                InputStream is = AbstractApplication.getAppContext().getResources().getAssets().open(fileName);
                keyStore.setCertificateEntry(i++ + "", certificateFactory.generateCertificate(is));
                if (is != null) {
                    is.close();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static X509TrustManager getTrustManger() {
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }
}
