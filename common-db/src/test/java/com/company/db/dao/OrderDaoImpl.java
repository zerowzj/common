package com.company.db.dao;

import com.company.db.datasource.DataSourceHolder;
import com.company.db.datasource.DataSourceType;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 *
 * @author wangzhj
 * @time 2016-11-25 18:18
 */
@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<OrderEO> implements OrderDao {

    @Override
    public OrderEO findLt(Long id) {
        String sqlId = getSqlId("get");
        OrderEO entity = sqlSessionTemplate.selectOne(sqlId, id);
        return entity;
    }
}