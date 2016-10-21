package com.itsv.annotation.brand.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� brand ֵ������
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */

 @Entity
 @Table(name="brand")
public class Brand {
                  @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="NAME",length=200  )
	private String name; //Ʒ������

    @Column(name="TYPE",length=50  )
	private String type; //��Ʒ����
    
	@Column(name="CRADLE",length=200  )
	private String cradle; //��Դ��

    @Column(name="INTRODUCE",length=500  )
	private String introduce; //Ʒ�ƽ���

    @Column(name="PICTUREURL",length=200  )
	private String pictureurl; //ͼƬ

    @Column(name="BRANDCODE",length=200  )
	private String brandcode; //Ʒ�Ʊ���

    @Column(name="FLAG",length=10  )
	private String flag; //״̬��ǣ�0���¼ܣ�1������
    
    @Transient
    private String typename;//��Ʒ��������
    
    public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Transient
  	private String[] filearr; //����
     
	@Transient
  	private String[] removefilearr; //����
    /** ����Ϊget,set���� */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCradle() {
    return this.cradle;
  }

  public void setCradle(String cradle) {
    this.cradle = cradle;
  }

	public String getIntroduce() {
    return this.introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

	public String getPictureurl() {
    return this.pictureurl;
  }

  public void setPictureurl(String pictureurl) {
    this.pictureurl = pictureurl;
  }

	public String getBrandcode() {
    return this.brandcode;
  }

  public void setBrandcode(String brandcode) {
    this.brandcode = brandcode;
  }

	public String getFlag() {
    return this.flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }
  public String[] getFilearr() {
		return filearr;
	}

	public void setFilearr(String[] filearr) {
		this.filearr = filearr;
	}

	public String[] getRemovefilearr() {
		return removefilearr;
	}

	public void setRemovefilearr(String[] removefilearr) {
		this.removefilearr = removefilearr;
	}

}
