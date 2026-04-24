package com.mg.Association_Flows.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// add this class to enable @CreatedDate and @LastModifiedDate work well
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
