package com.itsv.annotation.goods.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.orm.paged.CachePagedList;
import com.itsv.gbp.core.util.PagedListTool;
import com.itsv.gbp.core.util.ResponseUtils;
import com.itsv.gbp.core.web.WebConfig;

import com.itsv.gbp.core.web.springmvc.BaseAnnotationController;
import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

/**
 * 说明：增加，修改，删除goods的前端处理类
 * 
 * @author swk
 * @since 2016-03-28
 * @version 1.0
 */
@Controller
public class GoodsController extends BaseAnnotationController<Goods> {

	/**
	 * logger对象
	 */
	protected static final Log logger = LogFactory
			.getLog(GoodsController.class);

	// 查询结果在session里的存储名称
	private static final String QUERY_NAME = "query.goods";
	@Autowired
	private GoodsService goodsService; // 逻辑层对象

	@Autowired
	private Itsv_dictionaryService itsv_dictionaryService;// 逻辑层对象

	@Autowired
	private SpecService specService;// 逻辑层对象

	@Autowired
	private BrandService brandService;// 逻辑层对象

	@Autowired
	private FileUploadService fileUploadService;// 逻辑层对象

	@Autowired
	private GoodCodeService goodCodeService;// 逻辑层对象

	public GoodsController() {

		super.setDefaultCheckToken(true);
		super.setAddView("meitong/goods/add");
		super.setIndexView("meitong/goods/index");
		super.setEditView("meitong/goods/edit");

	}

	// 覆盖父类方法，默认执行query()，分页显示数据

