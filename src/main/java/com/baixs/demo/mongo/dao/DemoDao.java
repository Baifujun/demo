package com.baixs.demo.mongo.dao;

import com.baixs.demo.mongo.document.DemoEntity;

public interface DemoDao {
    void saveDemo(DemoEntity demoEntity);

    void removeDemo(Long id);

    void updateDemo(DemoEntity demoEntity);

    DemoEntity findDemoById(Long id);
}
