package com.jwt.generate;

/**
 * 代码生成器
 *
 * @author dongwn
 * @date 2017/11/25 14:05
 */
public class CodeGenerator {

    public static void main(String[] args) {
        UmpGeneratorUtil mybatisPlusGeneratorUtil = new UmpGeneratorUtil();
        String propertiesFilePath = "generator.properties";
        mybatisPlusGeneratorUtil.generator(propertiesFilePath);
    }

}
