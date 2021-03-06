package br.com.luizalabs.clientsapi.configuration.security

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter
import org.keycloak.adapters.springsecurity.management.HttpSessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

@Profile(value = ["local"])
@KeycloakConfiguration
class KeycloakConfig : KeycloakWebSecurityConfigurerAdapter() {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val provider = keycloakAuthenticationProvider()
        provider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(provider)
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return NullAuthenticatedSessionStrategy()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**")
            .hasRole("CLIENTS_ADMIN")
            .anyRequest().permitAll()
    }

    @Bean
    fun keycloakAuthenticationProcessingFilterRegistrationBean(
        filter: KeycloakAuthenticationProcessingFilter?
    ): FilterRegistrationBean<*>? {
        val registrationBean: FilterRegistrationBean<*> = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    fun keycloakPreAuthActionsFilterRegistrationBean(
        filter: KeycloakPreAuthActionsFilter?
    ): FilterRegistrationBean<*>? {
        val registrationBean: FilterRegistrationBean<*> = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    fun keycloakAuthenticatedActionsFilterBean(
        filter: KeycloakAuthenticatedActionsFilter?
    ): FilterRegistrationBean<*>? {
        val registrationBean: FilterRegistrationBean<*> = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    fun keycloakSecurityContextRequestFilterBean(
        filter: KeycloakSecurityContextRequestFilter?
    ): FilterRegistrationBean<*>? {
        val registrationBean: FilterRegistrationBean<*> = FilterRegistrationBean(filter)
        registrationBean.isEnabled = false
        return registrationBean
    }

    @Bean
    @ConditionalOnMissingBean(HttpSessionManager::class)
    override fun httpSessionManager(): HttpSessionManager? {
        return HttpSessionManager()
    }

    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver? {
        return KeycloakSpringBootConfigResolver()
    }

}