package com.example.application.security;

import static com.vaadin.flow.spring.security.VaadinSecurityConfigurer.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.application.data.UserApp;
import com.example.application.data.UserAppRepository;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
@Slf4j
public class SecurityConfiguration {
    private final UserAppRepository userAppRepository;

    

    public SecurityConfiguration(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain vaadinSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/images/*.png", "/*.css").permitAll());

        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/line-awesome/**").permitAll());

        http.with(vaadin(), vaadin -> {
            vaadin.oauth2LoginPage("/oauth2/authorization/keycloak");
        });

        return http.build();
    }

    @Bean
    GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			authorities.forEach(authority -> {
				if (OidcUserAuthority.class.isInstance(authority)) {
					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

					// OidcIdToken idToken = oidcUserAuthority.getIdToken();
					OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                    List<String> roles = userInfo.getClaim("roles");
					// Map the claims found in idToken and/or userInfo
					// to one or more GrantedAuthority's and add it to mappedAuthorities
                    // log.info("username : " + userInfo.getPreferredUsername());

                    // ===tes bana====
                    // if(roles != null){
                    //     roles.stream().forEach(r -> {
                    //         SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(r);
                    //         mappedAuthorities.add(simpleGrantedAuthority);
                    //     });
                    // }
                    
                    //  ===end tes bana====
                    
                    // kalo Role ngambil dari tabel database
                    // comment tes bana
                    // uncomment tes bana2

                    // =======tes bana2====
                    UserApp userApp = userAppRepository.findById(userInfo.getPreferredUsername()).get();
                    userApp.getRoles().stream().forEach(role -> {
                        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.name());
                        mappedAuthorities.add(simpleGrantedAuthority);
                    });
                    // ===== end tes bana2====

                    // mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				// } else if (OAuth2UserAuthority.class.isInstance(authority)) {
				// 	OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

				// 	Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

					// Map the attributes found in userAttributes
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				}
			});

			return mappedAuthorities;
		};
	}
}
