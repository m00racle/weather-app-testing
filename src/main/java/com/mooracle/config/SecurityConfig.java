package com.mooracle.config;

import com.mooracle.domain.User;
import com.mooracle.service.UserService;
import com.mooracle.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/** Entry 41: com.mooracle.config.SecurityConfig
 *  1.  This class configure Spring Security mostly related to authentication on log in and authorization
 *  2.  This class extends Spring security web security confgurer adapter (link in the README
 *  3.  This class has so many elements comes from springframework security. One
 * */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    /** Notes:
     * We build a new method that auto instatiated to act as Builder for authentication manager.
     * Basically this method set user details to use passwordEncoder when saved into database. This will encrypt the
     * password data so even if our database was breached the data will still be encrypted.
     *
     * please refer to User entity class and see that inside it already has PASSWORD_ENCODER as constant filed. This
     * encoder used to encrypt the password String.
     * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(userService).passwordEncoder(User.PASSWORD_ENCODER);
    }

    /** Notes:
     * Okay we start to configure what we need the security will looks like
     * First we will make sure that authentication does not limit the access to static contents. Thus the checking to
     * static assets will be by passed.
     *
     * To do this we need to @Override the configure method and select WebSecurity to be configured
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    /** Notes:
     * Next we need to configure login and logout handlers. In this app we will not make too much procedures like what
     * we did in the to do list app. This time we will be staight forward and put reference on the "/login" URL
     * as the only templates that handles the view of both login and logout. The controller that accepts request to
     * "/login" URL will take the rest.
     *
     * Please note that login (for both success and failure) will be handled on separate method. The logout handler
     * is not necessary and only redirect it back to /login template web page with no flash since no handler available
     *
     * This app use CSRF security protection.
     *
     * This method also @Override configure HttpSecurity
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login") //<- this is the chosen url
                    .successHandler(loginSuccessHandler()) //<- make this method later
                    .failureHandler(failureLoginHandler()) //<- make this method later
                .and().logout()
                    .logoutSuccessUrl("/login")
                .and().csrf();
    }

    /** Notes
     * This method is used for configuring the HTTP security above
     *
     * This method handles what happen when login authentication fails. It will also includes a flash attribute as
     * redirect attribute. Then after that failure will need FAILURE status then redirect back to "/login" URL
     *
     * The attribute name flash is already set in the login template
     * */
    private AuthenticationFailureHandler failureLoginHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("flash",
                                new FlashMessage("Incorrect username and/or password. Try again",
                                                FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        };
    }

    /** Notes
     * This method is used for configuring the HTTP security above
     *
     * This will utilize lambda form of class to handle the next request after the user succesfully provides
     * authentication to login. This case we will redirect it to "/" url using response redirect
     * */
    private AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> response.sendRedirect("/");
    }

    /** Notes
     * To wrap all up we build a @Bean on EvaluationContextExtension interface which the full definition and explanation
     * is not yet clear. However, this is as concept not yet clear. Most links I can find are listed in the README.md
     * please put time to inspect the GitHUb git on each file.
     * */
    public EvaluationContextExtension securityExtension(){
        return new EvaluationContextExtensionSupport() {
            @Override
            public String getExtensionId() {
                return "sec";
            }

            @Override
            public Object getRootObject() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new SecurityExpressionRoot(authentication) {
                };
            }
        };
    }
}
