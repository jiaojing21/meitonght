package com.itsv.platform.common.fileMgr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itsv.gbp.core.admin.bo.LoggedService;
import com.itsv.platform.KeyGenerator;
import com.itsv.platform.UuidGenerator;

import com.itsv.platform.common.fileMgr.util.CalendarUtil;
import com.itsv.platform.common.fileMgr.util.Constants;
import com.itsv.platform.common.fileMgr.util.SmartFile;
import com.itsv.platform.common.fileMgr.util.SmartFiles;
import com.itsv.platform.common.fileMgr.util.SmartUpload;
import com.itsv.platform.common.fileMgr.util.fileEncrypter;
import com.itsv.platform.common.fileMgr.util.zipFileUtil;
import com.itsv.platform.common.fileMgr.dao.UploadFileDao;
import com.itsv.platform.common.fileMgr.vo.ZjWjlb;
import com.itsv.platform.common.fileMgr.vo.ZjWjys;

public class UploadFileService extends LoggedService {

	// 持久对象
	private UploadFileDao uploadFileDao;

	// 默认二级目录名称
	private static String DEFAULT_SECOND_DIR = "default";

	/**
	 * 默认构造方法
	 */
	public UploadFileService() {
	}

	/**
	 * 读取可用类别列表
	 */
	public String getCclbList() {
		String str = "";
		List lbmcList = this.uploadFileDao.getCclbList();
		for (int i = 0; i < lbmcList.size() - 1; i++) {
			str += ((ZjWjlb) lbmcList.get(i)).getCclbmc() + ",";
		}
		if (lbmcList.size() > 0) {
			str += ((ZjWjlb) lbmcList.get(lbmcList.size() - 1)).getCclbmc();
		}
		return str;
	}

	/**
	 * 保存单个附件，扩展方法
	 * 
	 * @param filename：上传文件名称
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj) throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, "", -1);
	}

	/**
	 * 保存单个附件，扩展方法
	 * 
	 * @param filename：上传文件名称
	 * @param maxFileSize：上传文件最大字节，例如:8表示上传文件最大为8K
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj, int maxFileSize) throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, "", maxFileSize);
	}

	/**
	 * 保存单个附件
	 * 
	 * @param filename：上传文件名称
	 * @param ywbz：业务标志，用于存储所属业务模块主键值
	 * @param maxFileSize：上传文件最大字节，例如:8表示上传文件最大为8K
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj, String ywbz, int maxFileSize)
			throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, ywbz, null,
				maxFileSize);
	}

	/**
	 * 保存单个附件，原始方法
	 * 
	 * @param ywbz：业务标志，用于存储所属业务模块主键值
	 * @param ejml：二级目录名称
	 * @param maxFileSize：上传文件最大字节，例如:8表示上传文件最大为8K
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj, String ywbz, String ejml, int maxFileSize)
			throws Exception {

		List<ZjWjys> fileObjList = new LinkedList<ZjWjys>();

		SmartFiles files = smartUpload.getFiles();
		for (int i = 0; i < files.getCount(); i++) {
			SmartFile file = files.getFile(i);

			String propName = file.getFieldName();
			if (!propName.equalsIgnoreCase(fieldName))
				continue;

			if (file.isMissing())
				continue;

			String filename = file.getFilePathName();
			if ("".equalsIgnoreCase(filename.trim()))
				continue;

			// 获取上传附件信息，在本地存储时重新命名
			String oriFileName = filename
					.substring((filename.lastIndexOf("\\")) + 1); // 去掉上传文件的目录名

			String uploadFileName = UuidGenerator.getUUID();

			// 获取二级目录
			String secondDir = getSecondDir(fileObj, fileObj.getEjmlgz());

			// 设置文件创建时间
			String cjsj = CalendarUtil.getCurrentDateTime();

			// 组合上传附件的全路径：文件类型的根目录+二级目录+文件名称
			String prePath = fileObj.getRoot() + Constants.PATH_SEPERATOR
					+ secondDir;
			String filePath = prePath + Constants.PATH_SEPERATOR
					+ uploadFileName;

			// 创建文件目录文件
			createFile(file, filePath, maxFileSize,
					fileObj.getIszip() == 1, fileObj.getIsencrypt() == 1);

			// 新建回传对象
			ZjWjys wjys = new ZjWjys();

			// 设置输入数据节点
			String fileid =KeyGenerator.getInstance().getNextKey(wjys.getClass())+"";
			wjys.setId((String) fileid);
			wjys.setYsbhPk((String) fileid);
			wjys.setCclbbhPk(fileObj.getCclbbh_pk());
			if (fileObj.getIszip() == 1)
				uploadFileName = uploadFileName + ".zip";
			if (fileObj.getIsencrypt() == 1)
				uploadFileName = uploadFileName + ".tdes";
			wjys.setIszip(fileObj.getIszip());
			wjys.setIsencrypt(fileObj.getIsencrypt());
			wjys.setWybs(uploadFileName);
			wjys.setWjmc(oriFileName);
			wjys.setWjzt(Constants.status_inuse);
			wjys.setCclj(prePath);
			wjys.setCjsj(cjsj);
			wjys.setScxgsj(cjsj);
			wjys.setYwbz(ywbz);

			try {
				uploadFileDao.insertFj(wjys);

			} catch (Exception e) {
				throw new Exception("插入附件表失败！");
			}
			fileObjList.add(wjys);
		}
		return fileObjList;
	}

	/**
	 * 修改单个附件，扩展方法
	 * 
	 * @param file：上传附件文件名
	 * @param ysbh_pk：附件映射主键PK值
	 */
	public ZjWjys updataFile(SmartUpload smartUpload, String fieldName,
			int index, String filename, String ysbh_pk) throws Exception {
		return updateFile(smartUpload, fieldName, index, filename, ysbh_pk, -1);
	}

