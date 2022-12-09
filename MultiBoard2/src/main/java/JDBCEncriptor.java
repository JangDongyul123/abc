import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class JDBCEncriptor {
	public static void main(String[] args) {
		StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		//암호화 객체 생성
		enc.setPassword("gjwlsrudwkd"); 
		//비밀번호가 아니다
		//암호화키이면서 복호화키
		//복호화키는 키 생성 방법에 의해서 어렵게 만드는 게 중요하다
		//아무거나 적으면 된다
		System.out.println(enc.encrypt("oracle.jdbc.OracleDriver"));
		System.out.println(enc.encrypt("jdbc:oracle:thin:@localhost:1521:xe"));
		System.out.println(enc.encrypt("hr"));
		System.out.println(enc.encrypt("hr"));
		
		
	}
}
