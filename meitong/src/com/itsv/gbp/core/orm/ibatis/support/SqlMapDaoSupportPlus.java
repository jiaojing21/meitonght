
package com.itsv.gbp.core.orm.ibatis.support;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 说明：针对spring的SqlMapDaoSupport类做的扩展类 <br>
 * 用SqlMapClientTemplatePlus类型代替SqlMapClientTemplate，以便能够执行分页查询。
 * 
 * @author admin 2004-9-28
 */
public class SqlMapDaoSupportPlus
{

    protected final Log logger = LogFactory.getLog(getClass());

    private SqlMapClientTemplatePlus sqlMapClientTemplate = new SqlMapClientTemplatePlus();

    /**
     * Set the JDBC DataSource to be used by this DAO.
     */
    public final void setDataSource(DataSource dataSource)
    {
        this.sqlMapClientTemplate.setDataSource(dataSource);
    }

    /**
     * Return the JDBC DataSource used by this DAO.
     */
    public final DataSource getDataSource()
    {
        return sqlMapClientTemplate.getDataSource();
    }

    /**
     * Set the iBATIS Database Layer SqlMap to work with.
     */
    public final void setSqlMapClient(SqlMapClient sqlMapClient)
    {
        this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
    }

    /**
     * Return the iBATIS Database Layer SqlMap that this template works with.
     */
    public final SqlMapClient getSqlMapClient()
    {
        return this.sqlMapClientTemplate.getSqlMapClient();
    }

    /**
     * Set the JdbcTemplate for this DAO explicitly, as an alternative to specifying a DataSource.
     */
    public final void setSqlMapClientTemplate(SqlMapClientTemplatePlus sqlMapClientTemplate)
    {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    /**
     * Return the JdbcTemplate for this DAO, pre-initialized with the DataSource or set explicitly.
     */
    public final SqlMapClientTemplatePlus getSqlMapClientTemplate()
    {
        return sqlMapClientTemplate;
    }

    public final void afterPropertiesSet() throws Exception
    {
        this.sqlMapClientTemplate.afterPropertiesSet();
        initDao();
    }

    /**
     * Subclasses can override this for custom initialization behavior. Gets called after population
     * of this instance's bean properties.
     * 
     * @throws Exception if initialization fails
     */
    protected void initDao() throws Exception
    {
    }
}