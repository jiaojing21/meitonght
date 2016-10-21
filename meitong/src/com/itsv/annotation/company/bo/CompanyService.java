package com.itsv.annotation.company.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.company.dao.CompanyDao;
import com.itsv.annotation.company.vo.Company;
import com.itsv.annotation.company.vo.Impaexp;
import com.itsv.annotation.company.vo.Polytene;
import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import com.itsv.gbp.core.util.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对企业的业务操作
 *
 * @author quyf
 * @since 2014-10-23
 * @version 1.0
 */
 @Service @Transactional 
public class CompanyService extends BaseService {

  //数据访问层对象
  @Autowired
  private CompanyDao companyDao;
  
  @Autowired
  private ImpaexpService impaexpService; //进出口的逻辑层对象
  
  @Autowired
  private PolyteneService polyteneService; //聚乙烯产能逻辑层对象
	
  /**
	 * 增加企业
	 */
	public void add(Company company) {
		this.companyDao.save(company);
		/*
		String company_id=company.getId();//保存后,获取持久实体ID
		String type=company.getType();
		if("1".equals(type)){//1企业
			Polytene polytene=new Polytene();//聚乙烯产能表
			polytene.setCompanyid(company_id);//企业id
			polytene.setType(type);//类型
			polytene.setCapacity(company.getTemp_capacity_exportsl());
			polytene.setProduction(company.getTemp_production_exportse());
			polytene.setPtime(company.getTemp_ptime_htime());
			this.polyteneService.add(polytene);
		}
		if("2".equals(type)){//2海关
			Impaexp impaexp=new Impaexp();//进出口
			impaexp.setCompanyid(company_id);//海关id
			impaexp.setType(type);//类型
			impaexp.setImportsl(company.getTemp_importsL());
			impaexp.setImportse(company.getTemp_importsE());
			impaexp.setExportsl(company.getTemp_capacity_exportsl());
			impaexp.setExportse(company.getTemp_production_exportse());
			impaexp.setHtime(company.getTemp_ptime_htime());
			this.impaexpService.add(impaexp);
		}
		*/
		
	}

	/**
	 * 修改企业
	 */
	public void update(Company company) {
		/*
		if("1".equals(company.getType())){//1 企业,删除聚乙烯产能表
			Polytene polytene_new=new Polytene(); 
			Polytene polytene_base=this.polyteneService.findUniqueBy("companyid",company.getId());
			Impaexp impaexp=this.impaexpService.findUniqueBy("companyid", company.getId());
			if(impaexp != null){
				this.impaexpService.delete(impaexp.getId());
			}
			if(polytene_base !=null){
				polytene_base.setCapacity(company.getTemp_capacity_exportsl());
				polytene_base.setProduction(company.getTemp_production_exportse());
				polytene_base.setPtime(company.getTemp_ptime_htime());
				this.polyteneService.update(polytene_base);
			} else {
				polytene_new.setCompanyid(company.getId());
				polytene_new.setCapacity(company.getTemp_capacity_exportsl());
				polytene_new.setProduction(company.getTemp_production_exportse());
				polytene_new.setPtime(company.getTemp_ptime_htime());
				this.polyteneService.add(polytene_new);
			}
		}
		if("2".equals(company.getType())){//2 海关,删除进出口
			Impaexp impaexp_new=new Impaexp();
			Impaexp impaexp_base=this.impaexpService.findUniqueBy("companyid", company.getId());
			Polytene polytene=this.polyteneService.findUniqueBy("companyid",company.getId());
			if(polytene !=null){
				this.polyteneService.delete(polytene.getId());	
			}
			if(impaexp_base !=null){
				impaexp_base.setImportsl(company.getTemp_importsL());
				impaexp_base.setImportse(company.getTemp_importsE());
				impaexp_base.setExportsl(company.getTemp_capacity_exportsl());
				impaexp_base.setExportse(company.getTemp_production_exportse());
				impaexp_base.setHtime(company.getTemp_ptime_htime());
				this.impaexpService.update(impaexp_base);
			} else {
				impaexp_new.setCompanyid(company.getId());
				impaexp_new.setImportsl(company.getTemp_importsL());
				impaexp_new.setImportse(company.getTemp_importsE());
				impaexp_new.setExportsl(company.getTemp_capacity_exportsl());
				impaexp_new.setExportse(company.getTemp_production_exportse());
				impaexp_new.setHtime(company.getTemp_ptime_htime());
				this.impaexpService.add(impaexp_new);
			}
			
		}
		*/
		if("1".equals(company.getType())){//1 更换企业,删除进出口
			Impaexp impaexp=this.impaexpService.findUniqueBy("companyid", company.getId());
			if(impaexp != null){
				this.impaexpService.delete(impaexp.getId());
			}
		}
		if("2".equals(company.getType())){//2 更换海关,删除聚乙烯产能表
			Polytene polytene=this.polyteneService.findUniqueBy("companyid",company.getId());
			if(polytene !=null){
				this.polyteneService.delete(polytene.getId());	
			}
		}
		Company base_company =this.companyDao.get(company.getId());
		BeanUtil.copyProperty(base_company, company);
		this.companyDao.update(base_company);
	}

	/**
	 * 删除企业
	 */
	public void delete(Serializable id) {
		Company company=this.queryById(id);
		
		if("1".equals(company.getType())){//1 企业,删除聚乙烯产能表
			Polytene polytene=new Polytene();
			polytene.setCompanyid(company.getId());
			List<Polytene> polytenelist=this.polyteneService.queryByVO(polytene);
			if(polytenelist !=null && polytenelist.size() > 0){
				for (Polytene polytenenew : polytenelist){
					this.polyteneService.delete(polytenenew.getId());
				}
			}
			
		}
		if("2".equals(company.getType())){//2 海关,删除进出口
			Impaexp impaexp=new Impaexp();
			impaexp.setCompanyid(company.getId());
			List<Impaexp> impaexplist=this.impaexpService.queryByVO(impaexp);
			if(impaexplist != null && impaexplist.size()>0){
				for (Impaexp impaexpnew :impaexplist) {
					this.impaexpService.delete(impaexpnew.getId());
				}
			}
		}
		if(company !=null){
			this.companyDao.removeById(company.getId());
		}
		
	}

	/**
	 * 根据ID查询企业的详细信息
	 */
	public Company queryById(Serializable companyid) {
		return this.companyDao.get(companyid);
	}

	/**
	 * 获取所有的企业对象
	 */
	public List<Company> queryAll() {
		return this.companyDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Company> queryByVO(Company company) {
		return this.companyDao.queryByObject(company);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Company company) {
		return this.companyDao.queryByObject(records, company);
	}
	

}