	/**
	 * 修改单个附件，原始方法
	 * 
	 * @see 通过附件ID查询附件存储路径，附件名，然后删除旧文件创建新文件，再修改附件表里的信息
	 * @param file：上传附件文件名
	 * @param ysbh_pk：附件映射主键PK值
	 * @param maxFileSize：上传文件最大字节，例如:8表示上传文件最大为8K
	 */
	public ZjWjys updateFile(SmartUpload smartUpload, String fieldName,
			int index, String filename, String ysbh_pk, int maxFileSize)
			throws Exception {

		if (filename == null || filename.trim().equals("") || ysbh_pk == null
				|| ysbh_pk.trim().equals(""))
			return null;

		SmartFile file = smartUpload.getFiles().getFile(index);
		String oriFileName = file.getFileName();
		String uploadFileName = UuidGenerator.getUUID();

		// 通过附件ID查询附件信息
		List wjysList = uploadFileDao.getWjysByFjid(ysbh_pk);
		ZjWjys wjys = (ZjWjys) wjysList.get(0);
		String path = wjys.getCclj();
		String oldFileName = wjys.getWybs();

		String oldPath = path + Constants.PATH_SEPERATOR + oldFileName;
		String newPath = path + Constants.PATH_SEPERATOR + uploadFileName;

		// 创建新文件
		createFile(file, newPath, maxFileSize, wjys
				.getIszip() == 1, wjys.getIsencrypt() == 1);

		// 删除附件
		File oldFile = new File(oldPath);
		if (oldFile.exists())
			oldFile.delete();

		// 设置修改时间
		String scxgsj = CalendarUtil.getCurrentDateTime();

		// 组织更新的数据结构
		wjys.setYsbhPk((ysbh_pk));
		wjys.setScxgsj(scxgsj);
		wjys.setWjmc(oriFileName);
		if (wjys.getIszip() == 1)
			uploadFileName = uploadFileName + ".zip";
		if (wjys.getIsencrypt() == 1)
			uploadFileName = uploadFileName + ".tdes";
		wjys.setWybs(uploadFileName);

		// 更新附件信息表
		uploadFileDao.update(wjys);

		return wjys;
	}

