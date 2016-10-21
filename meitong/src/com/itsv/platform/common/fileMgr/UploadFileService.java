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

	// �־ö���
	private UploadFileDao uploadFileDao;

	// Ĭ�϶���Ŀ¼����
	private static String DEFAULT_SECOND_DIR = "default";

	/**
	 * Ĭ�Ϲ��췽��
	 */
	public UploadFileService() {
	}

	/**
	 * ��ȡ��������б�
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
	 * ���浥����������չ����
	 * 
	 * @param filename���ϴ��ļ�����
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj) throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, "", -1);
	}

	/**
	 * ���浥����������չ����
	 * 
	 * @param filename���ϴ��ļ�����
	 * @param maxFileSize���ϴ��ļ�����ֽڣ�����:8��ʾ�ϴ��ļ����Ϊ8K
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj, int maxFileSize) throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, "", maxFileSize);
	}

	/**
	 * ���浥������
	 * 
	 * @param filename���ϴ��ļ�����
	 * @param ywbz��ҵ���־�����ڴ洢����ҵ��ģ������ֵ
	 * @param maxFileSize���ϴ��ļ�����ֽڣ�����:8��ʾ�ϴ��ļ����Ϊ8K
	 */
	public List saveFile(SmartUpload smartUpload, String fieldName,
			UploadFileObj fileObj, String ywbz, int maxFileSize)
			throws Exception {
		return saveFile(smartUpload, fieldName, fileObj, ywbz, null,
				maxFileSize);
	}

	/**
	 * ���浥��������ԭʼ����
	 * 
	 * @param ywbz��ҵ���־�����ڴ洢����ҵ��ģ������ֵ
	 * @param ejml������Ŀ¼����
	 * @param maxFileSize���ϴ��ļ�����ֽڣ�����:8��ʾ�ϴ��ļ����Ϊ8K
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

			// ��ȡ�ϴ�������Ϣ���ڱ��ش洢ʱ��������
			String oriFileName = filename
					.substring((filename.lastIndexOf("\\")) + 1); // ȥ���ϴ��ļ���Ŀ¼��

			String uploadFileName = UuidGenerator.getUUID();

			// ��ȡ����Ŀ¼
			String secondDir = getSecondDir(fileObj, fileObj.getEjmlgz());

			// �����ļ�����ʱ��
			String cjsj = CalendarUtil.getCurrentDateTime();

			// ����ϴ�������ȫ·�����ļ����͵ĸ�Ŀ¼+����Ŀ¼+�ļ�����
			String prePath = fileObj.getRoot() + Constants.PATH_SEPERATOR
					+ secondDir;
			String filePath = prePath + Constants.PATH_SEPERATOR
					+ uploadFileName;

			// �����ļ�Ŀ¼�ļ�
			createFile(file, filePath, maxFileSize,
					fileObj.getIszip() == 1, fileObj.getIsencrypt() == 1);

			// �½��ش�����
			ZjWjys wjys = new ZjWjys();

			// �����������ݽڵ�
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
				throw new Exception("���븽����ʧ�ܣ�");
			}
			fileObjList.add(wjys);
		}
		return fileObjList;
	}

	/**
	 * �޸ĵ�����������չ����
	 * 
	 * @param file���ϴ������ļ���
	 * @param ysbh_pk������ӳ������PKֵ
	 */
	public ZjWjys updataFile(SmartUpload smartUpload, String fieldName,
			int index, String filename, String ysbh_pk) throws Exception {
		return updateFile(smartUpload, fieldName, index, filename, ysbh_pk, -1);
	}

	/**
	 * �޸ĵ���������ԭʼ����
	 * 
	 * @see ͨ������ID��ѯ�����洢·������������Ȼ��ɾ�����ļ��������ļ������޸ĸ����������Ϣ
	 * @param file���ϴ������ļ���
	 * @param ysbh_pk������ӳ������PKֵ
	 * @param maxFileSize���ϴ��ļ�����ֽڣ�����:8��ʾ�ϴ��ļ����Ϊ8K
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

		// ͨ������ID��ѯ������Ϣ
		List wjysList = uploadFileDao.getWjysByFjid(ysbh_pk);
		ZjWjys wjys = (ZjWjys) wjysList.get(0);
		String path = wjys.getCclj();
		String oldFileName = wjys.getWybs();

		String oldPath = path + Constants.PATH_SEPERATOR + oldFileName;
		String newPath = path + Constants.PATH_SEPERATOR + uploadFileName;

		// �������ļ�
		createFile(file, newPath, maxFileSize, wjys
				.getIszip() == 1, wjys.getIsencrypt() == 1);

		// ɾ������
		File oldFile = new File(oldPath);
		if (oldFile.exists())
			oldFile.delete();

		// �����޸�ʱ��
		String scxgsj = CalendarUtil.getCurrentDateTime();

		// ��֯���µ����ݽṹ
		wjys.setYsbhPk((ysbh_pk));
		wjys.setScxgsj(scxgsj);
		wjys.setWjmc(oriFileName);
		if (wjys.getIszip() == 1)
			uploadFileName = uploadFileName + ".zip";
		if (wjys.getIsencrypt() == 1)
			uploadFileName = uploadFileName + ".tdes";
		wjys.setWybs(uploadFileName);

		// ���¸�����Ϣ��
		uploadFileDao.update(wjys);

		return wjys;
	}

	/**
	 * ɾ������
	 * 
	 * @see ɾ������ʱ��ͬʱɾ���ļ�ϵͳ�д洢���ļ����ļ�ӳ����Ӧ�ֶ�
	 * @param ysbh_pks������ӳ���������Զ��ŷֿ��Ķ������ӳ������
	 */
	public void deleteFile(String ysbh_pks) throws Exception {

		if (ysbh_pks == null || ysbh_pks.trim().equals(""))
			throw new Exception("����ӳ������Ϊ�գ�");

		// ��ָ���ֵ�������ΪӢ�İ�Ƕ���
		String[] ysbhs = ysbh_pks.split(Constants.ID_SEPERATOR);

		// ѭ��ɾ������ļ����ļ�ӳ�����ļ�¼
		for (int i = 0; i < ysbhs.length; i++) {
			List wjysList = uploadFileDao.getWjysByFjid(ysbhs[i]);
			if (wjysList == null)
				throw new Exception("���������ڻ��ѯ������ʧ�ܣ�");
			ZjWjys zjwjys = (ZjWjys) wjysList.get(0);
			// ���·��
			String path = zjwjys.getCclj() + Constants.PATH_SEPERATOR
					+ zjwjys.getWybs();
			// ɾ��������¼
			boolean isDele = uploadFileDao.deleteFile(zjwjys);
			// ɾ������
			if (isDele) {
				File file = new File(path);
				if (file.exists())
					file.delete();
			}
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param fileid���������
	 */
	public ZjWjys downFile(String fileid) throws Exception {
		ZjWjys wjys = (ZjWjys) uploadFileDao.getWjysByFjid(fileid).get(0);
		return wjys;
	}

	/**
	 * ���������ļ�����
	 * 
	 * @param request
	 * @param response
	 * @param wjys�����ظ�������
	 */
	public void doFileData(HttpServletRequest request,
			HttpServletResponse response, ZjWjys wjys) throws Exception {
		try {
			// �޸��ļ����ı����ʽ����������ļ�����������
			String fileName = URLEncoder.encode(wjys.getWjmc(), "UTF-8");
			// ���û����С
			response.setHeader("Cache-Control", "max-age=" + "no-cache");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName); // filenameӦ���Ǳ�����(utf-8)
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fullPath = "";
		// �����ļ�����Ŀ¼����װ�ļ��洢ȫ·��
		fullPath = wjys.getCclj() + Constants.PATH_SEPERATOR + wjys.getWybs();
		// ������ļ�����

		String tmpFile = "";
		if (wjys.getIszip() == 1 && wjys.getIsencrypt() == 0) {
			// ��ѹ
			String zipPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			zipFileUtil.UnZipFiles(new File(fullPath), zipPath);
			tmpFile = zipPath;
		} else if (wjys.getIszip() == 0 && wjys.getIsencrypt() == 1) {
			// ����
			String descryptPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			File descryptFile = new File(descryptPath);
			fileEncrypter.decrypt(new File(fullPath), descryptFile);
			tmpFile = descryptPath;
		} else if (wjys.getIszip() == 1 && wjys.getIsencrypt() == 1) {
			// ����
			String descryptPath = wjys.getCclj() + Constants.PATH_SEPERATOR
					+ UuidGenerator.getUUID();
			File descryptFile = new File(descryptPath);
			fileEncrypter.decrypt(new File(fullPath), descryptFile);
			// ��ѹ
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

			//ɾ��Ϊ��ѹ��������ʱ�ļ�
			File zipFile = new File(tmpFile);
			if (!tmpFile.equalsIgnoreCase(fullPath)&&zipFile.exists())
				zipFile.delete();
		}
	}

	/**
	 * �����ļ�������ƻ�ȡ�洢��Ŀ¼���ļ������
	 * 
	 * @param fileType���洢���
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
	 * �����ļ��������õĶ���Ŀ¼�������ɶ���Ŀ¼
	 * 
	 * @param ejml��ҵ��ģ�鴫�ݵĶ���Ŀ¼����
	 */
	private String getSecondDir(UploadFileObj fileObj, String ejml) {
		String secondDir = null;
		// ���ⷽʽ����ҵ��ģ���ṩ����Ŀ¼
		if ("0".equals(fileObj.getEjmlgz())
				&& (ejml != null && ejml.length() > 0)) {
			secondDir = ejml;
		}
		// ϵͳʱ�䷽ʽ������ϵͳʱ�䴴������Ŀ¼
		else if ("1".equals(fileObj.getEjmlgz())) {
			secondDir = getDirByTime();
		}
		// Ĭ�϶���Ŀ¼�����ڳ���δ�������Ŀ¼ʱʹ��
		else {
			secondDir = DEFAULT_SECOND_DIR;
		}

		// �ڴ洢���̴�������Ŀ¼�ļ���
		createFolder(fileObj.getRoot() + Constants.PATH_SEPERATOR + secondDir
				+ Constants.PATH_SEPERATOR);

		return secondDir;
	}

	/**
	 * ���ݶ���Ŀ¼�������ɶ���Ŀ¼����ʽΪ����/��/�ա��磺2007/2007-06/2007-06-07
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
	 * ��鲢�Ҵ���Ŀ¼
	 * 
	 * @param FilePath��Ŀ¼����
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
					// ������һ��Ŀ¼
					intseparatorPointer = fullPath.indexOf(
							Constants.PATH_SEPERATOR, intseparatorPointer + 1);
				}
			}
		}
	}

	/**
	 * ��鲢�Ҵ���Ŀ¼
	 * 
	 * @param FilePath��Ŀ¼����
	 */
	private void createFolder(String fullPath) {
		checkFolder(fullPath);
	}

	/**
	 * ���������ļ�
	 * 
	 * @param filename���ϴ��ļ���
	 * @param pDestFile��Ŀ���ļ�·���Լ��ļ���
	 * @param pMaxFileSize���ϴ��ļ�����ֽڣ�����:8��ʾ�ϴ��ļ����Ϊ8K
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
					// ɾ��ԭʼ�ļ�
					if (srcfile.exists())
						srcfile.delete();
				} else if (!isZip && isEncrypt) {
					File srcfile = new File(pDestFile);
					fileEncrypter.encrypt(srcfile);
					// ɾ��ԭʼ�ļ�
					if (srcfile.exists())
						srcfile.delete();
				} else if(isZip&&isEncrypt){
					File srcfile = new File(pDestFile);
					File zipfile = new File(pDestFile + ".zip");
					zipFileUtil.ZipFiles(srcfile, zipfile);
					fileEncrypter.encrypt(zipfile);
					// ɾ��ԭʼ�ļ�
					if (srcfile.exists())
						srcfile.delete();
					// ɾ��ѹ���ļ�
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
