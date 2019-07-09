package com.qianfeng.v1productservice;

import com.github.pagehelper.PageInfo;
import com.qf.v1.api.IProductService;
import com.qf.v1.api.IProductTypeService;
import com.qf.v1.entity.TProduct;
import com.qf.v1.entity.TProductType;
import com.qf.v1.pojo.TProductVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void connectionTest() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void contextLoads() {
        List<TProductType> list = productTypeService.list();
        System.out.println(list.size());
    }

}
