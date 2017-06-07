package com.kdn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kdn.model.biz.BoardService;
import com.kdn.model.domain.Board;
import com.kdn.model.domain.PageBean;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "insertBoardForm.do", method = RequestMethod.GET)
	public String insertBoardForm(Model model){
		model.addAttribute("content", "board/insertBoard.jsp");
		return "index";
	}
	
	@RequestMapping(value = "insertBoard.do", method = RequestMethod.POST)
	public String insertBoard(Board board, HttpServletRequest request){
		String dir = request.getRealPath("upload/");
		boardService.add(board, dir);
		return "redirect:listBoard.do"; //요청정보를 계속 살리기 위해서는 redirect가 필요하다. listBoard를 다시 호출하면 요청정보가 사라진다.
	}
	
	
	@RequestMapping(value = "listBoard.do", method = RequestMethod.GET)
	public String listBoard(PageBean bean, Model model){
		//이때 DTO PageBean이 request로 넘어간다.
		List<Board> list = boardService.searchAll(bean);
		model.addAttribute("list", list);
		model.addAttribute("content", "board/listBoard.jsp");
		return "index";
	}
	
	@RequestMapping(value = "searchBoard.do", method = RequestMethod.GET)
	public String searchBoard(int no, Model model){
		model.addAttribute("board", boardService.search(no));
		model.addAttribute("content", "board/searchBoard.jsp");
		return "index";
	}
}




