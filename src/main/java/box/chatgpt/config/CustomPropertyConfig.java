package box.chatgpt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CustomPropertyConfig implements ApplicationContextAware {

    @Value("${custom.sm4.key}")
    private String key;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomPropertyConfig.applicationContext = applicationContext;
    }

    public static CustomPropertyConfig instance() {
        return CustomPropertyHolder.SINGLETON;
    }

    private static class CustomPropertyHolder {
        private static CustomPropertyConfig SINGLETON;

        static {
            if (null != applicationContext) {
                SINGLETON = applicationContext.getBean(CustomPropertyConfig.class);
            }
        }
    }
}