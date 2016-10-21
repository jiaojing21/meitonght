package com.itsv.annotation.subject.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.security.util.SecureTool;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;


import com.itsv.annotation.brand.bo.BrandService;
import com.itsv.annotation.brand.vo.Brand;
import com.itsv.annotation.fileUpload.bo.FileUploadService;
import com.itsv.annotation.fileUpload.vo.FileUpload;
import com.itsv.annotation.goodCode.bo.GoodCodeService;
import com.itsv.annotation.goodCode.vo.GoodCode;
import com.itsv.annotation.goods.bo.GoodsService;
import com.itsv.annotation.goods.vo.Goods;
import com.itsv.annotation.spec.bo.SpecService;
import com.itsv.annotation.spec.vo.Spec;
import com.itsv.annotation.subGood.bo.SubGoodService;
import com.itsv.annotation.subGood.vo.SubGood;
import com.itsv.annotation.subGoodGoods.bo.SubGoodGoodsService;
import com.itsv.annotation.subGoodGoods.vo.SubGoodGoods;
import com.itsv.annotation.subject.bo.SubjectService;
import com.itsv.annotation.subject.vo.Subject;
import com.itsv.annotation.subjectGoods.bo.SubjectGoodsService;
import com.itsv.annotation.subjectGoods.vo.SubjectGoods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;
/**
 * 说明：增加，修改，删除subject的前端处理类
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */
@Controller

