import java.io.UnsupportedEncodingException;

public class TestEncode {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String s = "我是一个中国人";
		// 乱码
		System.out.println(new String(s.getBytes("ISO8859-1"), "UTF-8"));
	}

}
