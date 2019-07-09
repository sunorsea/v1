package com.qf.v1searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v1.api.ISearchService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;
import com.qf.v1.mapper.TProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/17
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean synAllData() {
        List<TProduct> list = productMapper.list();
        for (TProduct product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_images",product.getImages());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_sale_price",product.getSalePrice());
            document.setField("product_type_name",product.getTypeName());
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404", "数据添加到索引库失败");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "数据提交到索引库失败");
        }
        return new ResultBean("200", "数据同步成功");
    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        SolrQuery queryCondition = new SolrQuery();
        if (StringUtils.isAnyEmpty(keywords)) {
            queryCondition.setQuery("*:*");
        } else {
            queryCondition.setQuery("product_keywords:" + keywords);
        }

        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        //设置分页条件
//        queryCondition.setStart((pageIndex-1)*pageSize);
//        queryCondition.setRows(pageSize);



        try {
            QueryResponse response = solrClient.query(queryCondition);
            SolrDocumentList documents = response.getResults();
            List<TProduct> list = new ArrayList<>(documents.size());

            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();


            for (SolrDocument document : documents) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setImages(document.getFieldValue("product_images").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setSalePrice(Long.parseLong(document.getFieldValue("product_sale_price").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());

                Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                List<String> productNameHigh = idHigh.get("product_name");
                if (productNameHigh == null || productNameHigh.isEmpty()) {
                    product.setName(document.getFieldValue("product_name").toString());
                } else {
                    product.setName(productNameHigh.get(0));
                }
                list.add(product);
            }
            return new ResultBean("200", list);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "执行查询失败");
        }
    }

    @Override
    public ResultBean synDataById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("product_images",product.getImages());
        document.setField("product_sale_point",product.getSalePoint());
        document.setField("product_sale_price",product.getSalePrice());
        try {
            solrClient.add(document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "数据添加到索引库失败");
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "数据提交到索引库失败");
        }
        return new ResultBean("200", "数据同步成功");
    }
}