	@Override
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return super.query(request, response);
	}

	// 实现分页查询操作
	@Override
	protected void doQuery(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			CachePagedList records) {
		Goods goods = null;

		// 如果是保存后dispatch到列表页面，则不处理传来的参数
		String method = ServletRequestUtils.getStringParameter(request,
				getParamName(), "");
		if ("".equals(method)) {
			goods = param2Object(request);

			// 将查询参数返回给页面
			mnv.addObject("condition", goods);
		} else {
			goods = new Goods();
		}

		this.goodsService.queryByVO(records, goods);
	}

	// 显示增加goods页面前，准备相关数据

	@Override
	protected void beforeShowAdd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) throws AppException {
	}

	// 显示修改goods页面前，准备数据

	@Override
	protected void beforeShowEdit(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		String id = ServletRequestUtils.getStringParameter(request, "p_id", "");
		Goods goods = this.goodsService.queryById(id);
		if (null == goods) {
			showMessage(request, "未找到对应的goods记录。请重试");
			mnv = query(request, response);
		} else {
			mnv.addObject(WebConfig.DATA_NAME, goods);
		}
	}

	@RequestMapping("/goods.beforeadd.htm")
	public ModelAndView deforeadd(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv, String code) {
		// String code = request.getParameter("code");
		Brand brand = new Brand();
		brand.setFlag("1");
		List<Brand> brandlist = this.brandService.queryByVO(brand);
		mnv.addObject("brandlist", brandlist);
		mnv.addObject("code", code);
		mnv.setViewName(getAddView());
		return mnv;
	}

	/**
	 * 保存新增goods
	 */
	@RequestMapping("/goods.saveadd.htm")
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mnv = new ModelAndView();
		Goods goods = null;
		Spec spec = new Spec();
		try {
			goods = param2Object(request);
			String name = request.getParameter("goodName");
			String format = request.getParameter("format");
			String goodsNature = request.getParameter("goodsNature");
			String code = request.getParameter("lb");
			String brandId = request.getParameter("brandid");

			String flag = request.getParameter("flag");
			String onlypic = request.getParameter("onlypic");
			String manypic = request.getParameter("manypic");

			String remark = request.getParameter("remark");
			String remark2 = request.getParameter("remark2");
			
			String state = request.getParameter("state");
			
			List<Itsv_dictionary> list = this.itsv_dictionaryService
					.queryNextListByCode(code);
			spec.setSpecName(name);
			spec.setType(code);
			spec.setRemark(remark);
			spec.setSpecName(name);
			spec.setBrandCode(brandId);
			this.specService.add(spec);
			if(null!=goodsNature && !"".equals(goodsNature)){
				String[] result = goodsNature.split("\\!");
				if (result != null) {
					for (String j : result) {
						if (!j.equals("")) {
							String[] k = j.split("\\,");
							String resname = "";
							for (int i = 0; i < k.length - 2; i++) {
								resname += "  " + k[i];
							}
							Goods gs = new Goods();
							gs.setPrice(k[k.length - 2]);
							gs.setGoodsNumber(k[k.length - 1]);
							gs.setSpecId(spec.getId());
							gs.setName(name + resname);
							Date date = new Date();
							gs.setGoodNo("SP" + date.getTime());
							gs.setFlag(flag);
							gs.setRemark1("0");
							gs.setRemark2(remark2);
							gs.setState(state);
							this.goodsService.add(gs);

							GoodCode goodCodepp = new GoodCode();
							goodCodepp.setGoodId(gs.getId());
							goodCodepp.setCode(brandId);
							this.goodCodeService.add(goodCodepp);

							String[] selcode = format.split(",");
							for (String cd : selcode) {
								GoodCode goodCodecd = new GoodCode();
								goodCodecd.setGoodId(gs.getId());
								goodCodecd.setCode(cd);
								this.goodCodeService.add(goodCodecd);
							}

							for (int i = 0; i < k.length - 2; i++) {
								String coderes = "";
								for (Itsv_dictionary m : list) {
									List<Itsv_dictionary> itsvlisttwo = this.itsv_dictionaryService
											.queryNextListByCode(m.getCode());
									for (Itsv_dictionary res : itsvlisttwo) {
										if (k[i].equals(res.getDictname())) {
											coderes = res.getCode();
										}
									}
								}
								if (!"".equals(coderes)) {
									GoodCode goodCode = new GoodCode();
									goodCode.setGoodId(gs.getId());
									goodCode.setCode(coderes);
									this.goodCodeService.add(goodCode);
								}
							}
						}

					}
				} 
			}else {
				String dpprice = request.getParameter("dpprice");
				Goods gs = new Goods();
				gs.setPrice(dpprice);
				gs.setGoodsNumber("1");
				gs.setSpecId(spec.getId());
				gs.setName(name);
				Date date = new Date();
				gs.setGoodNo("SP" + date.getTime());
				gs.setFlag(flag);
				gs.setRemark1("0");
				gs.setRemark2(remark2);
				this.goodsService.add(gs);

				GoodCode goodCodepp = new GoodCode();
				goodCodepp.setGoodId(gs.getId());
				goodCodepp.setCode(brandId);
				this.goodCodeService.add(goodCodepp);

				String[] selcode = format.split(",");
				for (String cd : selcode) {
					GoodCode goodCodecd = new GoodCode();
					goodCodecd.setGoodId(gs.getId());
					goodCodecd.setCode(cd);
					this.goodCodeService.add(goodCodecd);
				}
			}
			
			
			if (null != onlypic && !"".equals(onlypic)) {
				FileUpload fileUpload = this.fileUploadService
						.queryById(onlypic);
				fileUpload.setFId(spec.getId());
				this.fileUploadService.update(fileUpload);
			}
			if (null != manypic && !"".equals(manypic)) {
				String[] manyid = manypic.split(",");
				for (String id : manyid) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(id);
					fileUpload.setFId(spec.getId());
					this.fileUploadService.update(fileUpload);
				}
			}

			showMessage(request, "新增商品成功");
			mnv.addObject("code", code);
			mnv.setViewName(getAddView());
		} catch (AppException e) {
			logger.error("新增商品[" + goods + "]失败", e);
			showMessage(request, "新增商品失败：" + e.getMessage(), e);

			// 增加失败后，应将已填写的内容重新显示给goods

			return new ModelAndView(getAddView(), WebConfig.DATA_NAME, goods);
		}
		return mnv;

	}

	// 查询前准备数据
	@RequestMapping("/goods.query.htm")
	public ModelAndView querygoods(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv) {
		mnv.setViewName(getIndexView());
		return mnv;
	}

	// 查询列表
	@RequestMapping("/goods.BeforeData.htm")
	@ResponseBody
	public Map<String, Object> indexAppControl(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		Goods goods = new Goods();
		CachePagedList records = PagedListTool
				.getEuiPagedList(request, "goods");
		this.goodsService.queryByVO(records, goods);
		for (Goods good : (List<Goods>) records.getSource()) {
			Spec spec = this.specService.queryById(good.getSpecId());
			if (null != spec) {
				Itsv_dictionary idic = new Itsv_dictionary();
				idic.setCode(spec.getType());
				List<Itsv_dictionary> itsv_dictionary = this.itsv_dictionaryService
						.queryByVO(idic);
				if (itsv_dictionary.size() > 0) {
					good.setPinlei(itsv_dictionary.get(0).getDictname());
				}
			}

		}
		map.put("total", records.getTotalNum());
		map.put("rows", records.getSource());
		return ResponseUtils.sendMap(map);
	}

	// 查询商品详细
	@RequestMapping("/good.detail.htm")
	public ModelAndView detailData(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			@RequestParam String id) {
		StringBuffer sb = new StringBuffer();
		Goods goods = goodsService.queryById(id);
		GoodCode gc = new GoodCode();
		gc.setGoodId(id);
		List<GoodCode> gclist = this.goodCodeService.queryByVO(gc);

		List<FileUpload> fulist = new ArrayList<FileUpload>();
		// 查询品牌
		String brandName = "";
		for (GoodCode code : gclist) {
			Brand brand = new Brand();
			brand.setBrandcode(code.getCode());
			List<Brand> bdlist = this.brandService.queryByVO(brand);
			if (bdlist.size() > 0) {
				brandName = bdlist.get(0).getName();
				break;
			}
		}
		// 查询类别
		String lbname = "";
		Spec spec = this.specService.queryById(goods.getSpecId());
		if (null != spec) {
			Itsv_dictionary idic = new Itsv_dictionary();
			idic.setCode(spec.getType());
			List<Itsv_dictionary> itsv_dictionary = this.itsv_dictionaryService
					.queryByVO(idic);
			if (itsv_dictionary.size() > 0) {
				lbname = itsv_dictionary.get(0).getDictname();
			}

			FileUpload fileUpload = new FileUpload();
			fileUpload.setFId(spec.getId());
			fulist = this.fileUploadService.queryByVO(fileUpload);
		}

		// 判别标签
		String flag = "";
		if (goods.getFlag().equals("0")) {
			flag = "普通";
		} else if (goods.getFlag().equals("1")) {
			flag = "新品";
		} else if (goods.getFlag().equals("2")) {
			flag = "热销商品";
		}
		// 判别上架状态
		String zt = "";
		if (goods.getRemark1().equals("0")) {
			zt = "未上架";
		} else if (goods.getRemark1().equals("1")) {
			zt = "已上架";
		} else if (goods.getRemark1().equals("2")) {
			zt = "已下架";
		}
		sb.append("<table  align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#69D7E4\" class=\"listWt\" style =\"width:60%;\"> ");
		sb.append("<tr><th colspan=\"4\">商品详情</th></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品名称</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getName() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">包装清单</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getRemark2() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">类别</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ lbname + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">品牌</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ brandName + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">编码</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getGoodNo() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">标签</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ flag + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品状态</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ zt + "</td></tr>");
		for (GoodCode code : gclist) {
			Itsv_dictionary itsv = new Itsv_dictionary();
			itsv.setCode(code.getCode());
			List<Itsv_dictionary> itsvlist = this.itsv_dictionaryService
					.queryByVO(itsv);
			String dicname = "";
			String aryname = "";
			if (null != itsvlist && itsvlist.size() > 0) {
				dicname = itsvlist.get(0).getDictname();
				// 查询上级COde
				Itsv_dictionary ary = new Itsv_dictionary();
				ary.setCode(itsvlist.get(0).getParentcode());
				List<Itsv_dictionary> arylist = this.itsv_dictionaryService
						.queryByVO(ary);
				if (null != arylist && arylist.size() > 0) {
					aryname = arylist.get(0).getDictname();
				}
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">"
						+ aryname
						+ "</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
						+ dicname + "</td></tr>");
			}

		}

		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">价格</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getPrice() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">数量</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getGoodsNumber() + "</td></tr>");

		String picstart = "<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品详细</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">";
		String picend = "</td></tr>";
		StringBuffer sbu = new StringBuffer();
		for (FileUpload fu : fulist) {
			if (fu.getType().equals("0")) {
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品封面</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><img style = \"width:200px;height:150px;\" src = \""
						+ fu.getDownloadPath() + "\"></img></td></tr>");
			} else if (fu.getType().equals("1")) {
				sbu.append("<img style = \"width:200px;height:150px;\" src = \"" + fu.getDownloadPath() + "\"></img>");
			}
		}
		if (sbu.toString().length() > 0) {
			sb.append(picstart + sbu.toString() + picend);
		}
		sb.append("</table>");
		try {
			request.setAttribute("body", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mnv.setViewName("meitong/goods/detail");
		return mnv;
	}

	// 编辑
	@RequestMapping("/goods.EditData.htm")
	public ModelAndView editData(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mnv,
			@RequestParam String id) {
		StringBuffer sb = new StringBuffer();
		Goods goods = goodsService.queryById(id);
		GoodCode gc = new GoodCode();
		gc.setGoodId(id);
		List<GoodCode> gclist = this.goodCodeService.queryByVO(gc);

		List<FileUpload> fulist = new ArrayList<FileUpload>();
		// 查询品牌
		String brandName = "";
		for (GoodCode code : gclist) {
			Brand brand = new Brand();
			brand.setBrandcode(code.getCode());
			List<Brand> bdlist = this.brandService.queryByVO(brand);
			if (bdlist.size() > 0) {
				brandName = bdlist.get(0).getName();
				break;
			}
		}
		// 查询类别
		String lbname = "";
		Spec spec = this.specService.queryById(goods.getSpecId());
		if (null != spec) {
			Itsv_dictionary idic = new Itsv_dictionary();
			idic.setCode(spec.getType());
			List<Itsv_dictionary> itsv_dictionary = this.itsv_dictionaryService
					.queryByVO(idic);
			if (itsv_dictionary.size() > 0) {
				lbname = itsv_dictionary.get(0).getDictname();
			}

			FileUpload fileUpload = new FileUpload();
			fileUpload.setFId(spec.getId());
			fulist = this.fileUploadService.queryByVO(fileUpload);
		}

		// 判别标签
		String flag = "";
		if (goods.getFlag().equals("0")) {
			flag = "普通";
		} else if (goods.getFlag().equals("1")) {
			flag = "新品";
		} else if (goods.getFlag().equals("2")) {
			flag = "热销商品";
		}
		// 判别上架状态
		String zt = "";
		if (goods.getRemark1().equals("0")) {
			zt = "未上架";
		} else if (goods.getRemark1().equals("1")) {
			zt = "已上架";
		} else if (goods.getRemark1().equals("2")) {
			zt = "已下架";
		}
		sb.append("<table  align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#69D7E4\" class=\"listWt\" style =\"width:60%;\"> ");
		sb.append("<tr><th colspan=\"4\">商品详情</th></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品名称</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getName() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">包装清单</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getRemark2() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">类别</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ lbname + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">品牌</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ brandName + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">编码</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ goods.getGoodNo() + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">标签</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ flag + "</td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品状态</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
				+ zt + "</td></tr>");
		for (GoodCode code : gclist) {
			Itsv_dictionary itsv = new Itsv_dictionary();
			itsv.setCode(code.getCode());
			List<Itsv_dictionary> itsvlist = this.itsv_dictionaryService
					.queryByVO(itsv);
			String dicname = "";
			String aryname = "";
			if (null != itsvlist && itsvlist.size() > 0) {
				dicname = itsvlist.get(0).getDictname();
				// 查询上级COde
				Itsv_dictionary ary = new Itsv_dictionary();
				ary.setCode(itsvlist.get(0).getParentcode());
				List<Itsv_dictionary> arylist = this.itsv_dictionaryService
						.queryByVO(ary);
				if (null != arylist && arylist.size() > 0) {
					aryname = arylist.get(0).getDictname();
				}
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">"
						+ aryname
						+ "</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">"
						+ dicname + "</td></tr>");
			}

		}

		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">价格</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><input type=\"text\" name=\"p_Brand_price\" id=\"p_Brand_price\" maxlength=\"32\" value=\""
				+ goods.getPrice() + "\"/></td></tr>");
		sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">数量</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><input type=\"text\" name=\"p_Brand_goodsNumber\" id=\"p_Brand_goodsNumber\" maxlength=\"32\" value=\""
				+ goods.getGoodsNumber() + "\"/></td></tr>");
		System.out.println(request.getParameter("p_Brand_price"));

		String picstart = "<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品详细</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\">";
		String picend = "</td></tr>";
		StringBuffer sbu = new StringBuffer();
		for (FileUpload fu : fulist) {
			if (fu.getType().equals("0")) {
				sb.append("<tr><td class=\"td_l\" style=\"line-height:35px;\" width = \"100px;\">商品封面</td><td style=\"padding:10px 10px 10px 8px ;width : 120px;\"><img src = \""
						+ fu.getDownloadPath() + "\"></img></td></tr>");
			} else if (fu.getType().equals("1")) {
				sbu.append("<img src = \"" + fu.getDownloadPath() + "\"></img>");
			}
		}
		if (sbu.toString().length() > 0) {
			sb.append(picstart + sbu.toString() + picend);
		}
		// sb.append("<input type=\"button\" class=\"input_b\" value=\"提 交\" style=\"cursor:pointer;\"onclick=\""+saveEdit()+"\"/><input type=\"button\" class=\"input_b\" value=\"关 闭\" style=\"cursor:pointer;\"onclick=\"layerclose();\"/>");
		sb.append("</table>");
		try {
			request.setAttribute("body", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mnv.addObject("data", goods);
		mnv.setViewName("meitong/goods/edit");
		return mnv;
	}

	/**
	 * 保存修改的goods
	 */
	@RequestMapping("/goods.edit.htm")
	public ModelAndView saveEdit(HttpServletRequest request,
			HttpServletResponse response) {
		Goods goods = null;
		try {
			String pid = request.getParameter("pid");
			// goods = param2Object(request);
			goods = this.goodsService.queryById(pid);
			Spec spec = this.specService.queryById(goods.getSpecId());
			String price = request.getParameter("p_Brand_price");
			String gn = request.getParameter("p_Brand_goodsNumber");
			String onlypic = request.getParameter("onlypic");
			String manypic = request.getParameter("manypic");
			if((!"".equals(price))&&(!"".equals(gn))){
				// String remark = request.getParameter("remark");
				goods.setPrice(price);
				goods.setGoodsNumber(gn);
				if (null != onlypic && !"".equals(onlypic)) {
					FileUpload fileUpload = this.fileUploadService
							.queryById(onlypic);
					fileUpload.setFId(spec.getId());
					this.fileUploadService.update(fileUpload);
				}
				if (null != manypic && !"".equals(manypic)) {
					String[] manyid = manypic.split(",");
					for (String id : manyid) {
						FileUpload fileUpload = this.fileUploadService
								.queryById(id);
						fileUpload.setFId(spec.getId());
						this.fileUploadService.update(fileUpload);
					}
				}
				this.goodsService.update(goods);
				showMessage(request, "修改商品成功");
			}else{
				showMessage(request, "修改失败，商品单价或数量不能为空");
			}
			
		} catch (AppException e) {
			logger.error("修改商品[" + goods + "]失败", e);
			showMessage(request, "修改商品失败：" + e.getMessage(), e);

			// 修改失败后，重新显示修改页面
			return new ModelAndView(getEditView(), WebConfig.DATA_NAME, goods);
		}

		return new ModelAndView("admin/message1", WebConfig.DATA_NAME, goods);

	}

	/*
	 * @RequestMapping("file.htm") public void file(HttpServletRequest
	 * request,HttpServletResponse response){ String fId =
	 * request.getParameter("fid"); FileUpload fileUpload = new FileUpload();
	 * fileUpload.setType("0"); fileUpload.setFId(fId); List<FileUpload> list =
	 * this.fileUploadService.queryByVO(fileUpload); for(FileUpload f : list){
	 * this.fileUploadService.delete(f.getId());
	 * 
	 * } MultipartHttpServletRequest muRequest =
	 * (MultipartHttpServletRequest)request; MultipartFile mup =
	 * muRequest.getFile("file"); }
	 */
	/**
	 * 删除选中的goods
	 */
	@RequestMapping("/goods.Delete.htm")
	@ResponseBody
	public String delete(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String app_id) {
		String result = "success";
		try {
			if (!"".equals(app_id)) {
				String[] goods = app_id.split(",");
				for (String id : goods) {
					if (!"".equals(id)) {
						this.goodsService.delgoods(id);
					}
				}
			}
		} catch (AppException e) {
			result = "error";
		}
		return result;
	}

	/**
	 * 上架选中的goods
	 */
	@RequestMapping("/goods.theshelves.htm")
	@ResponseBody
	public String theshelves(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String app_id) {
		String result = "success";
		try {
			if (!"".equals(app_id)) {
				String[] goods = app_id.split(",");
				for (String id : goods) {
					if (!"".equals(id)) {
						this.goodsService.theshelves(id);
					}
				}
			}
		} catch (AppException e) {
			result = "error";
		}
		return result;
	}

	/**
	 * 下架选中的goods
	 */
	@RequestMapping("/goods.theshelf.htm")
	@ResponseBody
	public String theshelf(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String app_id) {
		String result = "success";
		try {
			if (!"".equals(app_id)) {
				String[] goods = app_id.split(",");
				for (String id : goods) {
					if (!"".equals(id)) {
						this.goodsService.theshelf(id);
					}
				}
			}
		} catch (AppException e) {
			result = "error";
		}
		return result;
	}

	@RequestMapping("/goods.refresh.htm")
	@ResponseBody
	public JSONArray inittable(HttpServletRequest request,
			HttpServletResponse response) {
		List<Itsv_dictionary> idiclist = itsv_dictionaryService
				.queryNextListByName("品类");
		JSONArray ja = new JSONArray();
		for (Itsv_dictionary idic : idiclist) {
			if (idic.getType().equals("0")) {
				JSONObject jo = new JSONObject();
				jo.put("name", idic.getDictname());
				jo.put("code", idic.getCode());
				List<Itsv_dictionary> itsvlist = this.itsv_dictionaryService
						.queryNextListByCode(idic.getCode());
				JSONArray jaidic = new JSONArray();
				for (Itsv_dictionary ary : itsvlist) {
					JSONObject jotwo = new JSONObject();
					jotwo.put("name", ary.getDictname());
					jotwo.put("code", ary.getCode());
					jotwo.put("type", ary.getType());
					JSONArray jatwo = new JSONArray();
					List<Itsv_dictionary> itsvlisttwo = this.itsv_dictionaryService
							.queryNextListByCode(ary.getCode());
					for (Itsv_dictionary idi : itsvlisttwo) {
						JSONObject joidi = new JSONObject();
						joidi.put("name", idi.getDictname());
						joidi.put("code", idi.getCode());
						joidi.put("type", idi.getType());
						jatwo.add(joidi);
					}
					jotwo.put("array", jatwo);
					jaidic.add(jotwo);
				}
				jo.put("array", jaidic);
				ja.add(jo);
			}
		}
		return ja;
	}

	@RequestMapping("/goods.refreshbrand.htm")
	@ResponseBody
	public JSONArray initbrand(HttpServletRequest request,
			HttpServletResponse response, String type) {
		Brand brand = new Brand();
		brand.setType(type);
		List<Brand> brandlist = this.brandService.queryByVO(brand);
		JSONArray ja = new JSONArray();
		for (Brand b : brandlist) {
			JSONObject jo = new JSONObject();
			jo.put("name", b.getName());
			jo.put("code", b.getBrandcode());
			jo.put("id", b.getId());
			ja.add(jo);
		}
		ja.toString();
		return ja;
	}
	/*@RequestMapping("/goods.findGoods.htm")
	@ResponseBody
	public List<Goods> findGoods(HttpServletRequest request, HttpServletResponse response, ModelAndView mnv) throws UnsupportedEncodingException{
		List<Goods> list = new ArrayList<Goods>();
		String specName = request.getParameter("specName");
		specName = new String(specName.getBytes("iso-8859-1"),"gbk");
		System.out.println("---------"+specName);
		String type = request.getParameter("type");
		List<Goods> glist = this.goodsService.queryBySpecName(specName);
		for(Goods g : glist){
			list.add(g);
		}
			Spec spec = new Spec();
			spec.setType(type);
			List<Spec> slist = this.specService.queryByVO(spec);
		for(Spec sp : slist){
			List<Goods> gglist = this.goodsService.queryByType(sp.getId());
			for(Goods gg : gglist){
				list.add(gg);
			}
		}
		return list;
	}*/
	// 指定分页查询记录在session中的名称
	@Override
	protected String getQueryName() {
		return QUERY_NAME;
	}

	/** 以下为set,get方法 */
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

}
