package com.dahuangit.util.net.ftp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.Validate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

/**
 * ftp工具类<br>
 * 
 * @author 黄仁良
 * 
 *         创建时间2012-8-8上午10:08:59
 */
public class FTPHelper {
	private static Logger logger = Logger.getLogger(FTPHelper.class);

	/** 重试次初始值 */
	private int RETRYTIMES_INIT_VALUE = 3;

	/** 重试次数 */
	private int retryTimes = RETRYTIMES_INIT_VALUE;

	/** 每次操作失败之后沉睡多少秒之后才进行重试 */
	private int sleepTimes = 2 * 1000;

	/** 默认超时时间 */
	private int DEFAULT_TIMEOUT = 30 * 1000;

	/** ftpClient对象 */
	private FTPClient ftpClient = new FTPClient();

	/** ftp主机地址 */
	private String ftpHost;

	/** ftp用户名 */
	private String ftpUserName;

	/** ftp用户对应的密码 */
	private String ftpPassword;

	/** ftp服务器端口 */
	private int ftpPort;

	/**
	 * 构造方法
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 * @throws Exception
	 */
	public FTPHelper(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) throws Exception {
		logger.info("正在初始化FTPClient，ftpHost=" + ftpHost + ", ftpUserName=" + ftpUserName + ", ftpPassword="
				+ ftpPassword + ", ftpPort=" + ftpPort + "  ......");

		Validate.notNull(ftpHost, "FTP主机服务器不能为空");
		Validate.notNull(ftpUserName, "FTP登录用户名不能为空");
		Validate.notNull(ftpPort, " FTP端口不能为空");

		this.ftpHost = ftpHost;
		this.ftpUserName = ftpUserName;
		this.ftpPassword = ftpPassword;
		this.ftpPort = ftpPort;

		getConnect();
	}

