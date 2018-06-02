package com.mooracle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/** Entry 42: com.mooracle.config.TemplateConfig
 *  1.  This class configure the template engine in this case Thymeleaf.
 *  2.  This class handles the configuration on the relation between Thymeleaf specific to Springframework reources
 *  3.  Also this class configure the template engine related to Security measures implemented
 *  4.  At most this configuration class is pretty straightforward.
 *  5.  However, Thymeleaf is not much of a GitHub user thus not much files in the version control
 * */

@Configuration
public class TemplateConfig {
    /** Notes
     * Configure the template resolver which denotes where the path to the templates are located, Also denotes what
     * file extensions the template has. In this case we only use HTML file as tempale as the CSS and JavaScript will
     * be integrated by within the HTML file.
     *
     * The way we set the location is by set the prefix and suffix of the template thus when controller use Spring and
     * Thymeleaf to render a web page it will only need to return "the name of the template and path from /templates/
     * Please note that this method does not specifically set the location of the templates rather it only add path
     * address and extension of the template as String to be concatenated with the returned template engine in the
     * contoller to form a full (String) path i.e. /templates/weather/detail.html when the controller only returns
     * "/weather/detail"
     * */
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);

        // set the location of the templates

        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }

    /** Notes
     * Next we will configure the template engine. The template engine job is to set the path to the location of the
     * template that will be used and also add functionality coded as dialect. The location of the template already
     * defined by the templateRsolver method thus we can just use it as it is since it was already be a @Bean.
     *
     * The dialect on the other hand must be more specifc. Since we also implemented Spring Security and inside it
     * already consists all Spring Boot dialect we can just use that as chosen dialect for this app.
     * */
    @Bean
    public SpringTemplateEngine springTemplateEngine(){
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        // add the template resolver

        springTemplateEngine.addTemplateResolver(templateResolver());

        // add the dialect of the app

        springTemplateEngine.addDialect(new SpringSecurityDialect());

        return springTemplateEngine;
    }

    /** Notes
     * Lastly we set all of these into Thymeleaf main function, the view resolver. Here we set which template engine to
     * use and also the priority of the rendering order.
     *
     * As for the templateEngine we use the templateEngine @Bean method.
     * */
    @Bean
    public ThymeleafViewResolver viewResolver(){
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }
}
