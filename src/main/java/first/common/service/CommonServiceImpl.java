package first.common.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import first.common.dao.CommonDao;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="commonDao")
	private CommonDao commonDao;

	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		
		return commonDao.selectFileInfo(map);
	}
}
