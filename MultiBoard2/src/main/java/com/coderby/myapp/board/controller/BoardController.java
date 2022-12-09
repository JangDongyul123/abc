package com.coderby.myapp.board.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale.Category;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coderby.myapp.board.model.Board;
import com.coderby.myapp.board.model.BoardCategory;
import com.coderby.myapp.board.model.BoardUploadFile;
import com.coderby.myapp.board.service.IBoardCategoryService;
import com.coderby.myapp.board.service.IBoardService;

@Controller
public class BoardController {
	static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	//로그 출력을 위함
	
	@Autowired
	IBoardService boardService;
	//boardService 객체에 의존성 주입
	
	@Autowired
	IBoardCategoryService categoryService;
	//boardCategoryService 객체에 의존성 주입
	
	@RequestMapping("/board/cat/{categoryId}/{page}")
	public String getListByCategory(@PathVariable int categoryId, @PathVariable int page, HttpSession session, Model model) {
		
		logger.info("@RequestMapping(\"/board/cat/{categoryId}/{page}\")");
		//콘솔창에 로그 출력
		
		session.setAttribute("page", page);
		//session 객체의 page에 저장
		
		model.addAttribute("categoryId", categoryId);
		//model 객체의 categoryId에 저장
		
		List<Board> boardList = boardService.selectArticleListByCategory(categoryId, page);
		//해당 카테고리(게시판, 자료실, 게시글 중 하나)에 해당하면서, 해당 page에 속한 글들을 boardList에 담는다.
		
		model.addAttribute("boardList", boardList);
		//뷰에 forward 할 때 model에 boardList를 담아준다. 
		
		// paging start
		int bbsCount = boardService.selectTotalArticleCountByCategoryId(categoryId);
		//해당 카테고리의 글 전체 개수
		
		int totalPage = 0;
		//전체 페이지 숫자
		
		if(bbsCount > 0) {
			totalPage= (int)Math.ceil(bbsCount/10.0);
		}
		model.addAttribute("totalPageCount", totalPage);
		//전체 페이지 수
		
		model.addAttribute("page", page);
		//page 변수는 현재 페이지
		
		logger.info("page: "+page+"  totalpage: "+totalPage);
		//로그 남기는 메서드
		
		return "board/list";
		//forward 해준다.
	}

	@RequestMapping("/board/cat/{categoryId}")
	public String getListByCategory(@PathVariable int categoryId, HttpSession session, Model model) {
		logger.info("@RequestMapping(\"/board/cat/{categoryId}\")");
		//콘솔창에 로그 출력
		
		logger.info("categoryId: "+categoryId+"  session: "+session+" model "+model);
		//콘솔창에 로그 출력
		
		return getListByCategory(categoryId, 1, session, model);
		//아마도 같은 카테코리 Id와 page와 session 정보와 model을 공유하기 위해 매개변수로 보내는 듯 싶다.
	}
	
	@RequestMapping("/board/{boardId}/{page}")
	public String getBoardDetails(@PathVariable int boardId, @PathVariable int page, Model model) {
		
		logger.info("@RequestMapping(\"/board/{boardId}/{page}\")");
		//콘솔창에 로그 출력
		
		Board board = boardService.selectArticle(boardId);
		//글번호를 통해 글 정보를 가져온다.
		
		model.addAttribute("board", board);
		//글 정보를 model 에 담는다.
		
		model.addAttribute("page", page);
		//현재 page 정보를 model에 담는다.
		
		model.addAttribute("categoryId", board.getCategoryId());
		//logger.info("getBoardDetails " + board.toString());
		logger.info("board: "+board+" page: "+page+" categoryId: "+ board.getCategoryId());
		
		//콘솔창에 로그 출력
		return "board/view";
	}
  
	@RequestMapping("/board/{boardId}")
	public String getBoardDetails(@PathVariable int boardId, Model model) {
		
		logger.info("@RequestMapping(\"/board/{boardId}\")");
		
		return getBoardDetails(boardId, 1, model);
		// 현재 글번호와 1번째 페이지와 model 정보를 담는다.
		// 2번째 페이지의 글 또는 3번째 페이지의 글도 page 정보가 1이 담기게 되므로 여쭤봤다.
		// 편의상 그렇게 하셨다고 하셨다.
		// 원래는 목록에서 글을 클릭하면 /board/게시글번호/현재페이지 
		// 예를 들면 목록의 2번째 페이지에서 3번 글을 클릭하면
		// /board/3/2 이렇게 가야하는데
		// 교수님께서는
		// /board/3   으로만 가게 하시고
		// 현재페이지 정보는 고정으로 1만 가게 하심
		// 왜냐하면 게시글이 많이 생기면 해당 게시글이 속한 현재 페이지도 1씩 증가되니까.
	}
	
