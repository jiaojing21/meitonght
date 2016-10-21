package com.itsv.annotation.vision.web;

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

import com.itsv.annotation.customer.bo.CustomerService;
import com.itsv.annotation.customer.vo.Customer;
import com.itsv.annotation.region.bo.RegionService;
import com.itsv.annotation.region.vo.Region;
import com.itsv.annotation.vision.bo.VisionService;
import com.itsv.annotation.vision.vo.Vision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除vision的前端处理类
 * 
 * @author swk
 * @since 2016-05-04
 * @version 1.0
 */
@Controller
public class VisionController extends BaseAnnotationController<Vision> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(VisionController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.vision";
	@Autowired
	private VisionService visionService; //逻辑层对象

	@Autowired
	private UserService userService; //逻辑层对象
	
	@Autowired
	private RegionService regionService; //逻辑层对象
	
	@Autowired
	private CustomerService customerService; //逻辑层对象
	
	public VisionController(){

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/vision/add");
		super.setIndexView("meitong/vision/index");
		super.setEditView("meitong/vision/edit");

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
		Vision vision = null;

		//如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request, getParamName(), "");
		if ("".equals(method)) {
			vision = param2Object(request);

			//将查询参数返回给页面
			mnv.addObject("condition", vision);
		} else {
			vision = new Vision();
		}

		this.visionService.queryByVO(records, vision);
	}  
	//查询前数据准备
	@RequestMapping("/vision.sh.htm")
	public ModelAndView queryVison (HttpServletRequest request ,HttpServletResponse response,ModelAndView mnv){
		mnv.setViewName(getIndexView());
		return mnv;
	}
	//查询列表
	@RequestMapping("/vision.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexControl(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> map = new HashMap<String ,Object>();
		String shmc = "";
		try {
			shmc = java.net.URLDecoder
					.decode(ServletRequestUtils.getStringParameter(request,
							"shmc", ""), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vision vision = new Vision();
		vision.setMood(shmc);
		CachePagedList records = PagedListTool.getEuiPagedList(request,
				"vision");
		this.visionService.queryByVO(records, vision);
		for(Vision v : (List<Vision>)records.getSource() ){
			Customer customer = this.customerService.queryById(v.getCusId());
			List<String> list = new ArrayList<String>();
			Region region = new Region();
			region.setRegionCode(v.getSeatof());
			List<Region> regionrest = this.regionService.queryByVO(region);
			if(regionrest.size()>0){
				region = regionrest.get(0);
				list.add(region.getRegionName());
				while(null!=region&&!region.getParentId().equals("0")){
					region = this.regionService.queryById(region.getParentId());
					list.add(region.getRegionName());
				}
			}
			StringBuffer sb = new StringBuffer();
			for(int i = list.size()-1;i >= 0;i--){
				sb.append(list.get(i));
			}
			v.setSeatof(sb.toString());
			v.setCusId(customer.getNickname());
		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
		}
	@RequestMapping("/vision.detail.htm")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		String id = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Vision vision = this.visionService.queryById(id);
			Customer customer = this.customerService.queryById(vision.getCusId());
			List<String> list = new ArrayList<String>();
			Region region = new Region();
			region.setRegionCode(vision.getSeatof());
			List<Region> regionrest = this.regionService.queryByVO(region);
			if(regionrest.size()>0){
				region = regionrest.get(0);
				list.add(region.getRegionName());
				while(null!=region&&!region.getParentId().equals("0")){
					region = this.regionService.queryById(region.getParentId());
					list.add(region.getRegionName());
				}
			}
			StringBuffer sb = new StringBuffer();
			for(int i = list.size()-1;i >= 0;i--){
				sb.append(list.get(i));
			}
			User user = this.userService.queryById(vision.getUserId());
			if("0".equals(vision.getAuditStatus())){
				vision.setZt("未审核");
			}else if("1".equals(vision.getAuditStatus())){
				vision.setZt("审核通过");
			}else if("2".equals(vision.getAuditStatus())){
				vision.setZt("已拒绝");
			}
			vision.setUserId(user.getRealName());
			vision.setCusId(customer.getNickname());
			vision.setSeatof(sb.toString());
			mnv.addObject("data", vision);
			mnv.setViewName("meitong/vision/detail");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message");
		}
		return mnv;
	}
	@RequestMapping("/vision.save.htm")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,ModelAndView mnv){
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		User user = adapter.getRealUser();
		String id = "";
		String type = "";
		try {
			id = ServletRequestUtils.getStringParameter(request, "pid");
			type = ServletRequestUtils.getStringParameter(request, "type");
		} catch (ServletRequestBindingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(id!=""&&id!=null){
			Vision vision = this.visionService.queryById(id);
			vision.setUserId(user.getId());
			vision.setAuditTime(new Date());
			vision.setAuditStatus(type);
			this.visionService.update(vision);
			showMessage(request, "操作成功");		
			mnv.setViewName("admin/message1");
		}else{
			showMessage(request, "没有查到该数据");			
			mnv.setViewName("admin/message1");
		}
		return mnv;
	}
	//显示增加vision页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv)
			throws AppException {
	}

	//显示修改vision页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Vision vision = this.visionService.queryById(id);
		if (null == vision) {
			showMessage(request, "未找到对应的vision记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, vision);
		}
	}

	/**
	 * 保存新增vision
	 */

	public ModelAndView saveAdd(HttpServletRequest request, HttpServletResponse response) {
		Vision vision = null;
		try {
			vision = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//ace8 2006.9.10
			//数据校验，如失败直接返回
			//if (!validate(request, vision)) {
			//	return new ModelAndView(getAddView(), WebConfig.DATA_NAME, vision);
			//}

			this.visionService.add(vision);

			showMessage(request, "新增vision成功");
		} catch (AppException e) {
			logger.error("新增vision[" + vision + "]失败", e);
			showMessage(request, "新增vision失败：" + e.getMessage(), e);

			//增加失败后，应将已填写的内容重新显示给vision
			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, vision);
		}

		return query(request, response);
	}

	/**
	 * 保存修改的vision
	 */

	public ModelAndView saveEdit(HttpServletRequest request, HttpServletResponse response) {
		Vision vision = null;
		try {
			vision = param2Object(request);

			//由于数据校验存在问题，暂不启用服务端的数据校验功能
			//数据校验，如失败直接返回
			//if (!validate(request, vision)) {
			//	return edit(request, response);
			//}

			this.visionService.update(vision);
			showMessage(request, "修改vision成功");
		} catch (AppException e) {
			logger.error("修改vision[" + vision + "]失败", e);
			showMessage(request, "修改vision失败：" + e.getMessage(), e);

			//修改失败后，重新显示修改页面
			return edit(request, response);
		}

		return query(request, response);
	}

	/**
	 * 删除选中的vision
	 */

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		String[] visions = ServletRequestUtils.getStringParameters(request, "p_id");
		//允许部分删除成功
		try {
			for (String id : visions) {
				this.visionService.delete(id);
			}
			showMessage(request, "删除vision成功");
		} catch (AppException e) {
			logger.error("批量删除vision时失败", e);
			showMessage(request, "删除vision失败：" + e.getMessage(), e);
		}

		return query(request, response);
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setVisionService(VisionService visionService) {
		this.visionService = visionService;
	}
}