	/**
	 * 连接ftp
	 * 
	 * @throws Exception
	 */
	private void getConnect() throws Exception {
		this.close();

		boolean success = false;
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.connect(ftpHost);
			ftpClient.login(ftpUserName, ftpPassword);
			ftpClient.setDefaultPort(ftpPort);

			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode(); // 用被动模式传输

			ftpClient.setDefaultTimeout(DEFAULT_TIMEOUT);// 设置默认超时时间

			this.retryTimes = RETRYTIMES_INIT_VALUE;
			success = true;
		} catch (Exception e) {
			logger.error("初始化FTPClient出错" + e.getStackTrace(), e);
		} finally {
			// 尝试重试次数
			if (!success && this.retryTimes > 0) {
				Thread.sleep(sleepTimes);

				logger.info("初始化失败，正在重新初始化FTPClient，ftpHost=" + ftpHost + ", ftpUserName=" + ftpUserName
						+ ", ftpPassword=" + ftpPassword + ", ftpPort=" + ftpPort + "  ...... 还有" + retryTimes
						+ "次重试机会，一共有" + RETRYTIMES_INIT_VALUE + "次重试机会");

				retryTimes--;

				getConnect();
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param path
	 *            ftp上传路径
	 * @param remoteFileName
	 *            上传至ftp后的文件名
	 * @param localFilePathAndName
	 *            本地文件路径和文件名称,例如:c:/aa.txt
	 * @throws Exception
	 */
	public void uploadFile(String path, String remoteFileName, String localFilePathAndName) throws Exception {
		logger.info("正在上传文件,path=" + path + ", remoteFileName=" + remoteFileName + ",localFilePathAndName="
				+ localFilePathAndName + " ......");

		Validate.notNull(path, "ftp上传路径");
		Validate.notNull(remoteFileName, "上传至ftp后的文件名不能为空");
		Validate.notNull(localFilePathAndName, "本地文件路径和文件名称不能为空");

		try {
			path = replaceAll(path);
			changeDirectory(path);

			boolean b = ftpClient.storeFile(remoteFileName, new FileInputStream(new File(localFilePathAndName)));
			if (!b) {
				throw new FTPException("ftp上传文件出错,ftpClient.storeFile返回false");
			}
		} catch (Exception e) {
			logger.error("上传文件失败," + e.getStackTrace(), e);
			throw new FTPException("ftp上传文件出错", e);
		}
	}

	/**
	 * 将文件流写入远程ftp为文件
	 * 
	 * @param path
	 *            ftp上传路径
	 * @param remoteFileName
	 *            上传至ftp后的文件名
	 * @param inputStream
	 *            文件流
	 */
	public void writeInputStreamToFTP(String path, String remoteFileName, InputStream inputStream) {
		logger.info("正在上传文件,path=" + path + ", remoteFileName=" + remoteFileName);

		Validate.notNull(path, "ftp上传路径");
		Validate.notNull(remoteFileName, "上传至ftp后的文件名不能为空");
		Validate.notNull(inputStream, "文件流不能为空");

		try {
			path = replaceAll(path);
			changeDirectory(path);

			boolean b = ftpClient.storeFile(remoteFileName, inputStream);
			if (!b) {
				throw new FTPException("ftp上传文件出错,ftpClient.storeFile返回false");
			}
			inputStream.close();
		} catch (Exception e) {
			logger.error("将文件流写入远程ftp为文件", e);
			throw new FTPException("将文件流写入远程ftp为文件", e);
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将文件流写入远程ftp为文件
	 * 
	 * @param path
	 *            ftp上传路径和名称
	 * @param inputStream
	 *            文件流
	 */
	public void writeInputStreamToFTP(String remotePathAndName, InputStream inputStream) {
		logger.info("正在上传文件,remotePathAndName=" + remotePathAndName);

		Validate.notNull(remotePathAndName, "ftp上传路径和名称不能为空");

		remotePathAndName = replaceAll(remotePathAndName);
		String path = remotePathAndName.substring(0, remotePathAndName.lastIndexOf(File.separator) + 1);
		String fileName = remotePathAndName.substring(remotePathAndName.lastIndexOf(File.separator) + 1);

		try {
			this.makeDirectory(path);
			changeDirectory(path);

			boolean b = ftpClient.storeFile(fileName, inputStream);
			if (!b) {
				throw new FTPException("ftp上传文件出错,ftpClient.storeFile返回false");
			}
			inputStream.close();
		} catch (Exception e) {
			logger.error("将文件流写入远程ftp为文件", e);
			throw new FTPException("将文件流写入远程ftp为文件", e);
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从远程的一个目录往远程的另一个目录移动文件
	 * 
	 * @param orginalFilePathAndName
	 *            原始文件
	 * @param destDir
	 *            目标文件夹
	 */
	public void moveFileTo(String orginalFilePathAndName, String destDir) {
		Validate.notNull(orginalFilePathAndName, "原始文件不能为空");
		Validate.notNull(destDir, "目标文件夹不能为空");

		try {
			orginalFilePathAndName = replaceAll(orginalFilePathAndName);
			destDir = replaceAll(destDir);
			String filePathAndName = destDir + File.separator
					+ orginalFilePathAndName.substring(orginalFilePathAndName.lastIndexOf(File.separator) + 1);

			boolean b = this.ftpClient.rename(orginalFilePathAndName, filePathAndName);
			if (!b) {
				throw new FTPException("从远程的一个目录往远程的另一个目录移动文件時出错ftpClient.rename返回false");
			}
		} catch (Exception e) {
			throw new FTPException("从远程的一个目录往远程的另一个目录移动文件時出错", e);
		}

	}

	/**
	 * 将文件从ftp的一个目录拷贝到另一个目录
	 * 
	 * @param orginalFilePathAndName
	 * @param destDir
	 */
	public void copyFileTo(String orginalFilePathAndName, String destDir) {
		InputStream in = this.getInputStreamFromFTP(orginalFilePathAndName);
		this.moveFileTo(orginalFilePathAndName, destDir);
		this.writeInputStreamToFTP(orginalFilePathAndName, in);
	}

	/**
	 * 下载文件，并保存到指定的本地目录
	 * 
	 * @param path
	 *            ftp上的文件所在所在路径
	 * @param fileName
	 *            所取文件名称
	 * @param localpath
	 *            保存到本地的文件路径(全路径，包括文件名称)
	 * @throws Exception
	 */
	public void downloadFTPFile(String path, String fileName, String localpath) throws Exception {
		logger.info("正在下载文件，并保存到指定的本地目录，path=" + path + ", fileName=" + fileName + ", localpath=" + localpath
				+ " ......");

		Validate.notNull(path, "ftp上的文件所在所在路径不能为空");
		Validate.notNull(fileName, " 所取文件名称不能为空");
		Validate.notNull(localpath, "  保存到本地的文件路径(全路径，包括文件名称)不能为空");

		OutputStream is = null;
		try {
			path = replaceAll(path);
			changeDirectory(path);

			File f = new File(localpath);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

			is = new FileOutputStream(f);
			if (!ftpClient.retrieveFile(fileName, is)) {
				throw new FileNotFoundException(fileName);
			}
			is.close();
		} catch (Exception e) {
			logger.error("ftp下载文件，并保存到指定的本地目录", e);
			throw new FTPException("ftp下载文件，并保存到指定的本地目录", e);
		} finally {
			is.close();
		}
	}

	/**
	 * 通过文件名称获取远程文件流
	 * 
	 * @param path
	 *            ftp上的文件所在所在路径
	 * @param fileName
	 *            所取文件名称
	 * @return
	 */
	public InputStream getInputStreamFromFTP(String path, String fileName) {
		logger.info("正在通过文件名称获取远程文件流，path=" + path + ", fileName=" + fileName + " ......");

		Validate.notNull(path, "ftp上的文件所在所在路径不能为空");
		Validate.notNull(fileName, " 所取文件名称不能为空");

		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			path = replaceAll(path);
			changeDirectory(path);

			is = ftpClient.retrieveFileStream(fileName);// 获取远程ftp上指定文件的InputStream

			if (null == is) {
				throw new FileNotFoundException(fileName);
			}

			bos = new ByteArrayOutputStream();
			int length;
			byte[] buf = new byte[2048];
			while (-1 != (length = is.read(buf, 0, buf.length))) {
				bos.write(buf, 0, length);
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			is.close();
			bos.close();
			this.ftpClient.completePendingCommand();
			return bis;
		} catch (Exception e) {
			logger.error("ftp通过文件名称获取远程文件流", e);
			throw new FTPException("ftp通过文件名称获取远程文件流", e);
		} finally {
			try {
				is.close();
				bos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过文件完整路径从ftp上获取该文件的二进制流
	 * 
	 * @param pathAndName
	 *            文件完整路径 例如:/test/aa.xml
	 * @return
	 */
	public byte[] getByteArrayFromFTP(String pathAndName) {
		logger.info("正在通过文件完整路径从ftp上获取该文件的二进制流，filePathAndName=" + pathAndName + " ......");

		Validate.notNull(pathAndName, "ftp上的名和路径不能为空");

		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			pathAndName = replaceAll(pathAndName);
			String path = pathAndName.substring(0, pathAndName.lastIndexOf(File.separator) + 1);
			String fileName = pathAndName.substring(pathAndName.lastIndexOf(File.separator) + 1);

			changeDirectory(path);

			is = ftpClient.retrieveFileStream(fileName);// 获取远程ftp上指定文件的InputStream

			if (null == is) {
				throw new FileNotFoundException(fileName);
			}

			bos = new ByteArrayOutputStream();
			int length;
			byte[] buf = new byte[2048];
			while (-1 != (length = is.read(buf, 0, buf.length))) {
				bos.write(buf, 0, length);
			}
			is.close();
			this.ftpClient.completePendingCommand();
			return bos.toByteArray();
		} catch (Exception e) {
			logger.error("通过文件完整路径从ftp上获取该文件的二进制流出错", e);
			throw new FTPException("通过文件完整路径从ftp上获取该文件的二进制流出错", e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过文件名称获取远程文件流
	 * 
	 * @param pathAndName
	 *            ftp上的文件所在所在路径和名称
	 * @param fileName
	 *            所取文件名称
	 * @return
	 */
	public InputStream getInputStreamFromFTP(String pathAndName) {
		logger.info("正在通过文件名称获取远程文件流，pathAndName=" + pathAndName + " ......");

		Validate.notNull(pathAndName, "ftp上的文件所在所在路径和名称不能为空");

		pathAndName = replaceAll(pathAndName);
		String path = pathAndName.substring(0, pathAndName.lastIndexOf(File.separator) + 1);
		String fileName = pathAndName.substring(pathAndName.lastIndexOf(File.separator) + 1);

		try {
			changeDirectory(path);

			InputStream is = this.getInputStreamFromFTP(path, fileName);
			if (null == is) {
				throw new FileNotFoundException(fileName);
			}
			return is;
		} catch (Exception e) {
			logger.error("ftp通过文件名称获取远程文件流", e);
			throw new FTPException("ftp通过文件名称获取远程文件流", e);
		}
	}

	/**
	 * 判断文件是否存在ftp目录上
	 * 
	 * @param path
	 *            ftp上的文件所在所在路径
	 * @param fileName
	 *            所判断文件名称
	 * @return
	 */
	public boolean exsitsFile(String path, String fileName) {
		logger.info("正在判断文件是否存在ftp目录上，path=" + path + ", fileName=" + fileName + " ......");

		Validate.notNull(path, "ftp上的文件所在所在路径不能为空");
		Validate.notNull(fileName, " 所取文件名称不能为空");

		InputStream is = null;
		try {
			path = replaceAll(path);
			boolean flag = false;
			changeDirectory(path);

			is = ftpClient.retrieveFileStream(fileName);// 获取远程ftp上指定文件的InputStream
			if (null != is) {
				flag = true;
			}
			is.close();
			this.ftpClient.completePendingCommand();
			return flag;
		} catch (Exception e) {
			throw new RuntimeException("判断文件是否存在ftp目录上时报错,path=" + path + ", fileName=" + fileName, e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断文件是否存在ftp目录上
	 * 
	 * @param pathAndName
	 *            ftp上的文件所在所在文件全路径
	 * @return
	 */
	public boolean exsitsFile(String pathAndName) {
		logger.info("正在判断文件是否存在ftp目录上，pathAndName=" + pathAndName + " ......");

		Validate.notNull(pathAndName, "判断文件是否存在ftp目录上不能为空");

		pathAndName = replaceAll(pathAndName);
		String path = pathAndName.substring(0, pathAndName.lastIndexOf(File.separator) + 1);
		String fileName = pathAndName.substring(pathAndName.lastIndexOf(File.separator) + 1);

		InputStream is = null;
		try {
			boolean flag = false;
			changeDirectory(path);

			is = ftpClient.retrieveFileStream(fileName);// 获取远程ftp上指定文件的InputStream
			if (null != is) {
				flag = true;
			}
			is.close();
			this.ftpClient.completePendingCommand();
			return flag;
		} catch (Exception e) {
			throw new RuntimeException("判断文件是否存在ftp目录上时报错,path=" + path + ", fileName=" + fileName, e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取ftp上的文件,并把文件内容写为字符串返回
	 * 
	 * @param pathAndName
	 *            ftp上的文件所在所在路径和名称
	 * @return
	 */
	public String readFileToStringFromFTP(String pathAndName) {
		Validate.notNull(pathAndName, "ftp上的文件所在所在路径和名称不能为空");

		String path = pathAndName.substring(0, pathAndName.lastIndexOf(File.separator) + 1);
		String fileName = pathAndName.substring(pathAndName.lastIndexOf(File.separator) + 1);
		return this.readFileToStringFromFTP(path, fileName);
	}

	/**
	 * 将字符串写入到ftp文件里
	 * 
	 * @param remoteFilePathAndName
	 * @param content
	 */
	public void writeStringToFtpFile(String remoteFilePathAndName, String content) {
		Validate.notNull(remoteFilePathAndName, "ftp上的文件所在所在路径和名称不能为空");

		try {
			ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			this.writeInputStreamToFTP(remoteFilePathAndName, in);
		} catch (UnsupportedEncodingException e) {
			throw new FTPException("将字符串写入到ftp文件里出错", e);
		}
	}

	/**
	 * 读取ftp上的文件,并把文件内容写为字符串返回
	 * 
	 * @param path
	 *            ftp上的文件所在所在路径
	 * @param fileName
	 *            所取文件名称
	 * @return
	 */
	public String readFileToStringFromFTP(String path, String fileName) {
		logger.info("正在读取ftp上的文件,并把文件内容写为字符串返回，path=" + path + ", fileName=" + fileName + " ......");

		Validate.notNull(path, "ftp上的文件所在所在路径不能为空");
		Validate.notNull(fileName, " 所取文件名称不能为空");

		InputStream is = null;
		try {
			path = replaceAll(path);
			changeDirectory(path);

			is = ftpClient.retrieveFileStream(fileName);
			if(is == null){
				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line = null;
			StringBuffer sb = new StringBuffer();
			while (null != (line = br.readLine())) {
				sb.append(line);
			}

			is.close();
			this.ftpClient.completePendingCommand();
			return sb.toString();
		} catch (Exception e) {
			logger.error("读取ftp上的文件,并把文件内容写为字符串返回", e);
			throw new FTPException("读取ftp上的文件,并把文件内容写为字符串返回", e);
		} finally {
			try {
				if(is != null){
					is.close();					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            ftp路径
	 * @param fileName
	 *            文件名
	 * @throws Exception
	 */
	public void deleteFile(String path, String fileName) throws Exception {
		logger.info("正在删除文件,path=" + path + ", fileName" + fileName + " ......");

		Validate.notNull(path, "ftp路径不能为空");
		Validate.notNull(fileName, "文件名不能为空");

		try {
			path = replaceAll(path);
			changeDirectory(path);

			boolean b = ftpClient.deleteFile(fileName);
			if (!b) {
				throw new FTPException("ftp删除文件出错ftpClient.deleteFile返回false");
			}
		} catch (Exception e) {
			logger.error("删除文件出错," + e.getStackTrace(), e);
			throw new FTPException("ftp删除文件出错", e);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param pathAndName
	 *            ftp路径和文件名
	 * @throws Exception
	 */
	public void deleteFile(String pathAndName) throws Exception {
		logger.info("正在删除文件,pathAndName=" + pathAndName + " ......");

		Validate.notNull(pathAndName, "ftp路径和文件名不能为空");

		try {
			pathAndName = replaceAll(pathAndName);
			String path = pathAndName.substring(0, pathAndName.lastIndexOf(File.separator) + 1);
			String fileName = pathAndName.substring(pathAndName.lastIndexOf(File.separator) + 1);

			changeDirectory(path);

			ftpClient.deleteFile(fileName);
		} catch (Exception e) {
			logger.error("删除文件出错," + e.getStackTrace(), e);
			throw new FTPException("ftp删除文件出错", e);
		}
	}

	/**
	 * 清空文件夹
	 * 
	 * @param path
	 *            ftp路径
	 */
	public void clearDir(String path) {
		logger.info("正在清空文件夹,path=" + path);

		Validate.notNull(path, "ftp路径不能为空");
		try {
			path = replaceAll(path);
			changeDirectory(path);

			FTPFile[] files = ftpClient.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
					ftpClient.dele(file.getName());
					logger.info("ftp删除文件" + file.getName() + "成功!");
				} else {
					ftpClient.removeDirectory(file.getName());
					logger.info("ftp删除目录" + file.getName() + "成功!");
				}
			}
		} catch (Exception e) {
			logger.error("清空文件夹," + e.getStackTrace(), e);
			throw new FTPException("ftp清空文件夹出错", e);
		}
	}

	/**
	 * ftp创建目录
	 * 
	 * @param path
	 */
	public void makeDirectory(String path) {
		logger.info("ftp创建目录,path=" + path);

		Validate.notNull(path, "ftp路径不能为空");

		try {
			path = replaceAll(path);
			if (this.exsitsDir(path)) {
				return;
			}

			String[] arr = path.split("\\" + File.separator);
			String ppath = File.separator;
			changeDirectory(ppath);
			for (String s : arr) {
				if (null == s || "".equals(s.trim())) {
					continue;
				}
				s = s.trim();

				if (this.exsitsDir(ppath + File.separator + s)) {
					ppath = ppath + File.separator + s;
					continue;
				}

				changeDirectory(ppath);
				this.ftpClient.makeDirectory(s);
				ppath = ppath + File.separator + s;
			}
		} catch (Exception e) {
			logger.error("ftp创建目录失败," + e.getStackTrace(), e);
			throw new FTPException("ftp创建目录出错", e);
		}
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param path
	 *            ftp路径
	 */
	public boolean exsitsDir(String path) {
		logger.info("正在判断文件夹是否存在,path=" + path);

		Validate.notNull(path, "ftp路径不能为空");

		try {
			path = replaceAll(path);
			return ftpClient.changeWorkingDirectory(path);
		} catch (Exception e) {
			logger.error("判断文件夹是否存在," + e.getStackTrace(), e);
			throw new FTPException("ftp判断文件夹是否存在", e);
		}
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @throws Exception
	 * 
	 */
	public void close() throws Exception {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();

				ftpClient.disconnect();
			} catch (Exception e) {
				logger.error("关闭FTP连接出错," + e.getStackTrace(), e);
				throw new FTPException("关闭FTP连接出错", e);
			}
		}
	}

	/**
	 * 改变路径
	 * 
	 * @param path
	 *            新路径
	 * @throws Exception
	 */
	private void changeDirectory(String path) throws Exception {
		try {
			path = replaceAll(path);
			if (!ftpClient.changeWorkingDirectory(path)) {
				String[] temp = path.split("\\" + File.separator);
				String tmpStr = "";

				for (int i = 1; i < temp.length; i++) {
					tmpStr += File.separator + temp[i];
					if (ftpClient.changeWorkingDirectory(tmpStr)) {
						continue;
					} else {
						ftpClient.makeDirectory(tmpStr);
					}
				}

				ftpClient.changeWorkingDirectory(path);
			}
		} catch (Exception e) {
			logger.error("改变ftp路径出错" + e.getStackTrace(), e);
			throw new FTPException("改变ftp路径出错", e);
		}
	}

	/**
	 * 设置操作失败之后的重试次数
	 * 
	 * @param retryTimes
	 *            重试次数
	 */
	public void setRetryTimes(int retryTimes) {
		RETRYTIMES_INIT_VALUE = retryTimes;
	}

	/**
	 * 向外部提供ftpClient对象，如果外部觉得此工具里的方法不不能满足需求可以基于ftpClient进行扩展
	 * 
	 * @return
	 */
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	private String replaceAll(String str) {
		str = str.replaceAll("\\\\", "\\" + File.separator);
		str = str.replaceAll("/", "\\" + File.separator);
		return str;
	}


}
