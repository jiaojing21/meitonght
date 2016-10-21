package com.itsv.annotation.ratio.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

import com.itsv.annotation.company.vo.Company;
import com.itsv.annotation.company.vo.CompanyConver;
import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.Polytene;
import com.itsv.annotation.ratio.bo.RatioService;
import com.itsv.annotation.ratio.bo.RatioSubService;
import com.itsv.annotation.ratio.vo.Ratio;
import com.itsv.annotation.ratio.vo.RatioSub;
import com.itsv.annotation.ratio.vo.RatioSub_Conver;
import com.itsv.annotation.ratio.vo.Ratio_Conver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.BizFile;
/**
 * 说明：增加，修改，删除比例表的前端处理类
 * 
 * @author quyf
 * @since 2014-12-25
 * @version 1.0
 */
@Controller
@RequestMapping("/IRatio.htm")
public class IRatioController extends BaseCURDController<Ratio> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(IRatioController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.ratio";
	@Autowired
	private RatioService ratioService; //逻辑层对象
	@Autowired
	private RatioSubService ratioSubService; //逻辑层对象
	
	private static final String ENCODE = "UTF-8";
	
	public ModelAndView getRatioTime(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		String type = null;
		String json = this.receiverPost(request);
		json = this.decodeWrite(json);
		if(StringUtils.isNotBlank(json)){
			Map<String,Object> m = getObject(json);
			if(m.containsKey("type")){			
				type = m.get("type").toString();
			}
			if(StringUtils.isNotBlank(type)){
				Ratio ratio = new Ratio();
				ratio.setType(type);
				List<Ratio> list = this.ratioService.queryByVO(ratio);
				List<String> slist = new LinkedList<String>();
				Set<String> set = new HashSet<String>();
				for(int i=0;i<list.size();i++){
					set.add(list.get(i).getTime());	
				}
				Iterator iterator = set.iterator();
				String times ="";
				while(iterator.hasNext()){					
					times+=","+(String) iterator.next();
				}
				maps.put("times", times.subSequence(1, times.length()));
				this.encodeWrite(JSONObject.fromObject(maps), response);
			}else{
				maps.put("message", "参数错误");
				this.encodeWrite(JSONObject.fromObject(maps), response);
			}
		}else{
			maps.put("message", "没有参数错误");
			this.encodeWrite(JSONObject.fromObject(maps), response);
		}
		return null;
	}

	public ModelAndView getRatio(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object> maps = new HashMap<String, Object>();
		String time = null;
		String type = null;
		String subtype = null;
		String json = this.receiverPost(request);
		json = this.decodeWrite(json);
		if(StringUtils.isNotBlank(json)){
			Map<String,Object> m = getObject(json);
			if(m.containsKey("time")&&m.containsKey("type")&&m.containsKey("subtype")){
				time = m.get("time").toString();
				type = m.get("type").toString();
				subtype = m.get("subtype").toString();
			}
			if(StringUtils.isNotBlank(subtype)&&StringUtils.isNotBlank(time)&&StringUtils.isNotBlank(type)){
				
				Ratio ratio = new Ratio();
				ratio.setSubtype(subtype);
				ratio.setTime(time);
				ratio.setType(type);
				List<Ratio> list = this.ratioService.queryByVO(ratio);
				if(list.size()>0){
					Ratio_Conver rc = new Ratio_Conver();
					Ratio ra = list.get(0);
					rc.setContent(ra.getContent());
					rc.setId(ra.getId());
					rc.setPicurl(ra.getPicurl());
					rc.setSubtype(ra.getSubtype());
					rc.setTime(ra.getTime());
					rc.setTitle(ra.getTitle());
					rc.setType(ra.getType());
					List<RatioSub_Conver> rsclist = new ArrayList<RatioSub_Conver>();
					RatioSub rs = new RatioSub();
					rs.setRatioid(ra.getId());
					List<RatioSub> rslist = this.ratioSubService.queryByVO(rs);
					for(RatioSub rsb : rslist){
						RatioSub_Conver rsc = new RatioSub_Conver();
						rsc.setId(rsb.getId());
						rsc.setRname(rsb.getRname());
						rsc.setDataone(rsb.getDataone());
						rsc.setDatatwo(rsb.getDatatwo());
						rsclist.add(rsc);
					}
					rc.setList(rsclist);
					this.encodeWrite(JSONObject.fromObject(rc), response);
				}else{
					maps.put("message", "没有数据");
					this.encodeWrite(JSONObject.fromObject(maps), response);
				}
				
			}else{
				maps.put("message", "参数错误");
				this.encodeWrite(JSONObject.fromObject(maps), response);
			}
		}else{
			maps.put("message", "没有参数错误");
			this.encodeWrite(JSONObject.fromObject(maps), response);
		}	
		return null;
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

	/** 以下为set,get方法 */
	public void setRatioService(RatioService ratioService) {
		this.ratioService = ratioService;
	}
}