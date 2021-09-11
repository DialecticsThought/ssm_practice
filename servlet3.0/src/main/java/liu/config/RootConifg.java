package liu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = "liu",
        excludeFilters =
                {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {ModuleLayer.Controller.class})}
)
public class RootConifg {
}
