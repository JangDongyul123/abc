package kr.kosa.web;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.kosa.emp.EmpDao;
import kr.kosa.emp.EmpVo;
import oracle.sql.DATE;



public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	public EmpServlet() {
		super();
		System.out.println("EmpServlet 생성자 실행");
	}


	EmpDao dao = new EmpDao();
	EmpVo empvo = new EmpVo();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf('/'));
		String view = "/index.jsp";
		if("/EmpList.do".equals(cmd)) {
			System.out.println("모든 사원의 정보를 조회합니다.");
			//DAO 메소드 호출
			//request에 정보 저장
			request.setAttribute("empList", dao.getAllEmps());
			System.out.println(dao.getAllEmps().size());
			//뷰로 포워드(뷰 경로를 지정)
			view="/WEB-INF/views/emp/emplist.jsp";
		}else if("/EmpInsert.do".equals(cmd)) {
			System.out.println("입력 양식을 요청합니다.");
			request.setAttribute("jobIdList", dao.getJobIdList());
			request.setAttribute("deptIdList", dao.getDeptIdList());
			request.setAttribute("empIdList", dao.getEmpIdList());
			request.setAttribute("maxEmpId", dao.getMaxEmpId());
			view="/WEB-INF/views/emp/empform.jsp";
		}else if ("/EmpDetails.do".equals(cmd)) {
			System.out.println("상세 정보를 요청합니다.");
			String empidStr = request.getParameter("empid");
			int empid = Integer.parseInt(empidStr);
			request.setAttribute("emp", dao.getEmpDetails(empid));
			view="/WEB-INF/views/emp/empdetails.jsp";
		}else if("/EmpUpdate.do".equals(cmd)) {
			System.out.println("수정 정보를 요청합니다.");
			String empidStr = request.getParameter("empid");
			int empid = Integer.parseInt(empidStr);
			request.setAttribute("emp", dao.getEmpDetails(empid));
			request.setAttribute("jobIdList", dao.getJobIdList());
			request.setAttribute("deptIdList", dao.getDeptIdList());
			request.setAttribute("empIdList", dao.getEmpIdList());
			view="/WEB-INF/views/emp/empupdateform.jsp";
		}else if("/EmpDelete.do".equals(cmd)) {
			System.out.println("정보 삭제를 요청합니다.");
			view="/WEB-INF/views/emp/empdeleteform.jsp";
		}
		RequestDispatcher disp =request.getRequestDispatcher(view);
		disp.forward(request, response);// 서버로 가져온 값을 화면에 출력
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf('/'));
		if("/EmpInsert.do".equals(cmd)) {
			//입력 처리
			String employeeId = request.getParameter("employeeId");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date hireDate = null;
			try {
				
				hireDate = new Date(sdf.parse(request.getParameter("hireDate")).getTime());
			} catch (ParseException e) {
				System.out.println("날짜 형식 오류");
				e.printStackTrace();
			}
			String jobId = request.getParameter("jobId");
			String salary = request.getParameter("salary");
			String commissionPct = request.getParameter("commissionPct");
			String managerId = request.getParameter("managerId");
			String departmentId = request.getParameter("departmentId");
			
			EmpVo emp = new EmpVo();
			emp.setEmployeeId(Integer.parseInt(employeeId));
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
			//emp.setHireDate(Date.valueOf(hireDate));
			emp.setHireDate(hireDate);
			emp.setJobId(jobId);
			emp.setSalary(Double.parseDouble(salary));
			emp.setCommissionPct(Double.parseDouble(commissionPct));
			emp.setManagerId(Integer.parseInt(managerId));
			emp.setDepartmentId(Integer.parseInt(departmentId));

			dao.insertEmp(emp);
			response.sendRedirect("EmpList.do"); //서버에 값을 주고 나서 새로운 요청을 생성
		}
		else if ("/EmpUpdate.do".equals(cmd)) {
			
			//입력 처리
			String employeeId = request.getParameter("employeeId");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date hireDate = null;
			try {
				
				hireDate = new Date(sdf.parse(request.getParameter("hireDate")).getTime());
			} catch (ParseException e) {
				System.out.println("날짜 형식 오류");
				e.printStackTrace();
			}
			String jobId = request.getParameter("jobId");
			String salary = request.getParameter("salary");
			String commissionPct = request.getParameter("commissionPct");
			String managerId = request.getParameter("managerId");
			String departmentId = request.getParameter("departmentId");
			
			EmpVo emp = new EmpVo();
			emp.setEmployeeId(Integer.parseInt(employeeId));
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
		
			//emp.setHireDate(Date.valueOf(hireDate));
			emp.setHireDate(hireDate);
			emp.setJobId(jobId);
			emp.setSalary(Double.parseDouble(salary));
			emp.setCommissionPct(Double.parseDouble(commissionPct));
			emp.setManagerId(Integer.parseInt(managerId));
			emp.setDepartmentId(Integer.parseInt(departmentId));
			System.out.println(emp);
			dao.updateEmp(emp);
			response.sendRedirect("EmpDetails.do?empid="+employeeId); //서버에 값을 주고 나서 새로운 요청을 생성
		}
		else if("/EmpDelete.do".equals(cmd)) {
			String empidStr = request.getParameter("empid");
			int empid = Integer.parseInt(empidStr);
			String email =  request.getParameter("email");
			dao.deleteEmp(empid, email);
			response.sendRedirect("EmpList.do");
			
		}
		
	}

}


