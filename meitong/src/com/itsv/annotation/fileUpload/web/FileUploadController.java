package com.itsv.annotation.fileUpload.web;


import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.util.RePublic;
import com.itsv.annotation.util.Upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除file_upload的前端处理类
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
@Controller
public class FileUploadController extends BaseAnnotationController<FileUpload> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(FileUploadController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.fileUpload";
	@Autowired
	private FileUploadService fileUploadService; //逻辑层对象

	public FileUploadController(){

		super.setDefaultCheckToken(true);
		super.setAddView("fileUpload/fileUpload/add");
		super.setIndexView("fileUpload/fileUpload/index");
		super.setEditView("fileUpload/fileUpload/edit");

	}

  /**
   * 注册自定义类型转换类，用来转换日期对象
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
  }    

	@RequestMapping("/fileupload.htm")
	public void fileupload(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("bj");
		MultipartHttpServletRequest muRequest = (MultipartHttpServletRequest)request;
		MultipartFile  mup 	= muRequest.getFile("file");
		try {
			String filename=new Date().getTime()+"."+mup.getOriginalFilename().substring(mup.getOriginalFilename().length()-3, mup.getOriginalFilename().length());
			String path = RePublic.PICURL;
			String url = RePublic.URL+filename;
			//上传文件到服务器下
			Upload.SaveFileFromInputStream(mup.getInputStream(),
					path, filename);
			//记录上传的文件
			FileUpload fu = new FileUpload();
			fu.setDownloadPath(url);
			fu.setFileName(filename);
			fu.setFileTime(new Date());
			fu.setType(type);
			this.fileUploadService.add(fu);
			PrintWriter pw = response.getWriter();
			pw.write(fu.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("file.htm")
	public void file(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("bj");
		String fId = request.getParameter("sid");
		FileUpload fileUpload = new FileUpload();
		fileUpload.setType(type);
		fileUpload.setFId(fId);
		List<FileUpload> list = this.fileUploadService.queryByVO(fileUpload);
		for (FileUpload f :list){
			this.fileUploadService.delete(f.getId());
		}
		fileupload(request, response);
	}
}