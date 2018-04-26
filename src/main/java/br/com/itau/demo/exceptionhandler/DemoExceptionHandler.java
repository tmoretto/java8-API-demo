package br.com.itau.demo.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagem = messageSource.getMessage("request.invalida", null, LocaleContextHolder.getLocale());
		return handleExceptionInternal(ex, new ErroCustomizado(mensagem, ex.getMessage()), headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagem = messageSource.getMessage("dados.obrigatorios", null, LocaleContextHolder.getLocale());
		return handleExceptionInternal(ex, new ErroCustomizado(mensagem, ex.getMessage()), headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		String mensagem = messageSource.getMessage("recurso.naoencontrado", null, LocaleContextHolder.getLocale());
		return handleExceptionInternal(ex, new ErroCustomizado(mensagem, ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	public static class ErroCustomizado {
		private String msgErroAmigavel;
		private String msgErro;

		public ErroCustomizado(String msgErroAmigavel, String msgErro) {
			super();
			this.msgErroAmigavel = msgErroAmigavel;
			this.msgErro = msgErro;
		}

		public String getMsgErroAmigavel() {
			return msgErroAmigavel;
		}

		public void setMsgErroAmigavel(String msgErroAmigavel) {
			this.msgErroAmigavel = msgErroAmigavel;
		}

		public String getMsgErro() {
			return msgErro;
		}

		public void setMsgErro(String msgErro) {
			this.msgErro = msgErro;
		}

	}

}
