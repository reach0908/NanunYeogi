package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice	//해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public String except(Exception e, Model model) {
		
		log.error("Exeption......." + e.getMessage());
		model.addAttribute("exception", e);
		log.error(model);
		
		return "error_page";
	}
	
	//500메세지는 문법 오류 발생시 @ExceptionHandler를 이용해서 처리가 가능하지만,
	//404메세지는 오류가 아닌 URL을 잘못 호출했을 때 처리해야한다. 따라서 서블릿이 web.xml에 간 후
	//어디로 가야할 지 모를 때 NoHandlerFoundException을 발생시켜줌으로써 예외가 발생되고
	//예외발생시 CommonExceptionAdvice객체가 잡아준다. 따라서 404메세지 대신 원하는 페이지로 응답할 수 있게 된다.
	@ExceptionHandler(NoHandlerFoundException.class)
	
	//응답시 발생하시는 오류코드가 NOT_FOUND일 때 작동되는 메소드이다.
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException nhfe) {
		return "error404_page";
	}
}













