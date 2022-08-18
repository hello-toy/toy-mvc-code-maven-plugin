package org.hellotoy.maven.plugins.code.generation.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileUtil {

	public static interface FileProcessor {
	   default void process(String content) {
		   
	   }
	   default void file(File file) {
		   
	   }
	}
	public static interface FileFilter{
		boolean accept(File file);
	}
	
	
    public static void readFile(File file, FileProcessor tFileProcessor) throws IOException {
        if (file == null) {

        } else if (file.isFile()) {
        	tFileProcessor.file(file);
            //遍历文件
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    // do something with line
                    tFileProcessor.process(line);
                }
            } finally {
                LineIterator.closeQuietly(it);
            }

        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    readFile(f, tFileProcessor);
                }
            }
        }
    }
    /**
     * 复制文件夹
     *
     * @param resource 源路径
     * @param target   目标路径
     */
    public static void copyFolder(String resource, String target,FileFilter filter) throws Exception {

        File resourceFile = new File(resource);
        if (!resourceFile.exists()) {
            throw new Exception("源目标路径：[" + resource + "] 不存在...");
        }
        File targetFile = new File(target);
        if (!targetFile.exists()) {
            //throw new Exception("存放的目标路径：[" + target + "] 不存在...");
        	targetFile.mkdirs();
        }

        // 获取源文件夹下的文件夹或文件
        File[] resourceFiles = resourceFile.listFiles();

        for (File file : resourceFiles) {
        	if(!filter.accept(file)) {
        		continue;
        	}
            File file1 = new File(targetFile.getAbsolutePath() + File.separator + resourceFile.getName());
            // 复制文件
            if (file.isFile()) {
                // 在 目标文件夹（B） 中 新建 源文件夹（A），然后将文件复制到 A 中
                // 这样 在 B 中 就存在 A
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                File targetFile1 = new File(file1.getAbsolutePath() + File.separator + file.getName());
                copyFile(file, targetFile1);
            }
            // 复制文件夹
            if (file.isDirectory()) {// 复制源文件夹
                String dir1 = file.getAbsolutePath();
                // 目的文件夹
                String dir2 = file1.getAbsolutePath();
                copyFolder(dir1, dir2,filter);
            }
        }

    }

    /**
     * 复制文件
     *
     * @param resource
     * @param target
     */
    public static void copyFile(File resource, File target) throws Exception {
        // 输入流 --> 从一个目标读取数据
        // 输出流 --> 向一个目标写入数据

        long start = System.currentTimeMillis();

        // 文件输入流并进行缓冲
        FileInputStream inputStream = new FileInputStream(resource);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // 文件输出流并进行缓冲
        FileOutputStream outputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // 缓冲数组
        // 大文件 可将 1024 * 2 改大一些，但是 并不是越大就越快
        byte[] bytes = new byte[1024 * 2];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, len);
        }
        // 刷新输出缓冲流
        bufferedOutputStream.flush();
        //关闭流
        bufferedInputStream.close();
        bufferedOutputStream.close();
        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

    }
}
