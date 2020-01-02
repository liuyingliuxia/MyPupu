package me.vast.common.util;

import android.content.Context;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * zhengz
 */
public final class FileUtils {
    public static void copyFile(File oldfile, File newFile) {
        try {
            int byteread;
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldfile); //读入原文件
                FileOutputStream output = new FileOutputStream(newFile);
                byte[] buffer = new byte[4096];
                while ((byteread = inStream.read(buffer)) != -1) {
                    output.write(buffer, 0, byteread);
                }
                closeQuietly(inStream);
                closeQuietly(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        copyFile(new File(oldPath), new File(newPath));
    }

    /**
     * 删除指定文件夹
     */
    public static void clearPathFile(String cachePath, String path) {
        File cacheDir = new File(cachePath + File.separator + path);
        if (cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }
    }

    public static File createFolder(Context context, String folder) {
        File file = new File(StorageUtils.getCacheDirectory(context), folder);
        if (file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * @param context
     * @param fileName
     * @return
     */
    public static File createFile(Context context, String fileName) {
        return createFile(StorageUtils.getCacheDirectory(context), fileName);
    }

    /**
     * 创建文件夹和文件
     *
     * @param context
     * @param folder
     * @param fileName
     * @return
     */
    public static File createFile(Context context, String folder, String fileName) {
        return createFile(createFolder(context, folder), fileName);
    }

    /**
     * 创建文件
     *
     * @param dirPath
     * @param fileName
     * @return
     */
    private static File createFile(File dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }


    /**
     * 删除文件夹
     *
     * @param context
     * @param folder
     * @return
     */
    public static void deleteFolder(Context context, String folder) {
        File cacheDir = new File(StorageUtils.getCacheDirectory(context), folder);
        deleteFolder(cacheDir);
    }

    private static void deleteFolder(File cacheDir) {
        if (cacheDir.exists() && cacheDir.isDirectory()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFolder(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
    }

    public static void deleteFile(String filePath) {
        new File(filePath).delete();
    }


    /**
     * 获取文件夹大小
     *
     * @param file
     * @return long
     * @throws Exception
     */
    public static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        if (null == fileList || fileList.length == 0) {
            return size;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size += getFolderSize(fileList[i]);
            } else {
                size += fileList[i].length();
            }
        }
        return size;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */

    public static String formatFileSize(long fileS) {
//        zhengz 最新使用google提供
//        Formatter.formatFileSize(fileS);
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static String readAssetsData(Context context, String path) {
        InputStream is = null;
        String res = null;
        try {
            is = context.getAssets().open(path);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    /**
     * 拷贝文件
     * FileChannel复制文件的速度比BufferedInputStream/BufferedOutputStream复制文件的速度快了近三分之一。
     * 在复制大文件的时候更加体现出FileChannel的速度优势。而且FileChannel是多并发线程安全的。
     *
     * @param s 源文件
     * @param t 目标文件
     *          zhengz 备注不安全，部分手机会发生异常
     */
    public static void fileChannelCopy(File s, File t) {
        if (null == s || null == t) {
            return;
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fi);
            closeQuietly(fo);
            closeQuietly(in);
            closeQuietly(out);
        }
    }

    private static byte[] readBytesFromFile(File file) {
        byte[] fileData = null;
        DataInputStream dis = null;
        try {
            if (file.length() == 0) {
                return null;
            }

            fileData = new byte[(int) file.length()];
            dis = new DataInputStream(new FileInputStream(file));
            dis.readFully(fileData);
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(dis);
        }
        return fileData;
    }

    /**
     * 读取文件路径数据
     *
     * @param path
     * @return
     */
    public static byte[] readBytesFromPath(String path) {
        try {
            File f = new File(path);
            if (f.length() == 0) {
                return null;
            }
            return readBytesFromFile(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入文件路径数据
     *
     * @param filePath
     * @param bytes
     */
    public static void writeBytesByPath(String filePath, byte[] bytes) {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writeBytesByFile(file, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入文件路径数据
     *
     * @param file
     * @param bytes
     */
    private static void writeBytesByFile(File file, byte[] bytes) {
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(file);
            fops.write(bytes);
            fops.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fops);
        }
    }


    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(FileChannel channel) {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }


}