	@RequestMapping(value="/board/write/{categoryId}", method=RequestMethod.GET)
	public String writeArticle(@PathVariable int categoryId, Model model) {
		
		logger.info("@RequestMapping(value=\"/board/write/{categoryId}\", method=RequestMethod.GET)");
		//해당 카테고리에 글 쓰는 페이지다.
		//예를 들면 /board/write/1 이면 게시판에 글 쓰는 페이지
		//       /board/write/2 이면 자료실에 글 쓰는 페이지
		//		 /board/write/3 이면 갤러리에 글 쓰는 페이지
		
		//카테고리 아이디, 모델이 매개변수다.
		List<BoardCategory> categoryList = categoryService.selectAllCategory();
		//카테고리 리스트에 모든 카테고리를 담는다. 
		model.addAttribute("categoryList", categoryList);
		//카테고리 리스트를 담는다.
		model.addAttribute("categoryId", categoryId);
		//현재 카테고리 아이디를 담는다. 예를 들면 게시판에 글 쓰는 페이지면 현재 categoryId는 1이다.
		return "board/write";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String writeArticle(@ModelAttribute Board board, BindingResult result, RedirectAttributes redirectAttrs) {
		//result.addError(error);
		logger.info("@RequestMapping(value=\"/board/write\", method=RequestMethod.POST)");
		
		//게시판 글쓰기 화면에서 글을 적은 다음 글을 등록하면 여기로 오는 것 같다.
		//굳이 @requestParameter를 안써도, 1번째 매개변수 클래스의 필드 이름과 (즉 board 객체의 필드이름과)
		//프론트단의 form 태그 내부 태그들의 name 이름이 일치하다면
		//1번째 매개변수 클래스 객체 필드에 name 값들을 담아준다.
		//어떤 클래스든 상관없다. 여기서는 Board board 의 필드에 이름이 일피하는 name들을 담아주는 것이다
		//예를 들면 1번째 매개변수인 board의 필드에는 title이 있다. 
		//프론트단에는 form 태그 내부 태그의 name 속성에도 title이 있으면 1대1 맵핑 시켜주는 것이다.
		//다시 말해서 name 속성의 값을 board 의 필드인 name에 저장해줌
		//logger.info(result.getClass());
		//result.getFieldError();
		logger.info("/board/write1 : " + board.toString());
		//로그 출력을 위함
		
		try{
			//XSS 방지 기능을 제공하기 위해 Jsoup 을 사용해서 화이트 리스트 방식의 HTML 문서 필터링을 한다. 
			board.setTitle(Jsoup.clean(board.getTitle(), Whitelist.basic()));
			//이미 만들어져있는 Whitelist 를 이용하여 문서 필터링을 한다.
			board.setContent(Jsoup.clean(board.getContent(), Whitelist.basic()));
			//이미 만들어져있는 Whitelist 를 이용하여 문서 필터링을 한다.
			MultipartFile mfile = board.getFile();
			//
			if(mfile!=null && !mfile.isEmpty()) {
				//파일 첨부를 했으면
				//그런데, mfile!=null과 !mfile.isEmpty() 의 차이가 대체 뭘까?
				//null 은 인스턴스가 생성되지 않은 상태다.
				//isEmpty()는 인스턴스는 있지만 안에 아무것도 적재하지 않은 상태다
				//!mfile.isEmpty()만 썼을 때는 nullpointerexception을 처리하기 어렵고
				//mfile!=null 만 썼을 때는 0바이트를 처리하기 어렵다.
				logger.info("/board/write2 : " + mfile.getOriginalFilename());
				//로그 출력을 위함
				
				BoardUploadFile file = new BoardUploadFile();
				//
				file.setFileName(mfile.getOriginalFilename());
				//
				file.setFileSize(mfile.getSize());
				//
				file.setFileContentType(mfile.getContentType());
				//
				file.setFileData(mfile.getBytes());
				//
				logger.info("/board/write : " + file.toString());

				boardService.insertArticle(board, file);
			}else {
				//첨부파일이 없는 경우
				boardService.insertArticle(board);
				//파일 없는 게시글을 DB에 입력한다.
				
				//throw new RuntimeException();
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
			//세션에 에러메세지 
		}
		return "redirect:/board/cat/"+board.getCategoryId();
	}

	@RequestMapping("/file/{fileId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int fileId) {
		
		logger.info("@RequestMapping(\"/file/{fileId}\")");
		
		//파일 아이디에 해당하는 파일을 다운로드 하는 메서드
		BoardUploadFile file = boardService.getFile(fileId);
		//
		logger.info("getFile " + file.toString());
		//로그 출력을 위함
		
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getFileContentType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getFileSize());
		headers.setContentDispositionFormData("attachment", file.getFileName(), Charset.forName("UTF-8"));
		return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/board/reply/{boardId}", method=RequestMethod.GET)
	public String replyArticle(@PathVariable int boardId, Model model) {
		
		logger.info("@RequestMapping(value=\"/board/reply/{boardId}\", method=RequestMethod.GET)");		
		//boarid 에 해당하는 게시글에 댓글 다는 메서드		
		
		Board board = boardService.selectArticle(boardId);
		board.setWriter("");
		board.setEmail("");
		board.setTitle("[Re]"+board.getTitle());
		board.setContent("\n\n\n----------\n" + board.getContent());
		model.addAttribute("board", board);
		model.addAttribute("next", "reply");
		return "board/reply";
	}
	
	@RequestMapping(value="/board/reply", method=RequestMethod.POST)
	public String replyArticle(Board board, BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		logger.info("@RequestMapping(value=\"/board/reply\", method=RequestMethod.POST)");
		logger.info("/board/reply : " + board.toString());
		//로그 출력을 위함
		
//	    if(result.hasErrors()) {
//	    	logger.debug(result.getErrorCount());
//	        return "board/write";
//	    }
		try{
			board.setTitle(Jsoup.clean(board.getTitle(), Whitelist.basic()));
			board.setContent(Jsoup.clean(board.getContent(), Whitelist.basic()));
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				//파일 첨부를 했으면
				//그런데, mfile!=null과 !mfile.isEmpty() 의 차이가 대체 뭘까?
				//null 은 인스턴스가 생성되지 않은 상태다.
				//isEmpty()는 인스턴스는 있지만 안에 아무것도 적재하지 않은 상태다
				//!mfile.isEmpty()만 썼을 때는 nullpointerexception을 처리하기 어렵고
				//mfile!=null 만 썼을 때는 0바이트를 처리하기 어렵다.
				
				logger.info("/board/reply : " + mfile.getOriginalFilename());
				//로그 출력을 위함
				
				BoardUploadFile file = new BoardUploadFile();
				file.setFileName(mfile.getOriginalFilename());
				file.setFileSize(mfile.getSize());
				file.setFileContentType(mfile.getContentType());
				file.setFileData(mfile.getBytes());
				logger.info("/board/reply : " + file.toString());
				//로그 출력을 위함
				
				boardService.replyArticle(board, file);
			}else {
				boardService.replyArticle(board);
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		
		if(session.getAttribute("page") != null) {
			return "redirect:/board/cat/"+board.getCategoryId()+"/"+(Integer)session.getAttribute("page");
		}else {
			return "redirect:/board/cat/"+board.getCategoryId(); 
		}
	}

	@RequestMapping(value="/board/update/{boardId}", method=RequestMethod.GET)
	public String updateArticle(@PathVariable int boardId, Model model) {
		//글 수정하는 메서드
		logger.info("@RequestMapping(value=\"/board/update/{boardId}\", method=RequestMethod.GET)");
		
		List<BoardCategory> categoryList = categoryService.selectAllCategory();
		//모든 카테고리 리스트를 담는다.
		
		model.addAttribute("categoryList", categoryList);
		Board board = boardService.selectArticle(boardId);
		model.addAttribute("categoryId", board.getCategoryId());
		model.addAttribute("board", board);
		return "board/update";
	}

	@RequestMapping(value="/board/update", method=RequestMethod.POST)
	public String updateArticle(Board board, BindingResult result, HttpSession session, RedirectAttributes redirectAttrs) {
		logger.info("@RequestMapping(value=\"/board/update\", method=RequestMethod.POST)");
		logger.info("/board/update " + board.toString());
		//로그 출력을 위함
		
		String dbPassword = boardService.getPassword(board.getBoardId());
		//
		if(!board.getPassword().equals(dbPassword))
		{
			//throw new RuntimeException("게시글 비밀번호가 다릅니다.");
			redirectAttrs.addFlashAttribute("passwordError", "게시글 비밀번호가 다릅니다");
			return "redirect:/board/update/"+board.getBoardId();
		}
		try{
			board.setTitle(Jsoup.clean(board.getTitle(), Whitelist.basic()));
			board.setContent(Jsoup.clean(board.getContent(), Whitelist.basic()));
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				//파일 첨부를 했으면
				//그런데, mfile!=null과 !mfile.isEmpty() 의 차이가 대체 뭘까?
				//null 은 인스턴스가 생성되지 않은 상태다.
				//isEmpty()는 인스턴스는 있지만 안에 아무것도 적재하지 않은 상태다
				//!mfile.isEmpty()만 썼을 때는 nullpointerexception을 처리하기 어렵고
				//mfile!=null 만 썼을 때는 0바이트를 처리하기 어렵다.
				
				logger.info("/board/update : " + mfile.getOrig	inalFilename());
				//로그 출력을 위함
				
				BoardUploadFile file = new BoardUploadFile();
				file.setFileId(board.getFileId());
				file.setFileName(mfile.getOriginalFilename());
				file.setFileSize(mfile.getSize());
				file.setFileContentType(mfile.getContentType());
				file.setFileData(mfile.getBytes());
				logger.info("/board/update : " + file.toString());
				//로그 출력을 위함		
				boardService.updateArticle(board, file);
			}else {
				boardService.updateArticle(board);
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/board/"+board.getBoardId();
	}

	@RequestMapping(value="/board/delete/{boardId}", method=RequestMethod.GET)
	public String deleteArticle(@PathVariable int boardId, Model model) {
		logger.info("@RequestMapping(value=\"/board/delete/{boardId}\", method=RequestMethod.GET)");
		
		Board board = boardService.selectDeleteArticle(boardId);
		logger.info(board.toString());
		model.addAttribute("categoryId", board.getCategoryId());
		model.addAttribute("boardId", boardId);
		model.addAttribute("replyNumber", board.getReplyNumber());
		return "board/delete";
	}
	
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String deleteArticle(Board board, BindingResult result, HttpSession session, Model model) {
		logger.info("@RequestMapping(value=\"/board/delete\", method=RequestMethod.POST)");
		
		try {
			String dbpw = boardService.getPassword(board.getBoardId());
			//기본키를 통해 비번을 가져온다.
			
			if(dbpw.equals(board.getPassword())) {
				boardService.deleteArticle(board.getBoardId(), board.getReplyNumber());
				//게시글 번호와 댓글 번호 모두 넘겨주는 이유는 트랜잭션 처리를 위해서
				return "redirect:/board/cat/"+board.getCategoryId()+"/"+(Integer)session.getAttribute("page");
			}else {
				model.addAttribute("message", "WRONG_PASSWORD_NOT_DELETED");
				return "error/runtime";
			}
		}catch(Exception e){
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "error/runtime";
		}
	}

	@RequestMapping("/board/search/{page}")
	public String search(@RequestParam(required=false, defaultValue="") String keyword, @PathVariable int page, HttpSession session, Model model) {
		logger.info("@RequestMapping(\"/board/search/{page}\")");
		
		try {
			List<Board> boardList = boardService.searchListByContentKeyword(keyword, page);
			model.addAttribute("boardList", boardList);
	
			// paging start
			int bbsCount = boardService.selectTotalArticleCountByKeyword(keyword);
			int totalPage = 0;
			System.out.println(bbsCount);
			if(bbsCount > 0) {
				totalPage= (int)Math.ceil(bbsCount/10.0);
			}
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			logger.info(totalPage + ":" + page + ":" + keyword);
			//로그 출력을 위함
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "board/search";
	}
}