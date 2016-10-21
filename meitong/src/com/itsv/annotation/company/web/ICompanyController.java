package com.itsv.annotation.company.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.annotation.company.bo.CompanyService;
import com.itsv.annotation.company.bo.ImpaexpService;
import com.itsv.annotation.company.bo.PolyteneService;
import com.itsv.annotation.company.vo.Company;
import com.itsv.annotation.company.vo.CompanyConver;
import com.itsv.annotation.company.vo.DataTypeConver;
import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.ImpaexpConver;
import com.itsv.annotation.company.vo.PolyDate;
import com.itsv.annotation.company.vo.Polytene;
import com.itsv.annotation.company.vo.PolyteneConver;
import com.itsv.gbp.core.util.DateTool;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

/**
 * 说明：增加，修改，删除文章栏目的前端处理类
 * 
 * @author grk
 * @since 2014-08-08
 * @version 1.0
 */	
@Controller
@RequestMapping("/IShzsydpt.htm")
public class ICompanyController extends BaseCURDController<Company>{

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(ICompanyController.class);
	
	//查询结果在Session里的储存名称
	private static final String QUERY_NAME ="query.company";
	@Autowired
	private CompanyService companyService; //逻辑层对象
	
	@Autowired
	private ImpaexpService impaexpService; //进出口的逻辑层对象
	  
