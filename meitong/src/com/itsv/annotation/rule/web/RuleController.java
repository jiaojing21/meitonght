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
 * ˵�������ӣ��޸ģ�ɾ������ȯ�����ǰ�˴�����
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */
@Controller
public class RuleController extends BaseAnnotationController<Rule> {

	/**
	 * logger����
	 */
	protected static final Log logger = LogFactory.getLog(RuleController.class);

	//��ѯ�����session��Ĵ洢����
	private static final String QUERY_NAME = "query.rule";
	@Autowired
	private RuleService ruleService; //�߼������
	@Autowired
	private VoucherService voucherService; //�߼������

	
	
	


	//����ҳ����ת
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
					sbuser.append("<td colspan=\"3\">��ע���û�</td>");
					sbuser.append("<td>����</td>");
					sbuser.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbuser.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbuser.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbuser.append("</select></td>");
					sbuser.append("<td>Ԫ����ȯ</td>");
					sbuser.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbuser.append("<td>��</td><td></td></tr>");
				}else if(r.getType().equals("1")){
					sbfx.append("<tr id = \"fenxiang\">");
					sbfx.append("<td colspan=\"3\">����</td>");
					sbfx.append("<td>����</td>");
					sbfx.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbfx.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbfx.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbfx.append("</select></td>");
					sbfx.append("<td>Ԫ����ȯ</td>");
					sbfx.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbfx.append("<td>��</td><td></td></tr>");
				}else if(r.getType().equals("2")){
					sbdl.append("<tr class = \"dl\">");
					sbdl.append("<td colspan=\"2\"><div class=\"form-group float-left w140\"><input type=\"text\" name=\"datepicker\" class=\"form-control\" value=\""+r.getRuleCrux()+"\"/></div></td>");
					sbdl.append("<td>��½</td>");
					sbdl.append("<td>����</td>");
					sbdl.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sbdl.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sbdl.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sbdl.append("</select></td>");
					sbdl.append("<td>Ԫ����ȯ</td>");
					sbdl.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sbdl.append("<td>��</td>");
					sbdl.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
				}else if(r.getType().equals("3")){
					sblxdl.append("<tr class = \"lxdl\">");
					sblxdl.append("<td>������½</td>");
					sblxdl.append("<td><input size=\"10px\" value=\""+r.getRuleCrux()+"\"></td>");
					sblxdl.append("<td>��</td>");
					sblxdl.append("<td>����</td>");
					sblxdl.append("<td><select>");
					for(Voucher v : voucher){
						if(r.getVoucherId().equals(v.getId())){
							sblxdl.append("<option value = \""+v.getId()+"\" selected>"+v.getWorth()+"</option>");
						}else{
							sblxdl.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
						}
					}
					sblxdl.append("</select></td>");
					sblxdl.append("<td>Ԫ����ȯ</td>");
					sblxdl.append("<td><input size=\"10px\" value = \""+r.getAmount()+"\"></td>");
					sblxdl.append("<td>��</td>");
					sblxdl.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
				}
			}
			sb.append(sbuser.toString()+sbfx.toString()+sbdl.toString()+sblxdl.toString());
		}else{
				sb.append("<tr id = \"newuser\">");
				sb.append("<td colspan=\"3\">��ע���û�</td>");
				sb.append("<td>����</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>Ԫ����ȯ</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>��</td><td></td></tr>");
				
				sb.append("<tr id = \"fenxiang\">");
				sb.append("<td colspan=\"3\">����</td>");
				sb.append("<td>����</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>Ԫ����ȯ</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>��</td><td></td></tr>");

				sb.append("<tr class = \"dl\">");
				sb.append("<td colspan=\"2\"><div class=\"form-group float-left w140\"><input type=\"text\" name=\"datepicker\" class=\"form-control\" value=\"\"/></div></td>");
				sb.append("<td>��½</td>");
				sb.append("<td>����</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>Ԫ����ȯ</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>��</td>");
				sb.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");

				sb.append("<tr class = \"lxdl\">");
				sb.append("<td>������½</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>��</td>");
				sb.append("<td>����</td>");
				sb.append("<td><select>");
				for(Voucher v : voucher){
					sb.append("<option value = \""+v.getId()+"\">"+v.getWorth()+"</option>");
				}
				sb.append("</select></td>");
				sb.append("<td>Ԫ����ȯ</td>");
				sb.append("<td><input size=\"10px\"></td>");
				sb.append("<td>��</td>");
				sb.append("<td class=\"end2\"><a href=\"javascript:;\" style=\"text-decoration:none;\"><span onclick=\"javascript:add_tr(this)\">+</span><strong onclick=\"javascript:remove_tr(this)\">-</strong></a></td></tr>");
			}
		
		mnv.addObject("body", sb.toString());
		return mnv;
	}

	/**
	 * ������������ȯ����
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
			//��ע���û�����
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
			//��ע���û�����
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
			//�ض�ʱ���½
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
			//������½
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

	//ָ����ҳ��ѯ��¼��session�е�����
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** ����Ϊset,get���� */
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
}