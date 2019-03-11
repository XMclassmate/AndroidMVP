package lib.xm.mvp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import lib.xm.mvp.util.log.LogUtils;


/**
 * Created by boka_lyp on 2015/10/22.
 */
public class FileUtil {

    public static final String SDPATH;

    static {
        SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
    }

    public FileUtil() {
    }

    public static boolean checkSDcard() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String readFileFromAssets(Context context, String name) {
        InputStream is = null;

        try {
            is = context.getResources().getAssets().open(name);
        } catch (Exception var4) {
            throw new RuntimeException(FileUtil.class.getName() + ".readFileFromAssets---->" + name + " not found");
        }

        return inputStream2String(is);
    }

    public static String inputStream2String(InputStream is) {
        if (null == is) {
            return null;
        } else {
            StringBuilder resultSb = null;

            try {
                BufferedReader ex = new BufferedReader(new InputStreamReader(is));
                resultSb = new StringBuilder();

                String len;
                while (null != (len = ex.readLine())) {
                    resultSb.append(len);
                }
            } catch (Exception var7) {
            } finally {
                IOUtil.closeIO(is);
            }

            return null == resultSb ? null : resultSb.toString();
        }
    }

    /**
     * 获取有SD卡个没有SD卡的文件的存储路径
     */
    public static String getFilePath() {
        if (checkSDcard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator;
        } else {
            return Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + "data" + File.separator;
        }
    }

    public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
        boolean isSuccess = false;
        if (bitmap == null) {
            return isSuccess;
        } else {
            BufferedOutputStream out = null;

            try {
                out = new BufferedOutputStream(new FileOutputStream(filePath), 8192);
                isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            } catch (FileNotFoundException var8) {
                var8.printStackTrace();
            } finally {
                IOUtil.closeIO(out);
            }

            return isSuccess;
        }
    }

    public static Bitmap getBitmapFromSd(String fileName) {
        return BitmapFactory.decodeFile(SDPATH + fileName);
    }

    public static String getSavePath(String folderName) {
        return createSDDir(folderName).getAbsolutePath();
    }

    public static File getSaveFolder(String folderName) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    public static File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public static File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        return dir;
    }

    public static File createSDDir(Context context, String dirName) {
        File dir = new File(context.getFilesDir(), dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        FileOutputStream output = null;

        try {
            createSDDir(path);
            file = createSDFile(path + File.separator + fileName);
            output = new FileOutputStream(file);
            byte[] e = new byte[input.available()];

            while (input.read(e) != -1) {
                output.write(e);
            }

            output.flush();
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return file;
    }

    public static File write2SDFromInput(String path, InputStream input) {
        File file = new File(path);
        FileOutputStream output = null;
        try {
            createSDDir(file.getParent());
            file = createSDFile(path);
            output = new FileOutputStream(file);
            byte[] e = new byte[input.available()];

            while (input.read(e) != -1) {
                output.write(e);
            }

            output.flush();
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return file;
    }

    public static String readFileFromSd(String filepath) {
        String result = "";
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(filepath);
            int e = fin.available();
            byte[] buffer = new byte[e];
            fin.read(buffer);
            result = new String(buffer, "utf-8");
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                fin.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return result;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir, String srcPath, boolean deleteSrcDir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]), srcPath, deleteSrcDir);
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        if (deleteSrcDir) {
            if (dir.getAbsolutePath().equals(srcPath)) {
                return true;
            } else {
                return dir.delete();
            }
        }
        return dir.delete();
    }

    public static void writeFile2Sd(String filepath, String content) {
        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream(filepath);
            byte[] e = content.getBytes();
            fout.write(e);
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }
            }

        }

    }

    public static void AppendFile2Sd(String filepath, String content) {
        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream(filepath, true);
            byte[] e = content.getBytes();
            fout.write(e);
        } catch (Exception var11) {
            var11.printStackTrace();
            throw new RuntimeException(var11);
        } finally {
            try {
                fout.close();
            } catch (IOException var10) {
                var10.printStackTrace();
            }

        }

    }

    public static void saveFileCache(byte[] fileData, String folderPath, String fileName) {
        File folder = new File(folderPath);
        folder.mkdirs();
        File file = new File(folderPath, fileName);
        ByteArrayInputStream is = new ByteArrayInputStream(fileData);
        FileOutputStream os = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                os = new FileOutputStream(file);
                byte[] e = new byte[1024];
                boolean len = false;

                int len1;
                while (-1 != (len1 = is.read(e))) {
                    os.write(e, 0, len1);
                }

                os.flush();
            } catch (Exception var12) {
                throw new RuntimeException(FileUtil.class.getClass().getName(), var12);
            } finally {
                IOUtil.closeIO(is, os);
            }
        }

    }

    public static final byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        } else {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            boolean rc = false;

            int rc1;
            try {
                while ((rc1 = inStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc1);
                }
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            byte[] in2b = swapStream.toByteArray();
            return in2b;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSpace(File dir) {
        try {
            StatFs e = new StatFs(dir.getPath());
            return e.getBlockSizeLong() * (long) e.getAvailableBlocks();
        } catch (Throwable var2) {
            LogUtils.e(var2.getMessage());
            return -1L;
        }
    }

    public static long getFileSize(File f) //取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static String formetFileSize(long fileS) {//转换文件大小
        if (fileS == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS);
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;

    }

    /**
     * The function is used to read public key from file.
     */
    public static String readFromFile(String fileName, boolean isAppendLineSeparator) {
        String lineSeparator = System.getProperty("line.separator");
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("Error: file " + fileName + " not found!");
            return null;
        }

        BufferedReader reader = null;
        InputStream in = null;
        try {
            in = new FileInputStream(new File(fileName));
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("-")) {
                    continue;
                } else {
                    stringBuilder.append(line);
                    if (isAppendLineSeparator) {
                        stringBuilder.append(lineSeparator);
                    }
                }

            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static byte[] readByteArrayFromFile(String fileName) {
        BufferedInputStream in = null;
        try {
            File file = new File(fileName);

            ByteArrayOutputStream out = new ByteArrayOutputStream((int) file.length());
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();
            return data;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean saveTextToFile(String content, String fileName) {
        if (fileName == null || fileName.equals("")) {
            return false;
        }
        if (content == null || content.equals("")) {
            return false;
        }
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, false);
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean saveBinaryToFile(byte[] bin, String fileName) {
        if (bin == null || bin.length == 0) {
            return false;
        }
        if (fileName == null || fileName.equals("")) {
            return false;
        }
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(fileName, false);
            outputStream.write(bin);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeIO(fileInputStream);
            IOUtil.closeIO(fileOutputStream);
        }
    }
}
