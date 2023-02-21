package com.adben.testdatabuilder.entity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 *
 */
@Configuration
@ComponentScan(basePackages = "com.adben.testdatabuilder", includeFilters = @Filter(type = FilterType.ANNOTATION, value = Component.class))
public class EntityTestCfg {

}