	/**
	 * 删除附件
	 * 
	 * @see 删除附件时，同时删除文件系统中存储的文件和文件映射表对应字段
	 * @param ysbh_pks：附件映射主键或以逗号分开的多个附件映射主键
	 */
	public void deleteFile(String ysbh_pks) throws Exception {

		if (ysbh_pks == null || ysbh_pks.trim().equals(""))
			throw new Exception("附件映射主键为空！");

		// 拆分附件值，间隔符为英文半角逗号
		String[] ysbhs = ysbh_pks.split(Constants.ID_SEPERATOR);

		// 循环删除多个文件和文件映射表里的记录
		for (int i = 0; i < ysbhs.length; i++) {
			List wjysList = uploadFileDao.getWjysByFjid(ysbhs[i]);
			if (wjysList == null)
				throw new Exception("附件不存在或查询附件表失败！");
			ZjWjys zjwjys = (ZjWjys) wjysList.get(0);
			// 组合路径
			String path = zjwjys.getCclj() + Constants.PATH_SEPERATOR
					+ zjwjys.getWybs();
			// 删除附件记录
			boolean isDele = uploadFileDao.deleteFile(zjwjys);
			// 删除附件
			if (isDele) {
				File file = new File(path);
				if (file.exists())
					file.delete();
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param fileid：附件编号
	 */
	public ZjWjys downFile(String fileid) throws Exception {
		ZjWjys wjys = (ZjWjys) uploadFileDao.getWjysByFjid(fileid).get(0);
		return wjys;
	}

	/**
	 * 处理下载文件数据
	 * 
	 * @param request
	 * @param response
	 * @param wjys：下载附件对象
	 */
	public void doFileData(HttpServletRequest request,
			HttpServletResponse response, ZjWjys wjys) throws Exception {
		try {
			// 修改文件名的编码格式，解决中文文件名乱码问题
			String fileName = URLEncoder.encode(wjys.getWjmc(), "UTF-8");
			// 设置缓存大小
			response.setHeader("Cache-Control", "max-age=" + "no-cache");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName); // filename应该是编码后的(utf-8)
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fullPath = "";
		// 根据文件类别根目录，组装文件存储全路径
		fullPath = wjys.getCclj() + Constants.PATH_SEPERATOR + wjys.getWybs();
		// 输入出文件数据

		String tmpFile = "";
		if (wjys.getIszip() == 1 && wjys.getIsencrypt() == 0) {
			// 解压
			String zipPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			zipFileUtil.UnZipFiles(new File(fullPath), zipPath);
			tmpFile = zipPath;
		} else if (wjys.getIszip() == 0 && wjys.getIsencrypt() == 1) {
			// 解密
			String descryptPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			File descryptFile = new File(descryptPath);
			fileEncrypter.decrypt(new File(fullPath), descryptFile);
			tmpFile = descryptPath;
		} else if (wjys.getIszip() == 1 && wjys.getIsencrypt() == 1) {
			// 解密
			String descryptPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			File descryptFile = new File(descryptPath);
			fileEncrypter.decrypt(new File(fullPath), descryptFile);
			// 解压
			String zipPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			zipFileUtil.UnZipFiles(descryptFile, zipPath);
			tmpFile = zipPath;
		}else{
			tmpFile = fullPath;
		}

		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = response.getOutputStream();
			inputStream = new FileInputStream(tmpFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (java.net.SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream = null;
			inputStream = null;

			//删除为解压建立的临时文件
			File zipFile = new File(tmpFile);
			if (!tmpFile.equalsIgnoreCase(fullPath)&&zipFile.exists())
				zipFile.delete();
		}
	}

	/**
	 * 根据文件类别名称获取存储根目录及文件类别编号
	 * 
	 * @param fileType：存储类别
	 */
	public UploadFileObj getUploadFileObj(String fileType) {
		if (fileType == null || fileType.length() == 0 || fileType.trim() == "")
			return null;
		List list = uploadFileDao.getWjlbByLbmc(fileType);
		if (list == null || list.size() <= 0)
			return null;
		ZjWjlb wjlb = (ZjWjlb) list.get(0);
		return new UploadFileObj(wjlb.getCcgml(), wjlb.getEjmlgz(), String
				.valueOf(wjlb.getCclbbhPk()), wjlb.getIszip(), wjlb
				.getIsencrypt());
	}

	/**
	 * 根据文件类型设置的二级目录规则生成二级目录
	 * 
	 * @param ejml：业务模块传递的二级目录规则
	 */
	private String getSecondDir(UploadFileObj fileObj, String ejml) {
		String secondDir = null;
		// 任意方式：由业务模块提供二级目录
		if ("0".equals(fileObj.getEjmlgz())
				&& (ejml != null && ejml.length() > 0)) {
			secondDir = ejml;
		}
		// 系统时间方式：根据系统时间创建二级目录
		else if ("1".equals(fileObj.getEjmlgz())) {
			secondDir = getDirByTime();
		}
		// 默认二级目录，用于程序未定义二级目录时使用
		else {
			secondDir = DEFAULT_SECOND_DIR;
		}

		// 在存储磁盘创建二级目录文件夹
		createFolder(fileObj.getRoot() + Constants.PATH_SEPERATOR + secondDir
				+ Constants.PATH_SEPERATOR);

		return secondDir;
	}

	/**
	 * 根据二级目录规则生成二级目录，格式为：年/月/日。如：2007/2007-06/2007-06-07
	 */
	private String getDirByTime() {
		String year = CalendarUtil.getCurrYear();
		String month = CalendarUtil.getCurrMonth();
		String yyyymmdd = CalendarUtil
				.getCalendarByFormat(CalendarUtil.FORMAT11);
		String dir = year + Constants.PATH_SEPERATOR + year + "-" + month
				+ Constants.PATH_SEPERATOR + yyyymmdd;
		return dir;
	}

	/**
	 * 检查并且创建目录
	 * 
	 * @param FilePath：目录名称
	 */
	public void checkFolder(String fullPath) {
		File fileDirecotry = new File(fullPath);
		if (!fileDirecotry.exists()) {
			if (fullPath.indexOf(Constants.PATH_SEPERATOR) >= 0) {
				int intseparatorPointer = fullPath
						.indexOf(Constants.PATH_SEPERATOR);
				while (intseparatorPointer >= 0) {
					if (intseparatorPointer == fullPath.length()) {
					}
					String strFilePath = fullPath.substring(0,
							intseparatorPointer);
					fileDirecotry = new File(strFilePath);
					if (!fileDirecotry.exists()) {
						fileDirecotry.mkdir();
					}
					// 处理下一个目录
					intseparatorPointer = fullPath.indexOf(
							Constants.PATH_SEPERATOR, intseparatorPointer + 1);
				}
			}
		}
	}

	/**
	 * 检查并且创建目录
	 * 
	 * @param FilePath：目录名称
	 */
	private void createFolder(String fullPath) {
		checkFolder(fullPath);
	}

	/**
	 * 创建保存文件
	 * 
	 * @param filename：上传文件名
	 * @param pDestFile：目标文件路径以及文件名
	 * @param pMaxFileSize：上传文件最大字节，例如:8表示上传文件最大为8K
	 */
	private boolean createFile(SmartFile file, String pDestFile,
			int pMaxFileSize, boolean isZip, boolean isEncrypt)
			throws Exception {
		boolean info = false;
		if (pDestFile != null && !pDestFile.trim().equals("")) {
			try {
				file.saveAs(pDestFile);
				if (isZip && !isEncrypt) {
					File srcfile = new File(pDestFile);
					File zipfile = new File(pDestFile + ".zip");
					zipFileUtil.ZipFiles(srcfile, zipfile);
					// 删除原始文件
					if (srcfile.exists())
						srcfile.delete();
				} else if (!isZip && isEncrypt) {
					File srcfile = new File(pDestFile);
					fileEncrypter.encrypt(srcfile);
					// 删除原始文件
					if (srcfile.exists())
						srcfile.delete();
				} else if(isZip&&isEncrypt){
					File srcfile = new File(pDestFile);
					File zipfile = new File(pDestFile + ".zip");
					zipFileUtil.ZipFiles(srcfile, zipfile);
					fileEncrypter.encrypt(zipfile);
					// 删除原始文件
					if (srcfile.exists())
						srcfile.delete();
					// 删除压缩文件
					if (zipfile.exists())
						zipfile.delete();
				}else{
					
				}
			} catch (FileNotFoundException fnfe) {
				throw new Exception(fnfe.toString());
			} catch (IOException ioe) {
				throw new Exception(ioe.toString());
			}
			info = true;
		}
		return info;
	}

	public void setUploadFileDao(UploadFileDao uploadFileDao) {
		this.uploadFileDao = uploadFileDao;
	}
}
