package first.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class AbstractDao { //로그 남기기위한 클래스
	protected Log log = LogFactory.getLog(AbstractDao.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("\t QueryId \t:" + queryId);
		}
	}
	
	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map selectPagingList(String queryId, Object params) {
		printQueryId(queryId);
		
		Map<String, Object> map  = (Map<String, Object>)params;
		PaginationInfo paginationInfo = null;
		
		if (map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true) {
			map.put("currentPageNo", "1"); //현재 페이지 번호
		}
		paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString())); 
		
		if (map.containsKey("page_row") == false || StringUtils.isEmpty(map.get("page_row")) == true) {
			paginationInfo.setRecordCountPerPage(15); //한 페이지에 보여 줄 게시물 수
		} else {
			paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("page_row").toString())); 
		}
		paginationInfo.setPageSize(10); //한번에 보여줄 페이지 크기
		
		int start = paginationInfo.getFirstRecordIndex();
		int end = start + paginationInfo.getRecordCountPerPage();
		map.put("start", start + 1);
		map.put("end", end);
		
		params = map;
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = sqlSession.selectList(queryId, params);
		
		if (list.size() == 0) {
			map = new HashMap<String, Object>();
			map.put("total_count", 0);
			list.add(map);
			
			if (paginationInfo != null) {
				paginationInfo.setTotalRecordCount(0);
				returnMap.put("paginationInfo", paginationInfo);
			}
		} else {
			if (paginationInfo != null) {
				paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("total_count").toString()));
				returnMap.put("paginationInfo", paginationInfo);
			}
		}
		returnMap.put("result", list);
		
		return returnMap;
	}
}
