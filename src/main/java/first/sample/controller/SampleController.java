package first.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.common.common.CommandMap;
import first.sample.service.SampleService;
import oracle.net.aso.s;

@Controller
public class SampleController {
		Logger log = LoggerFactory.getLogger(this.getClass());
		
		@Resource(name="sampleService")
		private SampleService sampleService;
		
		@RequestMapping(value="/sample/openBoardList.do")
		public ModelAndView openSampleBoardList(CommandMap commandMap, Model model) throws Exception { //게시글 목록 페이지
			ModelAndView mv = new ModelAndView("/sample/boardList");
			
			Map<String, Object> resultMap = sampleService.selectBoardList(commandMap.getMap());
			
			mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
			mv.addObject("list", resultMap.get("result"));
			
			return mv;
		}
		
		@RequestMapping(value="/sample/openBoardWrite.do")
		public ModelAndView openBoardWrite(CommandMap commandMap) { //글 쓰기 페이지
			ModelAndView mv = new ModelAndView("/sample/boardWrite");
			
			return mv;
		}
		
		@RequestMapping(value="/sample/insertBoard.do")
		public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception { //글 작성
			ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
			sampleService.insertBoard(commandMap.getMap(), request);
			
			return mv;
		}
		
		@RequestMapping(value="/sample/openBoardDetail.do")
		public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception { //글 상세보기 페이지
			ModelAndView mv = new  ModelAndView("/sample/boardDetail");
			Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
			
			mv.addObject("map", map.get("map"));
			mv.addObject("list", map.get("list"));
			return mv;
		}
		
		@RequestMapping(value="/sample/openBoardUpdate.do")
		public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{ //글 수정 페이지
			ModelAndView mv = new ModelAndView("/sample/boardUpdate"); 
			Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
			
			mv.addObject("map", map.get("map"));
			mv.addObject("list", map.get("list"));
			return mv;
		}
		
		@RequestMapping(value="/sample/updateBoard.do")
		public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception { //글 수정
			ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
			sampleService.updateBoard(commandMap.getMap(), request);
			
			mv.addObject("idx", commandMap.get("idx"));
			return mv;
		}
		
		@RequestMapping(value="/sample/deleteBoard.do")
		public ModelAndView deleteBoard(CommandMap commandMap) throws Exception { //글 삭제
			ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
			sampleService.deleteBoard(commandMap.getMap());
			
			return mv;
		}
		
		@RequestMapping(value="/sample/testMapArgumentResolver.do")
		public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception {
			ModelAndView mv = new ModelAndView("");
			
			if (commandMap.isEmpty() == false) {
				Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					log.debug("key : " + entry.getKey() + ", value : " + entry.getValue());
				}
			}
			
			return mv;
		}
}
