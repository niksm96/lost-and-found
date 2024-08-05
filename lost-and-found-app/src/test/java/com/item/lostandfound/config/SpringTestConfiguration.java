package com.item.lostandfound.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

@TestConfiguration
public class SpringTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final User.UserBuilder userBuilder = User.builder().passwordEncoder(passwordEncoder::encode);
        List<GrantedAuthority> adminAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        List<GrantedAuthority> userAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        UserDetails adminUser = userBuilder.username("shail@mail.com").password("shail@123").authorities(adminAuthorities).build();
        UserDetails userUser = userBuilder.username("john@mail.com").password("john@123").authorities(userAuthorities).build();

        return new InMemoryUserDetailsManager(Arrays.asList(adminUser, userUser));
    }
}
