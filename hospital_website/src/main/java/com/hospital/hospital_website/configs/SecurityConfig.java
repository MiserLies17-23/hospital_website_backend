package com.hospital.hospital_website.configs;


import com.hospital.hospital_website.services.UserDetailsServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация безопасности SpringSecurity для веб-приложений
 * Настраивает:
 *  * авторизацию по ролям
 *  * открытый и приватный доступ к различным страницам
 *  * CORS для localhost: 3000
 *  * BCrypt для шифрования паролей
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final UserDetailsServiceImp userDetailsService;

    /**
     * Основная цепочка фильтров безопасности
     * Отключает CSRF, настраивает авторизацию и CORS
     *
     * @param http объект конфигурации SpringSecurity
     * @return настроенная цепочка фильтров
     * @throws Exception используется для обработки ошибок конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/signup", "/news", "/time",
                                "/hospitalImages/**", "/userImages/**", "/doctors").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/moderator/**").hasRole("MODERATOR")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> {})
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    /**
     * Конфигурация CORS для фронтенда на localhost: 3000
     *
     * @return интерфейс, предоставляющий CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        configuration.setAllowCredentials(true);

        configuration.addExposedHeader("Cache-Control");
        configuration.addExposedHeader("Pragma");
        configuration.addExposedHeader("Expires");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Шифровщик паролей BCrypt
     * Предназначен для хеширования паролей пользователей
     *
     * @return объект BCryptPasswordEncoder() для хеширования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Менеджер аутентификации SpringSecurity
     * Предназначен для проверки пользовательских данных с бд
     *
     * @param configuration конфигурация аутентификации
     * @return конфигурация AuthenticationManager
     * @throws Exception используется для обработки ошибок конфигурации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Провайдер аутентификации на основе UserDetailsServiceImp
     * Скрывает логику аутентификации и связывает шифровщик паролей и сервер аутентификации
     *
     * @return настроенные провайдер AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    /**
     * Регистрирует статические ресурсы /hospitalImages/** и /userImages/**
     * Сопоставляет url с локальным файловым хранилищем
     * Отключает кэширование
     *
     * @param registry реестр обработчиков ресурсов
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/hospitalImages/**")
                .addResourceLocations("file:C:/users/THUNDEROBOT/ideaProjects/hospital_website/hospital_website/src/main/resources/static/images/hospitalImages/")
                .setCachePeriod(0)
                .setCacheControl(CacheControl.noStore().mustRevalidate());

        registry.addResourceHandler("/userImages/**")
                .addResourceLocations("file:C:/users/THUNDEROBOT/ideaProjects/hospital_website/hospital_website/src/main/resources/static/images/userImages/")
                .setCachePeriod(0)
                .setCacheControl(CacheControl.noStore().mustRevalidate());
    }
}
