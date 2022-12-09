package kr.kosa.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.kosa.emp.EmpDao;


public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    EmpDao dao = new EmpDao();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("서블릿 실행 성공");
		
		int count = dao.getEmpCount();
		request.setAttribute("cnt", count); //뷰(jsp)에 출력할 데이터를 request에 저장
		RequestDispatcher disp = request.getRequestDispatcher("/hello.jsp"); //웹페이지 사이트가 변하지 않는다라는 거....
		disp.forward(request, response); // 뷰로 forward
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
