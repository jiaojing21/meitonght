package com.itsv.annotation.rule.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.annotation.rule.bo.RuleService;
import com.itsv.annotation.rule.vo.Rule;
import com.itsv.annotation.voucher.bo.VoucherService;
import com.itsv.annotation.voucher.vo.Voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
/**
 * 说明：增加，修改，删除代金券规则的前端处理类
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
@Controller
public class RuleController extends BaseAnnotationController<Rule> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory.getLog(RuleController.class);

	//查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.rule";
	@Autowired
	private RuleService ruleService; //逻辑层对象
	@Autowired
	private VoucherService voucherService; //逻辑层对象

	
	
	


	//规则页面跳转
	@RequestMapping("rule.index.htm")
	protected ModelAndView beforeindex(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) {
		mnv.setViewName("meitong/rule/index");
		List<Rule> rule = this.ruleService.queryAll();
		List<Voucher> voucher = this.voucherService.queryAll();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbuser = new StringBuffer();
		StringBuffer sbfx = new StringBuffer();
		StringBuffer sbdl = new StringBuffer();
		StringBuffer sblxdl = new StringBuffer();
		if(rule.size()>0){
			for(Rule r : rule){
				if(r.getType().equals("0")){
					sbuser.append("<tr id = \"newuser\">");
					sbuser.append("<td colspan=\"3\">新注册用户</td>");
					sbuser.append("<td>发放</td>");
					sbuser.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbuser.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbuser.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbuser.append("</select></td>");
					sbuser.append("<td>元代金券</td>");
					sbuser.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbuser.append("<td>张</td><td></td></tr>");
				}else if(r.getType().equals("1")){
					sbfx.append("<tr id = \"fenxiang\">");
					sbfx.append("<td colspan=\"3\">分享</td>");
					sbfx.append("<td>发放</td>");
					sbfx.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbfx.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbfx.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbfx.append("</select></td>");
					sbfx.append("<td>元代金券</td>");
					sbfx.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbfx.append("<td>张</td><td></td></tr>");
				}else if(r.getType().equals("2")){
					sbdl.append("<tr class = \"dl\">");
					sbdl.append("<td colspan=\"2\"><div class=\"form-group float-left w140\"><input type=\"text\" name=\"datepicker\" class=\"form-control\" value=\""+r.getRuleCrux()+"\"/></div></td>");
					sbdl.append("<td>登陆</td>");
					sbdl.append("<td>发放</td>");
					sbdl.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbdl.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbdl.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbdl.append("</select></td>");
					sbdl.append("<td>元代金券</td>");
					sbdl.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbdl.append("<td>张</td>");
					sbdl.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
				}else if(r.getType().equals("3")){
					sblxdl.append("<tr class = \"lxdl\">");
					sblxdl.append("<td>连续登陆</td>");
					sblxdl.append("<td><input size=\"10px\" value=\""+r.getRuleCrux()+"\"></td>");
					sblxdl.append("<td>天</td>");
					sblxdl.append("<td>发放</td>");
					sblxdl.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sblxdl.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sblxdl.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sblxdl.append("</select></td>");
					sblxdl.append("<td>元代金券</td>");
					sblxdl.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sblxdl.append("<td>张</td>");
					sblxdl.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
				}
			}
			sb.append(sbuser.toString()+sbfx.toString()+sbdl.toString()+sblxdl.toString());
		}else{
				sb.append("<tr id = \"newuser\">");
				sb.append("<td colspan=\"3\">新注册用户</td>");
				sb.append("<td>发放</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>元代金券</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>张</td><td></td></tr>");
				
				sb.append("<tr id = \"fenxiang\">");
				sb.append("<td colspan=\"3\">分享</td>");
				sb.append("<td>发放</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>元代金券</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>张</td><td></td></tr>");

				sb.append("<tr class = \"dl\">");
				sb.append("<td colspan=\"2\"><div class=\"form-group float-left w140\"><input type=\"text\" name=\"datepicker\" class=\"form-control\" value=\"\"/></div></td>");
				sb.append("<td>登陆</td>");
				sb.append("<td>发放</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>元代金券</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>张</td>");
				sb.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");

				sb.append("<tr class = \"lxdl\">");
				sb.append("<td>连续登陆</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>天</td>");
				sb.append("<td>发放</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>元代金券</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>张</td>");
				sb.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
			}
		
		mnv.addObject("body", sb.toString());
		return mnv;
	}

	/**
	 * 保存新增代金券规则
	 */
	@RequestMapping("rule.add.htm")
	@ResponseBody
	public String saveAdd(HttpServletRequest request, HttpServletResponse response) {
		String resultparam ="success";
		try {
			List<Rule> rulelist = new ArrayList<Rule>();
			request.setCharacterEncoding("GBK");
			String result = request.getParameter("result");
			result = new String(result.getBytes("iso-8859-1"),"gbk");
//			Rule rule = null;
			JSONObject jo = JSONObject.fromObject(result);
			//新注册用户规则
			String newuse = jo.get("newuse").toString();
			String [] zhuce = newuse.split(",");
			Rule rulezhuce = new Rule();
			rulezhuce.setAmount(zhuce[2]);
			rulezhuce.setRuleCrux("");
			rulezhuce.setRuleDetail(zhuce[3]);
			rulezhuce.setRuleName(zhuce[0]);
			rulezhuce.setType(zhuce[4]);
			rulezhuce.setVoucherId(zhuce[1]);
			rulelist.add(rulezhuce);
			//新注册用户规则
			String fx = jo.get("fx").toString();
			String [] fenxiang = fx.split(",");
			Rule rulefx = new Rule();
			rulefx.setAmount(fenxiang[2]);
			rulefx.setRuleCrux("");
			rulefx.setRuleDetail(fenxiang[3]);
			rulefx.setRuleName(fenxiang[0]);
			rulefx.setType(fenxiang[4]);
			rulefx.setVoucherId(fenxiang[1]);
			rulelist.add(rulefx);
			//特定时间登陆
			String dl = jo.get("dl").toString();
			String [] strdl = dl.split("!");
			for(String s : strdl){
				if(!"".equals(s)){
					String [] str = s.split(",");
					Rule ruledl = new Rule();
					ruledl.setAmount(str[2]);
					ruledl.setRuleCrux(str[5]);
					ruledl.setRuleDetail(str[3]);
					ruledl.setRuleName(str[0]);
					ruledl.setType(str[4]);
					ruledl.setVoucherId(str[1]);
					rulelist.add(ruledl);
				}
			}
			//连续登陆
			String lxdl = jo.get("lxdl").toString();
			String [] strlxdl = lxdl.split("!");
			for(String s : strlxdl){
				if(!"".equals(s)){
					String [] str = s.split(",");
					Rule rulelxdl = new Rule();
					rulelxdl.setAmount(str[2]);
					rulelxdl.setRuleCrux(str[5]);
					rulelxdl.setRuleDetail(str[3]);
					rulelxdl.setRuleName(str[0]);
					rulelxdl.setType(str[4]);
					rulelxdl.setVoucherId(str[1]);
					rulelist.add(rulelxdl);
				}
			}
			this.ruleService.saverule(rulelist);
		} catch (Exception e) {
			resultparam = "error";
			e.printStackTrace();
		}
		

		return resultparam;
	}

	//指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
}