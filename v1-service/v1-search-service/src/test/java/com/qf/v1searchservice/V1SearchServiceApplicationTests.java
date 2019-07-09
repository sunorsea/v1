package com.qf.v1searchservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.ISearchService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @Test
    public void synDataByTest() {
        ResultBean resultBean = searchService.synDataById(10L);
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void queryByKeywordsTest() {
        ResultBean<List<TProduct>> resultBean = searchService.queryByKeywords("程序员");
        List<TProduct> products = resultBean.getData();
        System.out.println(products);
        for (TProduct product : products) {
            System.out.println(product.getId()+"------"+product.getName());
        }
    }

    @Test
    public void synAllDateTest(){
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getStatusCode());

    }

    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",555);
        document.setField("product_name","华为123电脑");
        document.setField("product_price",66666);
        document.setField("product_images","3344234");
        document.setField("product_sale_point","谁要煎鸡蛋吗");
//        solrClient.add(document);
        solrClient.add("collection2", document);
//        solrClient.commit();
        solrClient.commit("collection2");
        System.out.println("保存成功");
    }

    @Test
    public void queryTest() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("product_name:华为旗舰手机");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.println("id:" + result.get("id") + ",name:" + result.get("product_name"));
        }
    }

    @Test
    public void delById() throws IOException, SolrServerException {
        solrClient.deleteById("2");
        solrClient.commit();
    }

    @Test
    public void delByConditionTest() throws IOException, SolrServerException {
        solrClient.deleteByQuery("product_name:华为旗舰大龙虾");
        solrClient.commit();
    }

    @Test
    public void delAllTest() throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

}