public class SubjectController extends BaseAnnotationController<Subject> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(SubjectController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.subject";
	@Autowired
	private SubjectService subjectService; //逻辑层对象
	
	@Autowired
	private SubGoodService subGoodService; //逻辑层对象
	
	@Autowired
	private UserService userService; //逻辑层对象
	
	@Autowired
	private FileUploadService fileUploadService; //逻辑层对象
	
	@Autowired
	private SubjectGoodsService subjectGoodsService; //逻辑层对象
	
	@Autowired
	private SubGoodGoodsService subGoodGoodsService; //逻辑层对象
	
	@Autowired
	private Itsv_dictionaryService itsv_dictionaryService; //逻辑层对象
	
	@Autowired
	private GoodsService goodsService; //逻辑层对象
	
	@Autowired
	private BrandService brandService; //逻辑层对象
	
	@Autowired
	private SpecService specService; //逻辑层对象
	
	@Autowired
	private GoodCodeService goodCodeService; //逻辑层对象
	
	public SubjectController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/subject/add");
		super.setIndexView("meitong/subject/index");
		super.setEditView("meitong/subject/edit");

	}

  /**
   * 注册自定义类型转换类，用来转换日期对象
   */
  protected void registerEditor(DataBinder binder) {
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(formater, true));
  }    

	//覆盖父类方法，默认执行query()，分页显示数据

	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	//实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Subject subject = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			subject = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", subject);
		} else {
			subject = new Subject();
		}

		this.subjectService.queryByVO(records, subject);
	}

	//显示增加subject页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改subject页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Subject subject = this.subjectService.queryById(id);
		if (null == subject) {
			showMessage(request, "未找到对应的subject记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, subject);
		}
	}
	//跳转到添加页面
	@RequestMapping("/subject.beforeadd.htm")
	public ModelAndView beforeadd(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getAddView());
		return mnv;
	}
	/**
	 * 保存新增专题
	 */
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/subject.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		Subject subject = null;
		SubGood subGood = new SubGood();
		SubGoodGoods  subGoodGoods = new SubGoodGoods();
		SubjectGoods  subjectGoods = new SubjectGoods();
		try {
			subject = param2Object(request);
			subject.setCreatetime(new Date());
			String title = request.getParameter("p_SubGood_title");
			String manypic = request.getParameter("manypic");
			subGood.setTitle(title);
			subject.setUserName(user.getId());
			subject.setRemark1("0");
			this.subGoodService.add(subGood);
			subject.setSubGoodId(subGood.getId());
			this.subjectService.add(subject);
			String ids = request.getParameter("ids");
			String ids1 = request.getParameter("ids1");
			String [] id = ids.split(",");
			String [] id1 = ids1.split(",");
			for(int i = 0;i<id.length; i++){
				String d = id[i];
				subjectGoods.setGoodsId(d);
				subjectGoods.setSubjectId(subject.getId());
				this.subjectGoodsService.add(subjectGoods);
			}
			for(int i = 0;i<id1.length;i++){
				String dd = id1[i];
				subGoodGoods.setSubgoodId(subGood.getId());
				subGoodGoods.setGoodsId(dd);
				this.subGoodGoodsService.add(subGoodGoods);
			}
			if (null != manypic && !"".equals(manypic)) {
				String[] manyid = manypic.split(",");
				for (String idm : manyid) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(idm);
					fileUpload.setFId(subject.getId());
					this.fileUploadService.update(fileUpload);
				}
			}
			showMessage(request, "新增专题成功");
		} catch (AppException e) {
			logger.error("新增专题[" + subject + "]失败", e);
			showMessage(request, "新增专题失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给subject
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subject);
		}

		return new ModelAndView(getAddView(), WebConfig.DATA_NAME, subject);
	}
	//查询前准备数据
	@RequestMapping("/subject.query.htm")
	public ModelAndView querySubject(HttpServletRequest request,HttpServletResponse response, ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//查询列表
	@RequestMapping("/subject.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexAppControl(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		String shmc = "";
		try {
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Subject subject = new Subject();
		subject.setTitle(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"subject");
		this.subjectService.queryByVO(records, subject);
		for(Subject sub : (List<Subject>)records.getSource()){
			User user = this.userService.queryById(sub.getUserName());
			sub.setUser(user.getRealName());
			SubGood subGood =this.subGoodService.queryById(sub.getSubGoodId());
			sub.setSubGood(subGood.getTitle());
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}
	//编辑前数据
	@RequestMapping("/subject.EditData.htm")
	public ModelAndView editData(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String subjectid = "";
		FileUpload fileUpload = new FileUpload();
		try {
			subjectid = ServletRequestUtils.getStringParameter(request, "subjectid");
			Subject subject = subjectService.queryById(subjectid);
			SubGood subGood = this.subGoodService.queryById(subject.getSubGoodId());
			subject.setSubGood(subGood.getTitle());
			fileUpload.setFId(subjectid);
			List<FileUpload> flist = this.fileUploadService.queryByVO(fileUpload);
			mnv.addObject("flist", flist);
			mnv.addObject("data",subject);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mnv.setViewName(getEditView());
		return mnv;
	}
	/**
	 * 保存修改的subject
	 */
	@RequestMapping("/subject.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = null;
		SubGood subGood = new SubGood();
		SubjectGoods subjectGoods = new SubjectGoods();
		SubGoodGoods subGoodGoods = new SubGoodGoods();
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		String id = request.getParameter("sid");
		try {
			subject =this.subjectService.queryById(id);
			String title1 = request.getParameter("p_Subject_title");
			String remark1 = request.getParameter("p_Subject_remark1");
			String comment = request.getParameter("p_Subject_comment");
			String title = request.getParameter("p_Subgood_title");
			String subGoodId = request.getParameter("p_SubGood_id");
			String manypic = request.getParameter("manypic");
			subject.setTitle(title1);
			subject.setRemark1(remark1);
			subject.setComment(comment);
			subject.setUserName(user.getId());
			subject.setSubGoodId(subGoodId);
			subGood = this.subGoodService.queryById(subGoodId);
			subGood.setTitle(title);
			this.subGoodService.update(subGood);
			this.subjectService.update(subject);
			
			String ids = request.getParameter("ids");
			String ids1 = request.getParameter("ids1");
			if(ids!=null&&!("".equals(ids))){
				SubjectGoods sub = new SubjectGoods();
				sub.setSubjectId(subject.getId());
				List<SubjectGoods> sublist = this.subjectGoodsService.queryByVO(sub);
				for(SubjectGoods sg : sublist){
					this.subjectGoodsService.delete(sg.getId());
				}
				String [] idd = ids.split(",");
				for(int i = 0;i<idd.length; i++){
					String d = idd[i];
					subjectGoods.setGoodsId(d);
					subjectGoods.setSubjectId(subject.getId());
					this.subjectGoodsService.add(subjectGoods);
				}
			}
			if(ids1!=null&&!("".equals(ids1))){
				SubGoodGoods subgg = new SubGoodGoods();
				subgg.setSubgoodId(subGood.getId());
				List<SubGoodGoods> sgglist = this.subGoodGoodsService.queryByVO(subgg);
				for(SubGoodGoods sgg : sgglist){
					this.subGoodGoodsService.delete(sgg.getId());
				}
				String [] id1 = ids1.split(",");
				for(int i = 0;i<id1.length;i++){
					String dd = id1[i];
					subGoodGoods.setSubgoodId(subGood.getId());
					subGoodGoods.setGoodsId(dd);
					this.subGoodGoodsService.add(subGoodGoods);
				}
			}
			if (null != manypic && !"".equals(manypic)) {
				String[] manyid = manypic.split(",");
				for (String idm : manyid) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(idm);
					fileUpload.setFId(id);
					this.fileUploadService.update(fileUpload);
				}
			}
			showMessage(request, "修改专题成功");
		} catch (AppException e) {
			logger.error("修改专题[" + subject + "]失败", e);
			showMessage(request, "修改专题失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, subject);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, subject);
	}
	//详情
		@RequestMapping("/subject.detail.htm")
		public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv){
			String id ="";
			List<FileUpload> fulist = new ArrayList<FileUpload>();
			List<Spec> splist = new ArrayList<Spec>();
			List<Spec> splist1 = new ArrayList<Spec>();
			try {
				id = ServletRequestUtils.getStringParameter(request, "pid");
			} catch (ServletRequestBindingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(id!=""&&id!=null){
				Subject subject = this.subjectService.queryById(id);
				User user = this.userService.queryById(subject.getUserName());
				SubGood subGood = this.subGoodService.queryById(subject.getSubGoodId());
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFId(id);
				fulist = this.fileUploadService.queryByVO(fileUpload);
				SubjectGoods subjectGoods = new SubjectGoods();
				subjectGoods.setSubjectId(id);
				List<SubjectGoods> sglist = this.subjectGoodsService.queryByVO(subjectGoods);
				for(SubjectGoods sg : sglist){
					Goods goods = this.goodsService.queryById(sg.getGoodsId());
					Spec spec = this.specService.queryById(goods.getSpecId());
					Goods g = new Goods();
					g.setSpecId(spec.getId());
					List<Goods> gglist = this.goodsService.queryByVO(g);
					List<GoodCode> gclist = this.goodCodeService.queryByGoodId(goods.getId());
					List<Brand> brandlist =this.brandService.queryByTypeCode(spec.getType());
					Itsv_dictionary dic =  this.itsv_dictionaryService.queryByCode(spec.getType());
					String brand = "";
					for(GoodCode gc : gclist){
						for(Brand b : brandlist){
							if(gc.getCode().equals(b.getBrandcode())){
								brand = b.getName();
							}
						}
					}
					spec.setPrice(goods.getPrice());
					spec.setBrand(brand);
					spec.setType(dic.getDictname());
					spec.setSum(gglist.size()+"");
					splist.add(spec);
				}
				SubGoodGoods subGoodGoods = new SubGoodGoods();
				subGoodGoods.setSubgoodId(subGood.getId());
				List<SubGoodGoods> sgglist = this.subGoodGoodsService.queryByVO(subGoodGoods);
				for (SubGoodGoods sgg : sgglist){
					Goods goods = this.goodsService.queryById(sgg.getGoodsId());
					Spec spec = this.specService.queryById(goods.getSpecId());
					Goods g = new Goods();
					g.setSpecId(spec.getId());
					List<Goods> gglist = this.goodsService.queryByVO(g);
					List<GoodCode> gclist = this.goodCodeService.queryByGoodId(goods.getId());
					List<Brand> brandlist =this.brandService.queryByTypeCode(spec.getType());
					Itsv_dictionary dic =  this.itsv_dictionaryService.queryByCode(spec.getType());
					String brand = "";
					for(GoodCode gc : gclist){
						for(Brand b : brandlist){
							if(gc.getCode().equals(b.getBrandcode())){
								brand = b.getName();
							}
						}
					}
					spec.setPrice(goods.getPrice());
					spec.setBrand(brand);
					spec.setType(dic.getDictname());
					spec.setSum(gglist.size()+"");
					splist1.add(spec);
				}
				/*sb.append("<table  align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#69D7E4\" class=\"listWt\" style =\"width:60%;\"> ");
				sb.append("<tr><th colspan=\"4\">专题详情</th></tr>");
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">专题标题</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+subject.getTitle()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">登记时间</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+subject.getCreatetime()+"</td></tr>");
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">专题介绍</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+subject.getComment()+"</td></tr>");
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">操作用户</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+user.getRealName()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">浏览次数</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+subject.getRemark1()+"</td></tr>");	
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">专题相关标题</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"+subGood.getTitle()+"</td></tr>");	
				StringBuffer sbu = new StringBuffer();
				String picstart = "<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">图片信息</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">";  
				String picend = "</td></tr>";
				for(FileUpload fu : fulist){
						sbu.append("<img src = \""+fu.getDownloadPath()+"\"></img>");
				}
				sb.append(picstart+sbu.toString()+picend);
				sb.append("</table>");
				try {
					request.setAttribute("body", sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				mnv.addObject("splist", splist); 
				mnv.addObject("splist1", splist1); 
				mnv.addObject("subgood", subGood);
				mnv.addObject("user", user);
				mnv.addObject("fulist", fulist);
				mnv.addObject("data", subject);
				mnv.setViewName("/meitong/subject/detail");
			}else{
				showMessage(request, "没有查到该数据");			
				mnv.setViewName("admin/message");
			}
			return mnv;
		}
	/**
	 * 删除选中的subject
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] subjects = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : subjects) {
				this.subjectService.delete(id);
			}
			showMessage(request, "删除subject成功");
		} catch (AppException e) {
			logger.error("批量删除subject时失败", e);
			showMessage(request, "删除subject失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}
	@RequestMapping("/subject.beforeaddgoods.htm")
	public ModelAndView alertgooddetail(HttpServletRequest request, HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("品类");
		mnv.addObject("tplist", tplist);
		mnv.setViewName("meitong/subject/addgood");
		return mnv;
	}
	@RequestMapping("/subject.beforeaddgoods1.htm")
	public ModelAndView alertgooddetail1(HttpServletRequest request, HttpServletResponse response,ModelAndView mnv){
		List<Itsv_dictionary> tplist = this.itsv_dictionaryService
				.queryNextListByName("品类");
		mnv.addObject("tplist", tplist);
		mnv.setViewName("meitong/subject/addgood1");
		return mnv;
	}
	@RequestMapping("/subject.goodsdetail.htm")
	@ResponseBody
	public List<Spec> subjectAddGoodControl(HttpServletRequest request, HttpServletResponse response) throws AppException {
		List<Spec> list = new ArrayList<Spec>();
		String goodsName  = "";
		String goodsType = "";
		try {
			goodsName = ServletRequestUtils.getStringParameter(request, "goodsName");
			goodsType = ServletRequestUtils.getStringParameter(request, "goodsType");
			goodsName = URLDecoder.decode(goodsName,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Spec s = new Spec();
		s.setSpecName(goodsName);
		s.setType(goodsType);
		List<Spec> speclist = this.specService.queryByVO(s);
		for(Spec spec : speclist){
			Goods goods = this.goodsService.querySubjectByPrice(spec.getId());
			Goods g = new Goods();
			g.setSpecId(spec.getId());
			List<Goods> glist = this.goodsService.queryByVO(g);
			List<GoodCode> gclist = this.goodCodeService.queryByGoodId(goods.getId());
			List<Brand> brandlist =this.brandService.queryByTypeCode(spec.getType());
			Itsv_dictionary dic =  this.itsv_dictionaryService.queryByCode(spec.getType());
			String brand = "";
			for(GoodCode gc : gclist){
				for(Brand b : brandlist){
					if(gc.getCode().equals(b.getBrandcode())){
						brand = b.getName();
					}
				}
			}
			spec.setPrice(goods.getPrice());
			spec.setBrand(brand);
			spec.setSum(glist.size()+"");
			spec.setType(dic.getDictname());
			spec.setId(goods.getId());
			list.add(spec);
		}
		 return list;
		}
//	@RequestMapping("/subject.goodsdetail1.htm")
//	@ResponseBody
//	public Map<String ,Object> subjectAddGoodControl1(HttpServletRequest request, HttpServletResponse response) throws AppException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<Goods> goodslist = this.goodsService.querySubjectByPrice();
//		  for(Goods goods :goodslist){
//			  Spec spec = this.specService.queryById(goods.getSpecId());
//			 /* String sum = this.goodsService.querySumBySpecid(goods.getSpecId());*/
//			  Itsv_dictionary itsv = this.itsv_dictionaryService.queryByCode(spec.getType());
//			  List<GoodCode> gclist = this.goodCodeService.queryByGoodId(goods.getId());
//			  for (GoodCode gc : gclist){
//				  Brand brand = this.brandService.queryByCode(gc.getCode());
//				  if(brand!=null){
//					  goods.setBrand(brand.getName());
//				  }
//			  }
//			  /*goods.setSum(sum);*/
//			  goods.setPinlei(itsv.getDictname());
//			  CachePagedList records = PagedListTool.getEuiPagedList(request,
//						"goods");
//			  map.put("total", records.getTotalNum());
//			  map.put("rows", records.getSource());
//		  }
//		return ResponseUtils.sendMap(map);
//	}
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
}