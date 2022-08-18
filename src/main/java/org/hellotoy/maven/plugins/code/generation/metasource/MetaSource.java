package org.hellotoy.maven.plugins.code.generation.metasource;

import org.hellotoy.maven.plugins.code.generation.metasource.model.Model;

import java.util.List;

public interface MetaSource {
    /**
     * 查询所有的
     *
     * @return
     */
    List<Model> loadAllModels();

    /**
     * 根据ID查询单个
     *
     * @param id
     * @return
     */
    Model loadAllModel(String id);
}
