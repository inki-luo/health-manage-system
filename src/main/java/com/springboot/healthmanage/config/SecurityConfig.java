package com.springboot.healthmanage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // HTTPリクエストに対するセキュリティ設定
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login/**", "/css/**", "/js/**").permitAll()  // ログイン不要でアクセスできるURL
                        .anyRequest().authenticated()
                )
                // ログイン設定
                .formLogin(form -> form
                        .loginPage("/login")             // カスタムログインページのURL
                        .defaultSuccessUrl("/", true) // ログイン成功時のリダイレクト先
                        .permitAll()
                )
                // ログアウト設定
                .logout(logout -> logout.permitAll()
                );

        return http.build();
    }


}
