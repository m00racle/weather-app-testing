package com.mooracle.web.controller;

import com.mooracle.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/** Entry 36: com.mooracle.web.controller.LoginController
 *  1.  This controller class handles requests for login
 *  2.  If there was a flash message the View par in the templates/layout.html will send an attribute named "flash"
 *  3.  If there is "flash" attribute included in the request it will be added into the login page and shown in login
 *  4.  If not just proceed as usual and only consist of attribute name user
 *  5.  If there is a user failed to provide proper authentication send access denied warning page
 * */

@Controller
public class LoginController {

    /** Notes:
     * This method handles request to /login page.
     * It has by default a user attribute to be added into the model map
     * if there is prior flash message then the attribute will be get from the request session and put it into model map
     * after that we will delete the attribute from the session to prevent the attribute to pop up in the next request
     * */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request){
        model.addAttribute("user", new User());
        try {
            // test if a flash object si in the request attribute from prvious /login

            Object flash = request.getSession().getAttribute("flash");

            // if there is an attribute name flash put it back into model map to be rendered in the latest /login page

            model.addAttribute("flash", flash);

            // after added, delete the original flash attribute from the request

            request.getSession().removeAttribute("flash");
        } catch (Exception ex){
            // "flash" session attribute does not exist.. do nothing and proceed normally
        }
        return "login"; // <- use "login" template as rendered view.
    }

    /** Notes:
     * This access denied web page is not available at the moment. I guess it will be a plain web page with big Warning
     * words "Access denied"
     * */
    @RequestMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
