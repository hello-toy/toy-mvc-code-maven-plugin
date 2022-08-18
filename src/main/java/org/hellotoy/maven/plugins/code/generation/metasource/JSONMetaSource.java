package org.hellotoy.maven.plugins.code.generation.metasource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.logging.Log;
import org.hellotoy.maven.plugins.code.generation.config.MetaInfo;
import org.hellotoy.maven.plugins.code.generation.metasource.model.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONMetaSource implements MetaSource {
    private MetaInfo metaInfo;

    private Log log;

    public JSONMetaSource(MetaInfo metaInfo,Log log){
        this.metaInfo = metaInfo;
        this.log = log;
    }

    @Override
    public List<Model> loadAllModels() {
        List<Model> retData = new ArrayList<>();
        String path = metaInfo.getPath();
        File file = new File(path);
        List<File> metaFiles = new ArrayList<>();
        if(file.isDirectory()){
            metaFiles.addAll(Arrays.asList(file.listFiles()));
        } else{
            metaFiles.add(file);
        }
        for (File metaFile : metaFiles) {
            Model model = readModel(metaFile);
            if(model != null){
                retData.add(model);
            }
        }
        return retData;
    }

    private Model readModel(File file){
        try {
            String data = IOUtils.toString(new FileInputStream(file));
            return JSON.parseObject(data,Model.class);
        }
        catch (IOException e) {
            log.info("read data from "+ file.getPath()+" occured error",e);
        }

        return null;
    }

    @Override
    public Model loadAllModel(String id) {
        return null;
    }
}
