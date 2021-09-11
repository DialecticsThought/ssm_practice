package liu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//只要controller
//useDefaultFilters = false禁用默认的过滤规则  includeFilters要添加上
@ComponentScan(value = "liu",
        includeFilters =
                {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {ModuleLayer.Controller.class})}
,useDefaultFilters = false)
public class AppConfig {
}
