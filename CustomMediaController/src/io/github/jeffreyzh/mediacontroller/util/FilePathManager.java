package io.github.jeffreyzh.mediacontroller.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public final class FilePathManager {
	private Context mContext;
	public static final String ROOT_FILE = "/AMediaController/";
	public static final String BOOKS_FILE = "/downloads/books/";
	public static final String LICENCES_FILE = "/downloads/licences/";
	/**
	 * 顶层目录路径
	 */
	public static final String FILE_DIRECTORY = ROOT_FILE;
	/**
	 * 书封面存储路径
	 */
	public static final String COVER_FILE_DIR = "cover/";
	/**
	 * 分享路径
	 */
	public static final String SHARE_FILE_DIR = "share/";
	/**
	 * 日志路径
	 */
	public static final String LOG_FILE_DIR = "log/";
	/**
	 * 缓存等文件路径
	 */
	public static final String CACHE_FILE_DIR = "cache/";
	/**
	 * 字体路径
	 */
	public static final String FONT_FILE_DIR = "font/";
	/**
	 * 皮肤模板路径
	 */
	public static final String TEMPLATE_FILE_DIR = "template/";
	/**
	 * 其他资源路径
	 */
	public static final String RESOURCE_FILE_DIR = "resource/";

	private FilePathManager() {

	}

	public static FilePathManager getInstance() {
		return SingleInstance.sInstance;
	}

	public void destroy() {
		mContext = null;
	}

	public void init(Context context) {
		mContext = context;
	}

	/**
	 * 获取存储根目录
	 * 
	 * @return
	 */
	public String getFileDirectory() {
		if (isSdcardAvailable()) {
			return getSdcardPath() + ROOT_FILE;
		}
		return "";
	}

	public static boolean isSdcardAvailable() {
		if (isRealSdcardAvailable()) {
			return true;
		}

		return (new File("/mnt/sdcard2").exists());
	}

	public static String getSdcardPath() {
		if (isRealSdcardAvailable()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		if (new File("/mnt/sdcard2").exists()) {
			return "/mnt/sdcard2";
		}
		return null;
	}

	private static boolean isRealSdcardAvailable() {
		return "mounted".equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取缩略图目录
	 * 
	 * @return
	 */
	public String getCoverDirectory() {
		return getFileDirectory() + COVER_FILE_DIR;
	}

	/**
	 * 获取分享目录
	 * 
	 * @return
	 */
	public String getShareDirectory() {
		return getFileDirectory() + SHARE_FILE_DIR;
	}

	/**
	 * 获取Log目录
	 * 
	 * @return
	 */
	public String getLogDirectory() {
		return getFileDirectory() + LOG_FILE_DIR;
	}

	/**
	 * 获取字体路径
	 * 
	 * @return
	 */
	public String getFontDirectory() {
		return getFileDirectory() + FONT_FILE_DIR;
	}

	/**
	 * 皮肤模板路径
	 * 
	 * @return
	 */
	public String getTemplateDirectory() {
		return getFileDirectory() + TEMPLATE_FILE_DIR;
	}

	/**
	 * 其他资源路径
	 * 
	 * @return
	 */
	public String getResourceDirectory() {
		return getFileDirectory() + RESOURCE_FILE_DIR;
	}

	/**
	 * 获取缓存等文件的路径
	 * 
	 * @return
	 */
	public String getCacheDirectory() {
		return getFileDirectory() + CACHE_FILE_DIR;
	}

	private static class SingleInstance {
		private static FilePathManager sInstance = new FilePathManager();
	}
}
