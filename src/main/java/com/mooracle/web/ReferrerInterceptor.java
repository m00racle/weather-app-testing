package com.mooracle.web;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Entry 34: ReferrerInterceptor class extends HandlerInterceptorAdapter
 *  1.  This class intercept referrer and handle it using HTTP servlet request and response
 *  2.  This class extends HandlerInterceptorAdapter more about it in the README section
 *  3.  This class will handle redirects using the correct format
 *  4.  More on HTTP servlet in the README section
 * */

public class ReferrerInterceptor extends HandlerInterceptorAdapter {
    // field constant (final in this case)
    public static final String ATT_REFERRER_URI = "referrer";

    /** Note:
     * insert the @Override method from HandlerInterceptorAdapter
     * this override will change the Request session by setting the attribute using, ATT_REFERRER_URI object as the
     * attribute name and get the Request uri from the user as the value
     *
     * Patch -> make Http session request to be less eager to create sesssion
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Patched: add request.getSession create to be false at the moment
        HttpSession session = request.getSession(false);
        if (session != null) {
            request.getSession().setAttribute(ATT_REFERRER_URI, request.getRequestURI());
        }
    }

    /** Note:
     *  This method will return a String format to handle redirect using the request by HTTP servlet
     * */
    public static String redirect(HttpServletRequest request){
        return "redirect:" + request.getSession().getAttribute(ATT_REFERRER_URI);
    }
}
