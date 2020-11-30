package com.jwt.generate;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MybatisPlus代码生成工具
 *
 * @author dongwn
 * @date 2017-12-11
 **/
public class UmpGeneratorUtil {

    /**
     * 定义包名称
     */
    private static String modelPackageName = "com.jwt.entity";
    private static String controllerPackageName = "com.jwt.controller";
    private static String servicePackageName = "com.jwt.service";
    private static String serviceImplPackageName = "com.jwt.service.impl";
    private static String mapperPackageName = "com.jwt.mapper";

    /**
     * 根据配置文件执行生成
     *
     * @param propertiesFilePath properties配置文件绝对路径
     * @author yinjj
     * @date 2017-12-11 20:04
     */
    public void generator(String propertiesFilePath) {
        final Props props = new Props(FileUtil.touch(propertiesFilePath), CharsetUtil.UTF_8);
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(props.getStr("global.outputdir"));
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setBaseColumnList(false);
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor(props.getStr("global.author"));
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName(props.getStr("datasource.drivername"));
        dsc.setUsername(props.getStr("datasource.username"));
        dsc.setPassword(props.getStr("datasource.password"));
        dsc.setUrl(props.getStr("datasource.url"));
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true)
                //.setDbColumnUnderline(true) //表名字段名使用下划线
                .setRestControllerStyle(true);
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(props.getStr("strategy.tableprefix"));
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(StrUtil.split(props.getStr("strategy.include"), ","));
        // 排除生成的表
        strategy.setExclude(StrUtil.split(props.getStr("strategy.exclude"), ","));
        // 自定义实体父类
        strategy.setSuperEntityClass(props.getStr("strategy.superentityclass"));
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns(StrUtil.split(props.getStr("strategy.superentitycolumns"), ","));
        // 自定义 mapper 父类
        strategy.setSuperMapperClass(props.getStr("strategy.supermapperclass"));
        // 自定义 service 父类
        strategy.setSuperServiceClass(props.getStr("strategy.superserviceclass"));
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass(props.getStr("strategy.superserviceimplclass"));
        // 自定义 controller 父类
        strategy.setSuperControllerClass(props.getStr("strategy.superCcontrollerclass"));
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(props.getStr("package.parent"));
        pc.setModuleName(props.getStr("package.modulename"));
        mpg.setPackageInfo(pc);

        //自定义模板参数 用法${cfg.modelPackageName}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>(1);
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("modelPackageName", modelPackageName);
                map.put("controllerPackageName", controllerPackageName);
                map.put("servicePackageName", servicePackageName);
                map.put("serviceImplPackageName", serviceImplPackageName);
                map.put("mapperPackageName", mapperPackageName);
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();

        // 调整 model 生成目录
        FileOutConfig fileModelConfig = new FileOutConfig("/templates/entity.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                return props.getStr("fileout.modeldirectory") + tableInfo.getEntityName() + "Model.java";
            }
        };
        focList.add(fileModelConfig);
        //调整 controller 生成目录
        FileOutConfig controllerConfig = new FileOutConfig("/templates/controller.java.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                System.out.println("tableInfo.getEntityName()=" + tableInfo.getEntityName());
                return props.getStr("fileout.controllerdirectory") + tableInfo.getEntityName() + "Controller.java";
            }
        };
        focList.add(controllerConfig);

        // 调整 service 生成目录
        FileOutConfig serviceConfig = new FileOutConfig("/templates/service.java.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                return props.getStr("fileout.servicedirectory") + tableInfo.getEntityName() + "Service.java";
            }
        };
        focList.add(serviceConfig);

        // 调整 service impl 生成目录
        FileOutConfig serviceImplConfig = new FileOutConfig("/templates/serviceImpl.java.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                return props.getStr("fileout.serviceimpldirectory") + tableInfo.getEntityName() + "ServiceImpl.java";
            }
        };
        focList.add(serviceImplConfig);

        // 调整 mapper 生成目录
        FileOutConfig mapperConfig = new FileOutConfig("/templates/mapper.java.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                return props.getStr("fileout.mapperdirectory") + tableInfo.getEntityName() + "Mapper.java";
            }
        };
        focList.add(mapperConfig);

        // 调整 xml 生成目录
        FileOutConfig fileOutConfig = new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
                return props.getStr("fileout.mapperxmldirectory") + tableInfo.getEntityName() + "Mapper.xml";
            }
        };
        // 调整 htmlList 生成目录
//        FileOutConfig htmlList = new FileOutConfig("/templates/list.html.vm") {
//            @Override
//            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
//                return props.getStr("fileout.pagedirectory") + tableInfo.getEntityName() + "List.html";
//            }
//        };
        // 调整 htmlList 生成目录
//        FileOutConfig jsList = new FileOutConfig("/templates/list.js.vm") {
//            @Override
//            public String outputFile(com.baomidou.mybatisplus.generator.config.po.TableInfo tableInfo) {
//                return props.getStr("fileout.pagedirectory") + tableInfo.getEntityName() + "List.js";
//            }
//        };
        focList.add(fileOutConfig);
//        focList.add(htmlList);
//        focList.add(jsList);
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null); //不生成xml
        tc.setEntity(null);//不生成默认实体
        tc.setController(null);//不生成默认实体Controller
        tc.setMapper(null).setService(null).setServiceImpl(null);


        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
    }
}
