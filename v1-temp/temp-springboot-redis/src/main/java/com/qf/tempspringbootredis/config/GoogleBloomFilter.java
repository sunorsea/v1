package com.qf.tempspringbootredis.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/4
 */
@Component
public class GoogleBloomFilter {

    private BloomFilter<Long> bloomFilter;

    @PostConstruct
    public void initBloomFilter() {
        List<Long> sourceList = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            sourceList.add(i);
        }
        bloomFilter = BloomFilter.create(Funnels.longFunnel(), sourceList.size(), 0.001);
        for (Long id : sourceList) {
            bloomFilter.put(id);
        }
    }

    public boolean isExists(Long id) {
        return bloomFilter.mightContain(id);
    }

}
