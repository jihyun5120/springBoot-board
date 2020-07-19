package first.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils") //이 객체의 관리를 스프링이 담당.
public class FileUtils {
	private static final String filePath = "C:\\dev\\file\\"; //파일이 저장 될 위치.
	
	public List<Map<String, Object>> parseInsertFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String boardIdx = (String)map.get("idx");
		
		File file = new File(filePath);
		if (file.exists() == false) {
			file.mkdir();
		}
		
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file); //지정된 경로에 파일을 생성.
				
				listMap = new HashMap<String, Object>();
				listMap.put("board_idx", boardIdx);
				listMap.put("original_file_name", originalFileName);
				listMap.put("stored_file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				list.add(listMap);
			}
		}
		
		return list;
	}
	
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String boardIdx = (String)map.get("idx");
		String requestName = null;
		String idx = null;
		
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;
				
				multipartFile.transferTo(new File(filePath + storedFileName));
				
				listMap = new HashMap<String, Object>();
				listMap.put("is_new", "Y");
				listMap.put("board_idx", boardIdx);
				listMap.put("original_file_name", originalFileName);
				listMap.put("stored_file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				list.add(listMap);
			} else {
				requestName = multipartFile.getName();
				idx = "idx_" + requestName.substring(requestName.indexOf("_") + 1);
				if (map.containsKey(idx) == true && map.get(idx) != null) { //기존에 저장되어있던 파일 정보가 있다면.
					listMap = new HashMap<String, Object>();
					listMap.put("is_new", "N");
					listMap.put("file_idx", map.get(idx));
					list.add(listMap);
				}
			}
		}
		
		return list;
	}
}
