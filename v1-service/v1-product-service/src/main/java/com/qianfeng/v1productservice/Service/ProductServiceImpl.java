package com.qianfeng.v1productservice.Service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v1.api.IProductService;
import com.qf.v1.common.base.BaseServiceImpl;
import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TProduct;
import com.qf.v1.entity.TProductDesc;
import com.qf.v1.mapper.TProductDescMapper;
import com.qf.v1.mapper.TProductMapper;
import com.qf.v1.pojo.TProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/11
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<TProduct> list = list();
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list, 3);
        return pageInfo;
    }

    @Override
    @Transactional
    public Long save(TProductVO vo) {
        TProduct product=vo.getProduct();
        product.setFlag(true);
        int count = productMapper.insert(product);
        String productDesc=vo.getProductDesc();
        TProductDesc desc=new TProductDesc();
        desc.setProductId(product.getId());
        desc.setProductName(productDesc);
        productDescMapper.insert(desc);

        return product.getId();
    }

    @Override
    public Long batchDel(List<Long> ids) {
        return productMapper.batchUpdateFlag(ids);
    }

    @Override
    public TProductVO selectProductById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);
        Long productId=product.getId();
        TProductDesc productDesc = productDescMapper.selectByProductId(productId);
        TProductVO productVO=new TProductVO();
        productVO.setProduct(product);
        productVO.setProDesc(productDesc);
        return productVO;
    }

    /**
     * 做逻辑删除重写
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        TProduct product=new TProduct();
        product.setId(id);
        product.setFlag(false);
        return productMapper.updateByPrimaryKeySelective(product);
    }
}
