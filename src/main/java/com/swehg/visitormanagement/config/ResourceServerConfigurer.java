package com.swehg.visitormanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;


@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .authorizeRequests()

                // building
                .antMatchers("/v1/building/add")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/building/update")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/building/all")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/building/active")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/building/delete")
                .access("hasAnyRole('ROLE_ADMIN')")


                // visits
                .antMatchers("/v1/visit/checkin")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visit/checkout")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visit/checked")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visit/overdue")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visit/history")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visit/checked")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                // employee
                .antMatchers("/v1/employee/add")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/employee/update")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/employee/all")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                // floor
                .antMatchers("/v1/floor/add")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/floor/update")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/floor/all")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/floor/active")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                // pass card
                .antMatchers("/v1/pass/add")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/pass/update")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/pass/all")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/pass/active")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/pass/delete")
                .access("hasAnyRole('ROLE_ADMIN')")

                // receptionist
                .antMatchers("/v1/receptionist/add")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/receptionist/update")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/receptionist/all")
                .access("hasAnyRole('ROLE_ADMIN')")

                //visitor
                .antMatchers("/v1/visitor/filter/nic")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEP')")

                .antMatchers("/v1/visitor/filter")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers("/v1/**").permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}