	@Autowired
	private PolyteneService polyteneService; //聚乙烯产能逻辑层对象
	@Resource(name = "dictionary.itsv_dictionaryService")
	private Itsv_dictionaryService itsv_dictionaryService; // 逻辑层对象
	private static final String ENCODE = "UTF-8";
	/**
	 * 获取数据类型
	 */
	public ModelAndView getDataType(HttpServletRequest request,HttpServletResponse response)throws Exception {
		List<Itsv_dictionary> dicList = this.itsv_dictionaryService.queryNextListByCode("004");
		List<DataTypeConver> dataList = new ArrayList<DataTypeConver>();
		for(int i=0;i<dicList.size();i++){
			Itsv_dictionary dic = dicList.get(i);
			DataTypeConver data = new DataTypeConver();
			data.setId(dic.getId());
			data.setName(dic.getDictname());
			data.setCode(dic.getHardcode());
			data.setIndex(dic.getDictno()+"");
			dataList.add(data);
		}
		this.encodeWrite(JSONArray.fromObject(dataList), response);
		return null;
		
	}
	/**
	 * 获取企业海关信息
	 */
	public ModelAndView getCompany(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		String name = null;
		String type = null;
		String code = null;
		String json = this.receiverPost(request);
		json = this.decodeWrite(json);
		if(StringUtils.isNotBlank(json)){
			Map<String,Object> m = getObject(json);
			if(m.containsKey("name")&&m.containsKey("type")&&m.containsKey("code")){
				name = m.get("name").toString();
				type = m.get("type").toString();
				code = m.get("code").toString();
			}
			if(m.containsKey("code")){
				code = m.get("code").toString();
			}
		}
		List<CompanyConver> ccList = new ArrayList<CompanyConver>();
		if(StringUtils.isNotBlank(code)){
			Company company = new Company();
			company.setCompanyname(name);
			company.setType(type);
			company.setCode(code);
			List<Company> comList = this.companyService.queryByVO(company);
			for (Company ac : comList) {
				CompanyConver cc = new CompanyConver();
				cc.setId(ac.getId());
				cc.setCompanyname(ac.getCompanyname());
				cc.setCompanyx(ac.getCompanyx());
				cc.setCompanyy(ac.getCompanyy());
				cc.setType(ac.getType());
				if (ac.getType().equals("2")) {
					// "LDPE"进出口类型
					String time = this.getNewTime(ac, "LDPE");
					Impaexp ipe = new Impaexp();
					ipe.setCompanyid(ac.getId());
					ipe.setHtime(time);
					ipe.setType("LDPE");
					List<Impaexp> ldpe = this.impaexpService.queryByVO(ipe);
					if (ldpe.size() > 0) {
						Impaexp ipen = ldpe.get(0);
						String itype = "0";
						if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "1";
						} else if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isBlank(ipen.getImportse())) {
							itype = "2";
						} else if (StringUtils.isBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "3";
						}
						cc.setLDPEtype(itype);
					} else {
						cc.setLDPEtype("0");
					}
					// "HDPE"进出口类型
					time = this.getNewTime(ac, "HDPE");
					ipe = new Impaexp();
					ipe.setCompanyid(ac.getId());
					ipe.setHtime(time);
					ipe.setType("HDPE");
					List<Impaexp> hdpe = this.impaexpService.queryByVO(ipe);
					if (hdpe.size() > 0) {
						Impaexp ipen = hdpe.get(0);
						String itype = "0";
						if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "1";
						} else if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isBlank(ipen.getImportse())) {
							itype = "2";
						} else if (StringUtils.isBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "3";
						}
						cc.setHDPEtype(itype);
					} else {
						cc.setHDPEtype("0");
					}
					// "HDPE"进出口类型
					time = this.getNewTime(ac, "LLDPE");
					ipe = new Impaexp();
					ipe.setCompanyid(ac.getId());
					ipe.setHtime(time);
					ipe.setType("LLDPE");
					List<Impaexp> lldpe = this.impaexpService.queryByVO(ipe);
					if (lldpe.size() > 0) {
						Impaexp ipen = lldpe.get(0);
						String itype = "0";
						if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "1";
						} else if (StringUtils.isNotBlank(ipen.getExportse())
								&& StringUtils.isBlank(ipen.getImportse())) {
							itype = "2";
						} else if (StringUtils.isBlank(ipen.getExportse())
								&& StringUtils.isNotBlank(ipen.getImportse())) {
							itype = "3";
						}
						cc.setLLDPEtype(itype);
					} else {
						cc.setLLDPEtype("0");
					}

				}
				if (!"全国".equals(cc.getCompanyname())) {
					ccList.add(cc);
				}
			}
		}
		this.encodeWrite(JSONArray.fromObject(ccList), response);
		return null;
	}
	/**
	 * 获取企业产能
	 */
	public ModelAndView getPolytene(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		try{ 
			String json = this.receiverPost(request);
			json = this.decodeWrite(json);
			if(StringUtils.isNotBlank(json)){
				Map<String,Object> m = getObject(json);
				String id = "";
				//String time = "";
				if(m.containsKey("id")){
					id = m.get("id").toString();
				}
				if(StringUtils.isNotBlank(id)){
					Company cpt = this.companyService.queryById(id);
					String time = this.getNewTime(cpt, null);
					Polytene ple = new Polytene();
					ple.setCompanyid(id);
					ple.setPtime(time);
					List<Polytene> plist = this.polyteneService.queryByVO(ple);
					List<PolyteneConver> pclist = new ArrayList<PolyteneConver>();
					for(Polytene pt : plist){
						PolyteneConver pc = new PolyteneConver();
						pc.setId(pt.getId()); 
						pc.setCompanyid(pt.getCompanyid());
						pc.setCompanyname(this.companyService.queryById(pt.getCompanyid()).getCompanyname());
						pc.setCapacity(pt.getCapacity());
						pc.setProduction(pt.getProduction());
						pc.setPtime(pt.getPtime());
						pc.setType(pt.getType());
						pclist.add(pc);
					}
					Company cp = new Company();
					cp.setType("1");
					List<Company> cplist =this.companyService.queryByVO(cp);
					Company cpqg =null;
					for(int i=0;i<cplist.size();i++){
						if("全国".equals(cplist.get(i).getCompanyname())){
							cpqg = cplist.get(i);
							break;
						}
					}
					if(cpqg!=null){
						Polytene pleqg = new Polytene();
						pleqg.setCompanyid(cpqg.getId());
						pleqg.setPtime(time);
						List<Polytene> pqg = this.polyteneService.queryByVO(pleqg);
						for(Polytene pt : pqg){
							PolyteneConver pc = new PolyteneConver();
							pc.setId(pt.getId());
							pc.setCompanyid(pt.getCompanyid());
							pc.setCompanyname("quanguo");
							pc.setCapacity(pt.getCapacity());
							pc.setProduction(pt.getProduction());
							pc.setPtime(pt.getPtime());
							pc.setType(pt.getType());
							pclist.add(pc);							
						}
					}
					this.encodeWrite(JSONArray.fromObject(pclist), response);
				}else{
					maps.put("message", "0");
					this.encodeWrite(JSONObject.fromObject(maps), response);
				}
			}
			
		}catch (Exception e) {		
			maps.put("message", "0");
			this.encodeWrite(JSONObject.fromObject(maps), response);		
			System.out.println(e);
		}		
		
		return null;
	}
	/**
	 * 获取进出口信息
	 */
	public ModelAndView getImpaexp(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		try{ 
			String json = this.receiverPost(request);
			json = this.decodeWrite(json);
			if(StringUtils.isNotBlank(json)){
				Map<String,Object> m = getObject(json);
				String id = "";

				if(m.containsKey("id")){
					id = m.get("id").toString();
				}
				if(StringUtils.isNotBlank(id)){
					Company cpt = this.companyService.queryById(id);
					String time = this.getNewTime(cpt, null);
					Impaexp ipe = new Impaexp();
					ipe.setCompanyid(id);
					ipe.setHtime(time);
					List<Impaexp> plist = this.impaexpService.queryByVO(ipe);
					List<ImpaexpConver> pclist = new ArrayList<ImpaexpConver>();
					for(Impaexp pt : plist){
						ImpaexpConver pc = new ImpaexpConver();
						pc.setId(pt.getId());
						pc.setCompanyid(pt.getCompanyid());
						pc.setCompanyname(this.companyService.queryById(pt.getCompanyid()).getCompanyname());
						pc.setHtime(pt.getHtime());
						pc.setType(pt.getType());
						pc.setExportse(pt.getExportse());
						pc.setExportsl(pt.getExportsl());
						pc.setImportse(pt.getImportse());
						pc.setImportsl(pt.getImportsl());
						pclist.add(pc);
					}
					Company cp = new Company();
					cp.setType("2");
					List<Company> cplist =this.companyService.queryByVO(cp);
					Company cpqg =null;
					for(int i=0;i<cplist.size();i++){
						if("全国".equals(cplist.get(i).getCompanyname())){
							cpqg = cplist.get(i);
							break;
						}
					}
					if(cpqg!=null){
						Impaexp pleqg = new Impaexp();
						pleqg.setCompanyid(cpqg.getId());
						pleqg.setHtime(time);
						List<Impaexp> pqg = this.impaexpService.queryByVO(pleqg);
						for(Impaexp pt : pqg){
							ImpaexpConver pc = new ImpaexpConver();
							pc.setId(pt.getId());
							pc.setCompanyid(pt.getCompanyid());
							pc.setCompanyname("quanguo");		
							pc.setHtime(pt.getHtime());
							pc.setType(pt.getType());
							pc.setExportse(pt.getExportse());
							pc.setExportsl(pt.getExportsl());
							pc.setImportse(pt.getImportse());
							pc.setImportsl(pt.getImportsl());
							pclist.add(pc);							
						}
					}
					this.encodeWrite(JSONArray.fromObject(pclist), response);
				}else{
					maps.put("message", "0");
					this.encodeWrite(JSONObject.fromObject(maps), response);
				}
			}
			
		}catch (Exception e) {		
			maps.put("message", "0");
			this.encodeWrite(JSONObject.fromObject(maps), response);		
			System.out.println(e);
		}		
		
		return null;
	}
	/**
	 * 获取12月份数据
	 */
	public ModelAndView getDets(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		try{ 
			String json = this.receiverPost(request);
			json = this.decodeWrite(json);
			if(StringUtils.isNotBlank(json)){
				Map<String,Object> m = getObject(json);
				String id = "";

				if(m.containsKey("id")){
					id = m.get("id").toString();
				}
				if(StringUtils.isNotBlank(id)){
					/*
					Company cpt = this.companyService.queryById(id);
					String time = this.getNewTime(cpt, null);
					Impaexp ipe = new Impaexp();
					ipe.setCompanyid(id);
					ipe.setHtime(time);
					List<Impaexp> plist = this.impaexpService.queryByVO(ipe);
					List<ImpaexpConver> pclist = new ArrayList<ImpaexpConver>();
					for(Impaexp pt : plist){
						ImpaexpConver pc = new ImpaexpConver();
						pc.setId(pt.getId());
						pc.setCompanyid(pt.getCompanyid());
						pc.setCompanyname(this.companyService.queryById(pt.getCompanyid()).getCompanyname());
						pc.setHtime(pt.getHtime());
						pc.setType(pt.getType());
						pc.setExportse(pt.getExportse());
						pc.setExportsl(pt.getExportsl());
						pc.setImportse(pt.getImportse());
						pc.setImportsl(pt.getImportsl());
						pclist.add(pc);
					}
					Company cp = new Company();
					cp.setType("2");
					List<Company> cplist =this.companyService.queryByVO(cp);
					Company cpqg =null;
					for(int i=0;i<cplist.size();i++){
						if("全国".equals(cplist.get(i).getCompanyname())){
							cpqg = cplist.get(i);
							break;
						}
					}
					if(cpqg!=null){
						Impaexp pleqg = new Impaexp();
						pleqg.setCompanyid(cpqg.getId());
						pleqg.setHtime(time);
						List<Impaexp> pqg = this.impaexpService.queryByVO(pleqg);
						for(Impaexp pt : pqg){
							ImpaexpConver pc = new ImpaexpConver();
							pc.setId(pt.getId());
							pc.setCompanyid(pt.getCompanyid());
							pc.setCompanyname("全国");		
							pc.setHtime(pt.getHtime());
							pc.setType(pt.getType());
							pc.setExportse(pt.getExportse());
							pc.setExportsl(pt.getExportsl());
							pc.setImportse(pt.getImportse());
							pc.setImportsl(pt.getImportsl());
							pclist.add(pc);							
						}
					}*/
					List<PolyDate> plist = new ArrayList<PolyDate>();
					int month=DateTool.getCurrentMonth();
					int year = DateTool.getCurrentYear();
					for(int i=1;i<=12;i++){
						String dateTime = "";
						if((month-i)<1){
							dateTime = (year-1)+"."+(month-i+12);
						}else{
							dateTime = year +"."+(month-i);
						}
						PolyDate pd = new PolyDate();
						pd.setDatetime(dateTime);
						pd.setBysj("0");
						pd.setLjsj("0");
						pd.setId(id);
						plist.add(pd);
					}
					this.encodeWrite(JSONArray.fromObject(plist), response);
				}else{
					maps.put("message", "0");
					this.encodeWrite(JSONObject.fromObject(maps), response);
				}
			}
			
		}catch (Exception e) {		
			maps.put("message", "0");
			this.encodeWrite(JSONObject.fromObject(maps), response);		
			System.out.println(e);
		}		
		
		return null;
	}
	public String getNewTime(Company cp,String jyxtype){
		String time = null;
		if("1".equals(cp.getType())){//
			Polytene pt = new Polytene();
			pt.setCompanyid(cp.getId());
			pt.setType(jyxtype);
			List<Polytene> ptlist = this.polyteneService.queryByVO(pt);
			if(ptlist.size()>0){
				time = ptlist.get(0).getPtime();
			}
		}
		if("2".equals(cp.getType())){//
			Impaexp impaexp = new Impaexp();
			impaexp.setCompanyid(cp.getId());
			impaexp.setType(jyxtype);
			List<Impaexp> implist = this.impaexpService.queryByVO(impaexp); 
			if(implist.size()>0){
				time = implist.get(0).getHtime();
			}
		}		
		return time;
	}
	/**
	 * 获取请求接口流
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String receiverPost(HttpServletRequest request) throws IOException{
		BufferedReader buff = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = buff.readLine()) != null){
			sb.append(line);
		}
		buff.close();
		return sb.toString();
	}
	
	public Map<String,Object> getObject(String json){
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String,Object> map = new HashMap<String,Object>();;
		for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
			
			String key = (String) iter.next();
			String jsons =  jsonObject.get(key).toString();
			map.put(key, jsons);
		}
		return map;
	}
	/**
	 * 编码
	 * @param o
	 * @param response
	 */
	public void encodeWrite(Object o,HttpServletResponse response){
		String temp = "";
		try {
			PrintWriter pw = response.getWriter();
			temp = URLEncoder.encode(o.toString(), ENCODE);
			pw.write(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 解码 
	 * @param o
	 */
	public String decodeWrite(Object o){
		String temp = "";
		try {
			temp = java.net.URLDecoder.decode(o.toString(),ENCODE);
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}
	public void setItsv_dictionaryService(
			Itsv_dictionaryService itsvDictionaryService) {
		itsv_dictionaryService = itsvDictionaryService;
	}
	
}