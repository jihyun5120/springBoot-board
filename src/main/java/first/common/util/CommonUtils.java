package first.common.util;

import java.util.UUID;

public class CommonUtils {
	public static String getRandomString() { //32글자의 랜덤한 문자열(숫자포함)을 만들어서 반환해주는 기능.
		return UUID.randomUUID().toString().replace("-", "");
	}
}
