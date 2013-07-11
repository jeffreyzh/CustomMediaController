package io.github.jeffreyzh.mediacontroller.util;
/*     */
/*     */import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import android.text.TextUtils;
import android.util.Log;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */public class FileUtil
/*     */{
	/*     */private static final int COPY_SIZE = 10240;
	/*     */private static final long TIME_RETRY = 200L;
	/*     */private static final int DOWNLOAD_SIZE = 262144;

	/*     */
	/*     */public static boolean createFile(String pathname, String content)
	/*     */{
		/*     */try
		/*     */{
			/* 29 */File file = new File(pathname);
			/* 30 */if (file.exists())
				/* 31 */file.delete();
			/*     */else {
				/* 33 */file.getParentFile().mkdirs();
				/*     */}
			/* 35 */file.createNewFile();
			/* 36 */FileWriter writer = new FileWriter(file);
			/* 37 */writer.write(content);
			/* 38 */writer.close();
			/*     */} catch (Exception e) {
			/* 40 */Log.e(FileUtil.class.getSimpleName(), "create file exception", e);
			/*     */}
		/*     */
		/* 43 */return false;
		/*     */}

	/*     */
	/*     */public static File makeFile(String filepath, boolean delete) throws IOException
	/*     */{
		/* 48 */File path = new File(filepath);
		/* 49 */if (!(path.getParentFile().exists())) {
			/* 50 */path.getParentFile().mkdirs();
			/*     */}
		/* 52 */File file = new File(filepath);
		/* 53 */if (delete) {
			/* 54 */file.delete();
			/*     */}
		/* 56 */if (!(file.exists())) {
			/* 57 */file.createNewFile();
			/*     */}
		/* 59 */return file;
		/*     */}

	/*     */
	/*     */public static long download(String url, String filepath)
	/*     */{
		/* 64 */InputStream inStream = null;
		/* 65 */OutputStream outStream = null;
		/*     */try {
			/* 67 */inStream = new URL(url).openStream();
			/* 68 */if (inStream == null) {
				/*     */return -1L;
				/*     */}
			/* 71 */File file = makeFile(filepath, true);
			/* 72 */outStream = new FileOutputStream(file);
			/* 73 */byte[] buffer = new byte[262144];
			/* 74 */for (int size = 0; (size = inStream.read(buffer)) != -1;) {
				/* 75 */outStream.write(buffer, 0, size);
				/*     */}
			/* 77 */Log.i(FileUtil.class.getSimpleName(), "download file finish:" + file.getAbsolutePath() + ",from:"
					+ url);
			/* 78 */return file.length();
			/*     */} catch (Exception e) {
			/* 80 */Log.e(FileUtil.class.getSimpleName(), "download file error:" + url, e);
			/*     */} finally {
			/* 82 */close(inStream);
			/* 83 */close(outStream);
			/*     */}
		/* 85 */return -1L;
		/*     */}

	/*     */
	/*     */public static long copy(String srcFilename, String targetFilename) {
		/*     */try {
			/* 90 */return copy(new FileInputStream(srcFilename), targetFilename, true);
			/*     */} catch (Exception e) {
			/* 92 */Log.e("Error", e.getMessage());
			/*     */}
		/* 94 */return -1L;
		/*     */}

	/*     */
	/*     */public static long copy(InputStream inStream, String filename, boolean closeStream) throws IOException
	/*     */{
		/* 99 */File file = new File(filename);
		/* 100 */if (!(file.getParentFile().exists())) {
			/* 101 */file.getParentFile().mkdirs();
			/*     */}
		/*     */
		/* 104 */long size = 0L;
		/* 105 */OutputStream outStream = null;
		/*     */try {
			/* 107 */outStream = new FileOutputStream(file);
			/* 108 */byte[] buffer = new byte[10240];
			/* 109 */for (int length = 0; (length = inStream.read(buffer)) != -1;) {
				/* 110 */outStream.write(buffer, 0, length);
				/* 111 */size += length;
				/*     */}
			/*     */} finally {
			/* 114 */close(outStream);
			/* 115 */if (closeStream) {
				/* 116 */close(inStream);
				/*     */}
			/*     */}
		/*     */
		/* 120 */return size;
		/*     */}

	/*     */
	/*     */public static boolean delete(String filepath)
	/*     */{
		/* 131 */return delete(filepath, 0, 0L);
		/*     */}

	/*     */
	/*     */public static boolean delete(String filepath, int tryCount)
	/*     */{
		/* 143 */return delete(filepath, tryCount, 200L);
		/*     */}

	/*     */
	/*     */public static boolean deleteFile(String strFullFileName) {
		/* 147 */if ((strFullFileName == null) || (strFullFileName.length() <= 0)) {
			/* 148 */return false;
			/*     */}
		/* 150 */File file = new File(strFullFileName);
		/* 151 */if (file.isFile()) {
			/* 152 */return file.delete();
			/*     */}
		/* 154 */return false;
		/*     */}

	/*     */
	/*     */public static boolean delete(String filepath, int tryCount, long interval)
	/*     */{
		/* 167 */File file = new File(filepath);
		/* 168 */if (!(file.exists())) {
			/* 169 */return true;
			/*     */}
		/* 171 */if (file.delete()) {
			/* 172 */return true;
			/*     */}
		/* 174 */for (int i = 0; i < tryCount; ++i) {
			/* 175 */if (interval > 0L) {
				/*     */try {
					/* 177 */Thread.sleep(interval);
					/*     */} catch (InterruptedException e) {
					/* 179 */Log.e("FileTool.delete", "file:" + filepath, e);
					/*     */}
				/*     */}
			/* 182 */Log.v("FileTool.delete", "file:" + filepath + ",tryCount = " + i);
			/* 183 */if (file.delete()) {
				/* 184 */return true;
				/*     */}
			/*     */}
		/* 187 */return false;
		/*     */}

	/*     */
	/*     */public static void unZipToFile(String paramString1, String paramString2) throws IOException
	/*     */{
		/*     */}

	/*     */
	/*     */public static void close(InputStream inStream)
	/*     */{
		/*     */try {
			/* 197 */if (inStream != null)
				/* 198 */inStream.close();
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/*     */}
		/*     */}

	/*     */
	/*     */public static void close(OutputStream outStream) {
		/*     */try {
			/* 207 */if (outStream != null)
				/* 208 */outStream.close();
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/*     */}
		/*     */}

	/*     */
	/*     */public static void close(RandomAccessFile file) {
		/*     */try {
			/* 217 */if (file != null)
				/* 218 */file.close();
			/*     */}
		/*     */catch (Exception localException) {
			/*     */}
		/*     */}

	/*     */
	/*     */public static boolean renameFile(String strSrc, String strTo) {
		/* 225 */if ((strSrc == null) || (strSrc.length() <= 0) || (strTo == null) || (strTo.length() <= 0)) {
			/* 226 */return false;
			/*     */}
		/* 228 */File fileSrc = new File(strSrc);
		/* 229 */File fileTo = new File(strTo);
		/*     */
		/* 231 */return ((fileSrc.isFile()) && (fileSrc.renameTo(fileTo)));
		/*     */}

	/*     */
	/*     */public static boolean createMultilevelDirectory(String strPath)
	/*     */{
		/* 244 */if ((strPath == null) || (strPath.length() <= 0)) {
			/* 245 */return false;
			/*     */}
		/* 247 */File dir = null;
		/* 248 */boolean bExisted = false;
		/* 249 */boolean bSucceeded = false;
		/*     */
		/* 251 */dir = new File(strPath);
		/* 252 */bExisted = dir.exists();
		/* 253 */if (bExisted)
			/* 254 */return true;
		/*     */try
		/*     */{
			/* 257 */bSucceeded = dir.mkdirs();
			/*     */} catch (SecurityException se) {
			/* 259 */se.printStackTrace();
			/* 260 */bSucceeded = false;
			/*     */}
		/*     */
		/* 263 */return bSucceeded;
		/*     */}

	/*     */
	/*     */public static String getFileName(File file)
	/*     */{
		/* 269 */if (file == null) {
			/* 270 */return null;
			/*     */}
		/* 272 */if (file.isDirectory()) {
			/* 273 */return file.getName();
			/*     */}
		/* 275 */String name = file.getName();
		/* 276 */if (name.startsWith(".")) {
			/* 277 */return name;
			/*     */}
		/* 279 */int extIndex = name.indexOf(46);
		/* 280 */if (extIndex == -1) {
			/* 281 */return file.getName();
			/*     */}
		/* 283 */return name.substring(0, extIndex);
		/*     */}

	/*     */
	/*     */public static String getFileExt(File file)
	/*     */{
		/* 288 */if (file.isDirectory()) {
			/* 289 */return "文件夹";
			/*     */}
		/* 291 */String filename = file.getName();
		/* 292 */int extIndex = filename.lastIndexOf(46);
		/* 293 */if (extIndex == -1) {
			/* 294 */return null;
			/*     */}
		/* 296 */return filename.substring(extIndex + 1);
		/*     */}

	/*     */
	/*     */public static String getFileExt(String path)
	/*     */{
		/* 306 */if (path == null) {
			/* 307 */return null;
			/*     */}
		/* 309 */int start = path.lastIndexOf(".");
		/* 310 */if (start != -1) {
			/* 311 */return path.substring(start + 1);
			/*     */}
		/* 313 */return null;
		/*     */}

	/*     */
	/*     */public static String getFilePath(String path)
	/*     */{
		/* 326 */if (path == null) {
			/* 327 */return null;
			/*     */}
		/* 329 */int start = path.lastIndexOf("/");
		/* 330 */if (start != -1) {
			/* 331 */return path.substring(0, start);
			/*     */}
		/* 333 */return path;
		/*     */}

	/*     */
	/*     */public static String getFileNameFromPath(String path)
	/*     */{
		/* 346 */if (path == null) {
			/* 347 */return null;
			/*     */}
		/* 349 */int start = path.lastIndexOf("/");
		/* 350 */if (start != -1) {
			/* 351 */return path.substring(start + 1);
			/*     */}
		/* 353 */return path;
		/*     */}

	/*     */
	/*     */public static boolean isFileExisted(String strFullFileName)
	/*     */{
		/* 359 */if ((strFullFileName == null) || (strFullFileName.length() <= 0)) {
			/* 360 */return false;
			/*     */}
		/* 362 */File file = new File(strFullFileName);
		/* 363 */return file.exists();
		/*     */}

	/*     */
	/*     */public static boolean deleteDirectory(String dir)
	/*     */{
		/* 368 */if (!(dir.endsWith(File.separator))) {
			/* 369 */dir = dir + File.separator;
			/*     */}
		/* 371 */File dirFile = new File(dir);
		/*     */
		/* 373 */if ((!(dirFile.exists())) || (!(dirFile.isDirectory()))) {
			/* 374 */return false;
			/*     */}
		/* 376 */boolean flag = true;
		/*     */
		/* 378 */File[] files = dirFile.listFiles();
		/* 379 */for (int i = 0; i < files.length; ++i)
		/*     */{
			/* 381 */if (files[i].isFile()) {
				/* 382 */flag = deleteFile(files[i].getAbsolutePath());
				/* 383 */if (flag)
					continue;
				/* 384 */break;
				/*     */}
			/*     */
			/* 389 */flag = deleteDirectory(files[i].getAbsolutePath());
			/* 390 */if (!(flag)) {
				/*     */break;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 396 */if (!(flag)) {
			/* 397 */return false;
			/*     */}
		/*     */
		/* 402 */return (dirFile.delete());
		/*     */}

	/*     */
	/*     */public static byte[] readFile(String filename)
	/*     */{
		/* 409 */if ((TextUtils.isEmpty(filename)) || (!(isFileExisted(filename)))) {
			/* 410 */return null;
			/*     */}
		/* 412 */File file = new File(filename);
		/*     */
		/* 414 */long len = file.length();
		/* 415 */byte[] bytes = new byte[(int) len];
		/*     */
		/* 417 */BufferedInputStream bufferedInputStream = null;
		/*     */try {
			/* 419 */bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			/*     */
			/* 422 */int r = bufferedInputStream.read(bytes);
			/*     */
			/* 424 */bufferedInputStream.close();
			/*     */
			/* 426 */if (r != len)
				/* 427 */return null;
			/*     */}
		/*     */catch (FileNotFoundException e)
		/*     */{
			/* 431 */e.printStackTrace();
			/*     */}
		/*     */catch (IOException e) {
			/* 434 */e.printStackTrace();
			/*     */}
		/* 436 */return bytes;
		/*     */}

	/*     */
	/*     */public static boolean saveFile(String filePath, byte[] bytes)
	/*     */{
		/* 441 */File txtFile = new File(filePath);
		/*     */try {
			/* 443 */if (!(txtFile.exists())) {
				/* 444 */txtFile.createNewFile();
				/*     */}
			/* 446 */int length = bytes.length;
			/*     */
			/* 448 */FileOutputStream fos = new FileOutputStream(txtFile);
			/* 449 */fos.write(bytes, 0, length);
			/* 450 */fos.close();
			/*     */}
		/*     */catch (Exception e) {
			/* 453 */e.printStackTrace();
			/* 454 */return false;
			/*     */}
		/* 456 */return true;
		/*     */}

	/*     */
	/*     */public static long getFileSize(String filePath)
	/*     */{
		/* 461 */File file = new File(filePath);
		/* 462 */if (!(file.exists())) {
			/* 463 */return 0L;
			/*     */}
		/* 465 */long size = file.length();
		/* 466 */return size;
		/*     */}

	/*     */
	/*     */public static void checkAndCreateFileDir(String fileNameAndPath) {
		/* 470 */String path = getFilePath(fileNameAndPath);
		/* 471 */createMultilevelDirectory(path);
		/*     */}
	/*     */
}

/*
 * Location:
 * /JeffreyZhao/AndroidDev/SVNTaobao/tbreader_0704/libs/tbreadercommon.jar
 * Qualified Name: com.taobao.common.util.FileUtil Java Class Version: 6 (50.0)
 * JD-Core Version: 0.5.3
 */