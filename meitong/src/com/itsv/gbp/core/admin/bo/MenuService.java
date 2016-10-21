package com.itsv.gbp.core.admin.bo;

import java.util.ArrayList;
import java.util.List;

import com.itsv.gbp.core.admin.dao.MenuDao;
import com.itsv.gbp.core.admin.vo.Menu;

/**
 * ����Ե�λ�����ҵ�������<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����09:55:43
 * @version 1.0
 */
public class MenuService extends LoggedService {

	private MenuDao menuDao;

	/**
	 * ���Ӳ˵�
	 */
	public void add(Menu menu) {
		this.menuDao.save(menu);

		// log
		writeLog("���Ӳ˵�", "�����˵�[" + menu + "]");
	}

	/**
	 * �޸Ĳ˵�
	 */
	public void update(Menu menu) {
		this.menuDao.update(menu);

		// log
		writeLog("�޸Ĳ˵�", "�޸Ĳ˵�[" + menu + "]");
	}

	/**
	 * ɾ���˵�
	 */
	public void delete(String menuId) {
		this.menuDao.removeById(menuId);

		// log
		writeLog("ɾ���˵�", "ɾ���˵�[id=" + menuId + "]");
	}

	/**
	 * ���ݲ˵�ID��ѯ�˵�����ϸ��Ϣ
	 */
	public Menu queryById(String menuid) {
		return this.menuDao.get(menuid);
	}

	/**
	 * ���ݲ˵������ѯ�˵�����ϸ��Ϣ
	 */
	public Menu queryByCode(String code) {
		return this.menuDao.findUniqueBy("code", code);
	}

	/**
	 * ���������в˵������������ϵĲ˵�
	 */
	public List<Menu> queryAll() {
		return this.menuDao.getAll();
	}

	/** get,set */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public List<String> queryMenu(String strMenuCode) {

		List<String> ret = new ArrayList<String>();
		Menu tmp = this.queryByCode(strMenuCode);
		if (tmp.getCode() != null && tmp.getCode().length() % 3 == 0) {
			int c = tmp.getCode().length() / 3;

			ret.add(tmp.getName());
			for (int i = 1; i < c; i++) {
/*				System.out.println(">>"
						+ tmp.getCode().substring(0,
								(tmp.getCode().length() - i * 3)));*/
				try {
					String tcode = tmp.getCode().substring(0,
							(tmp.getCode().length() - i * 3));

					Menu m = this.queryByCode(tcode);
					if (m != null) {
						ret.add(m.getName());
					}
				} catch (Exception e) {

				}
			}
		}
		return ret;
	}

}
