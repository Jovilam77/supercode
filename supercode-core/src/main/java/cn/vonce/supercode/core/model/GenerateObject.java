package cn.vonce.supercode.core.model;

import cn.vonce.supercode.core.config.GenerateConfig;

import java.util.List;
import java.util.Map;

/**
 * 生成对象
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class GenerateObject {

    private GenerateConfig generateConfig;
    private List<Map<String, Object>> columnInfoList;

    public GenerateConfig getGenerateConfig() {
        return generateConfig;
    }

    public void setGenerateConfig(GenerateConfig generateConfig) {
        this.generateConfig = generateConfig;
    }

    public List<Map<String, Object>> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<Map<String, Object>> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

}
