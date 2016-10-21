package com.itsv.annotation.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itsv.annotation.brand.bo.BrandService;
import com.itsv.annotation.brand.vo.Brand;

@Component
public class ServiceUitl {
	@Autowired
	private BrandService brandService;
	private static ServiceUitl serviceUitl;

	@PostConstruct
	public void init() {
		serviceUitl = this;
		serviceUitl.brandService = this.brandService;
	}

	public BrandService getBrandService() {
		return brandService;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public static ServiceUitl getServiceUitl() {
		return serviceUitl;
	}

	public static void setServiceUitl(ServiceUitl serviceUitl) {
		ServiceUitl.serviceUitl = serviceUitl;
	}

	/**
	 * 通过类别查询品牌
	 * 
	 * @param type
	 * @return
	 */
	public static List<Brand> getBrandType(String type) {
		List<Brand> list = serviceUitl.brandService.queryByTypeCode(type);
		return list;
	}

}
