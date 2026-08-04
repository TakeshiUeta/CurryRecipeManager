package com.takeshiueta.curryrecipemanager.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** アプリケーション共通例外処理 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * リソース不存在エラー
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFound(ResourceNotFoundException e, Model model) {
		//エラーメッセージ
		model.addAttribute("errorMessage", e.getMessage());
		//画面遷移
		return "error/sc991system-error";
	}
	
	/**
	 * DBアクセスエラー
	 */
	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(
	        DataAccessException e,
	        Model model) {
	    e.printStackTrace();
	    model.addAttribute(
	        "errorMessage",
	        "データベース処理中にエラーが発生しました。"
	    );
	    return "error/sc991system-error";
	}
	
	/**
	 * 想定外エラー
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		// ログ出力用
		e.printStackTrace();

		// 画面表示用
		model.addAttribute(
				"errorMessage",
				"予期せぬエラーが発生しました。"
		);
		return "error/sc991system-error";
	}
}