//public void init(ServletConfig config) throws ServletException {
//System.out.println("EmpServlet init() 메소드 실행");
//// xml에 있는 파라미터를 가져올 때 사용
//String email = config.getInitParameter("email");
//System.out.println(email);
//}

//System.out.println("doGet 메소드 실행");
//String cmd = request.getParameter("cmd");
//String view = "/";
//if("empcount".equals(cmd)) {
//	System.out.println("사원의 수를 조회하는 요청입니다.");
//	String deptStr = request.getParameter("deptid");
//	if(deptStr==null) {
//		int empcount = dao.getEmpCount();
//		request.setAttribute("empcount", empcount);
//	}else {
//		int deptid = Integer.parseInt(deptStr);
//		request.setAttribute("empcount", dao.getEmpCount(deptid));
//	}
//	view = "/emp/empcount.jsp";
//}else if("getdept".equals(cmd)) {
//	System.out.println("사원의 부서이름을 조회하는 요청입니다.");
//	String empidStr = request.getParameter("empid");
//	if(empidStr!=null) {
//		int empid = Integer.parseInt(empidStr);
//		request.setAttribute("getdept", dao.getDepartmentNameByEmployeeId(empid));
//	}
//	view = "/emp/getdept.jsp";
//}else if("avgsal".equals(cmd)) {
//	System.out.println("부서의 평균 급여를 조회하는 요청입니다.");
//	String deptidStr = request.getParameter("deptid");
//	if(deptidStr!=null) {
//		int deptid = Integer.parseInt(deptidStr);
//		request.setAttribute("getdept", dao.getAverageSalaryByDepartment(deptid));
//	}
//	view = "/emp/avgsal.jsp";
//}else if("empsal".equals(cmd)) {
//	System.out.println("사원의 급여를 조회하는 요청입니다.");
//	String empidStr = request.getParameter("empid");
//	if(empidStr!=null) {
//		int empid = Integer.parseInt(empidStr);
//		request.setAttribute("getdept", dao.getSalaryByEmployeeId(empid));
//	}
//	view = "/emp/empsal.jsp";
//}else if("EmpDetails.do".equals(cmd)) {
//	System.out.println("사원의 정보를 조회하는 요청입니다");
//	String empidStr = request.getParameter("empid");
//	if(empidStr != null) {
//		int empid = Integer.parseInt(empidStr);
//		request.setAttribute("empvo", dao.getEmpDetail(empid));
//	}
//	view ="/emp/empdetail.jsp";
//}
//RequestDispatcher disp = request.getRequestDispatcher(view);
//disp.forward(request, response);