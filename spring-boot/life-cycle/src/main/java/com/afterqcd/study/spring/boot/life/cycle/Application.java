package com.afterqcd.study.spring.boot.life.cycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    /**
     * 运行查看生命周期内的事件.
     * 事件类型由 # 分隔开。
     * ################################################
     * App Event	    # ApplicationStartedEvent
     * App Event	    # ApplicationEnvironmentPreparedEvent
     * App Event		# ApplicationPreparedEvent application准备好后，开始加载bean
     * Bean child		# PostConstruct 先加载完被依赖的bean
     * Bean parent		# PostConstruct 后加载完有依赖的bean
     * Context Event	# ContextRefreshedEvent 所有bean加载完成后，context初始化成功。此时可以做自定义的初始化工作，如缓存预热
     * App Event		# ContextRefreshedEvent
     * Context Event	# ApplicationReadyEvent context初始化完成后，application进入可运行阶段
     * App Event		# ApplicationReadyEvent
     * Context Event	# ContextClosedEvent application关闭前，先关闭context
     * App Event		# ContextClosedEvent
     * Bean parent		# PreDestroy context关闭时会卸载bean。先卸载完有依赖的bean
     * Bean child		# PreDestroy 后卸载被依赖的bean
     * ################################################
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        // 添加ApplicationEvent的监听器
        application.addListeners(new OneApplicationListener());
        application.run(args);
    }
}
