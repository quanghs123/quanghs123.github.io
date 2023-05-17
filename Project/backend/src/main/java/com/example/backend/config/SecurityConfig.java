package com.example.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()

                .requestMatchers("/api/v1/brand/getall").permitAll()
                .requestMatchers("/api/v1/brand/getAll/findAllBr").permitAll()
                .requestMatchers("/api/v1/brand/editbrand/noimage/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/brand/editbrand/withimage/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/brand/addbrand").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/brand/getbyid/**").permitAll()
                .requestMatchers("/api/v1/brand/deletebrand/**").hasAnyAuthority("ADMIN")

                .requestMatchers("/api/v1/favorite/getfavoritebyproductid/**").permitAll()
                .requestMatchers("/api/v1/favorite/findTop5Product").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/favorite/getfavoritebyid").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/favorite/addfavorite").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/favorite/deletefavorite").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/api/v1/view/addview").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/api/v1/cart/getall").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/cart/addcart").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/cart/delete").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/cart/deletebyaccountid/**").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/api/v1/order/getall").permitAll()
                .requestMatchers("/api/v1/order/getAll/findAllOr").permitAll()
                .requestMatchers("/api/v1/order/addorder").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/order/getbyaccountid/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/order/getbyaccountid1/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/order/deleteorder/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/order/editorder/**").permitAll()

                .requestMatchers("/api/v1/orderdetail/addorderdetail").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/orderdetail/findTop5Product").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/orderdetail/revenueYear").permitAll()
                .requestMatchers("/api/v1/orderdetail/getbyorderid/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/orderdetail/deleteorderdetail/**").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/api/v1/product/getall").permitAll()
                .requestMatchers("/api/v1/product/getAll/findAllPr").permitAll()
                .requestMatchers("/api/v1/product/searchProductWithPrice").permitAll()
                .requestMatchers("/api/v1/product/searchProductWithoutPrice").permitAll()
                .requestMatchers("/api/v1/product/findFavoriteByAccountID/**").hasAnyAuthority("USER")
                .requestMatchers("/api/v1/product/findHistoryByAccountID/**").hasAnyAuthority("USER")
                .requestMatchers("/api/v1/product/editproduct/noimage/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/product/editproduct/withimage/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/product/addproduct").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/product/getbyid/**").permitAll()
                .requestMatchers("/api/v1/product/findByBrandID/**").permitAll()
                .requestMatchers("/api/v1/product/updateproductquantity/**").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/api/v1/account/changeAccount/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/account/forgotPassword").permitAll()
                .requestMatchers("/api/v1/account/getall").permitAll()
                .requestMatchers("/api/v1/account/getAll/findAllAcc").permitAll()
                .requestMatchers("/api/v1/account/editaccount/**").hasAnyAuthority("USER")
                .requestMatchers("/api/v1/account/addaccount").hasAnyAuthority("ADMIN")
                .requestMatchers("/api/v1/account/getbyid/**").permitAll()
                .requestMatchers("/api/v1/account/getbyid/**").permitAll()
                .requestMatchers("/api/v1/account/existsByUserName").permitAll()

                .requestMatchers("/getimage/brand/**").permitAll()
                .requestMatchers("/getimage/product/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Địa chỉ của ứng dụng web của bạn
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
