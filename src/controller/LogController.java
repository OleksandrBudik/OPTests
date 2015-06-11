package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import classes.User;

import dao.AbstractFacadeDAO;
import dao.Facade;

import other.SaxUsers;

public class LogController extends SimpleFormController {
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		Boolean valid = false;
		RegCommand regCommand = (RegCommand) command;
		String login = regCommand.getLogin();
		String password = regCommand.getPassword();
		AbstractFacadeDAO facade = new Facade();
		try {
			request.setCharacterEncoding("Cp1251");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uri = request.getSession().getServletContext().getRealPath("/WEB-INF/users.xml");
		//SaxUsers listUsers = new SaxUsers();
		for (User list: facade.getUserList(uri)){
			if ((list.getLogin().equals(login)) && (list.getPassword().equals(password))) {
				valid=true;
			}				
		}
		if (login.equals("admin")&&password.equals("ukraine")) {
			request.getSession().setAttribute("login", login);
			return new ModelAndView("redirect:adminProfile.htm");			
		}
		if (valid) {
			request.getSession().setAttribute("login", login);
			return new ModelAndView("redirect:testList.htm");
		} else
			return new ModelAndView("redirect:logerr.htm");		
	}
}
