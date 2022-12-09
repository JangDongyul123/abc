package kr.kosa.emp;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;



public class EmpDao {
	//   static {
	//      try {
	//         Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 로드
	//         System.out.println("드라이버 클래스가 로드되었습니다.");
	//      } catch (ClassNotFoundException e) {
	//         System.out.println("드라이버 클래스 로드 실패");
	//         e.printStackTrace();
	//      }
	//   }

	//   private String url; // = "jdbc:oracle:thin:@localhost:1521:xe";
	//   private String id; // = "hr";
	//   private String pw; // = "hr";
	//   
	//   public EmpDao(String url, String id, String pw) { // 첫 번째 연결 설정(생성자(매개변수))
	//      this.url = url;
	//      this.id = id;
	//      this.pw = pw;
	//   }
	//   
	//   public EmpDao(ServletContext application) { // 두 번째 연결 설정(web.xml) //EmpDao dao = new EmpDao(application); 와 연동 (testdb.jsp)
	//      this.url = application.getInitParameter("OracleURL");
	//      this.id = application.getInitParameter("OracleId");
	//      this.pw = application.getInitParameter("OraclePwd");
	//   }

	DataSource dataSource;

	public EmpDao() { // 세 번째 연결 설정 + 커넥션 풀 (톰캣 서버의 context.xml와 연결된 server.xml에 있는 커넥션 풀 코드 이용)
		try {
			Context initCtx = new InitialContext();
			dataSource = (DataSource)initCtx.lookup("java:comp/env/dbcp_myoracle"); // lookup : 찾다
			// Context ctx = (Context)initCtx.lookup("java:com/env");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public int getEmpCount() {
		int count = 0;
		// Connection 생성
		Connection con = null;
		try {
			//         con = DriverManager.getConnection(url, id, pw); // 커넥션 생성 // DriverManager : 드라이브 관리자 클래스
			con = dataSource.getConnection(); // 세 번째 커넥션 풀 방법 이용
			System.out.println(con);
			String sql = "select count(*) from employees";
			PreparedStatement stmt = con.prepareStatement(sql); // Statement 생성
			ResultSet rs = stmt.executeQuery(); // SQL 쿼리 전송
			if(rs.next()) { // 결과집합소비     다음에 데이터가 있는지 확인
				count = rs.getInt(1);
			}
			System.out.println("사원의 수 : " + count);
		} catch(Exception e) {
			e.printStackTrace();
		} finally { // Connection 닫기
			if(con!=null) try {con.close();} catch(Exception e) {}
		}

		return count;
	}

	public int getEmpCount(int deptno) {
		int count = 0;
		// Connection 생성
		Connection con = null;
		try {
			//         con = DriverManager.getConnection(url, id, pw); // 커넥션 생성 // DriverManager : 드라이브 관리자 클래스
			con = dataSource.getConnection();
			System.out.println(con);
			String sql = "select count(*) from employees where department_id=?";
			PreparedStatement stmt = con.prepareStatement(sql); // Statement 생성
			stmt.setInt(1, deptno);
			ResultSet rs = stmt.executeQuery(); // SQL 쿼리 전송
			if(rs.next()) { // 결과집합소비     다음에 데이터가 있는지 확인
				count = rs.getInt(1);
			}
			System.out.println("사원의 수 : " + count);
		} catch(Exception e) {
			e.printStackTrace();
		} finally { // Connection 닫기
			if(con!=null) try {con.close();} catch(Exception e) {}
		}

		return count;
	}

	public double getAverageSalaryByDepartment(int deptno) {
		// Connection 생성
		Connection con = null;
		double result = 0;
		try {
			con = dataSource.getConnection();
			System.out.println(con);
			String sql = "select round(avg(salary), 2) from employees where department_id=?";
			PreparedStatement stmt = con.prepareStatement(sql); // Statement 생성
			stmt.setInt(1, deptno); // 1번째
			ResultSet rs = stmt.executeQuery(); // SQL 쿼리 전송
			if(rs.next()) { // 결과집합소비     다음에 데이터가 있는지 확인
				result = rs.getDouble(1);
			}
			System.out.println("부서별 평균 : " + result);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally { // Connection 닫기
			if(con!=null) try {con.close();} catch(Exception e) {}
		}

		return result;
	}

	public int getSalaryByEmployeeId(int empid) {
		Connection con = null;
		int salary = 0; // 사원의 급여를 지정할 변수
		try {
			con = dataSource.getConnection();
			String sql = "select salary from employees where employee_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, empid); // 1 : 1번째
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				salary = rs.getInt(1);
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if(con!=null) try { con.close(); } catch(Exception e) { }
		}
		return salary;
	}

	public String getDepartmentNameByEmployeeId(int empid) {
		Connection con = null;
		String departmentName = null;
		try {
			con = dataSource.getConnection();
			String sql = "select department_name from employees "
					+ "join departments on employees.department_id=departments.department_id "
					+ "where employee_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, empid); // 1 : 1번째
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				departmentName = rs.getString("department_name");
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if(con!=null) try { con.close(); } catch(Exception e) { }
		}
		return departmentName;
	}

	public EmpVo getEmpDetails(int empid) {
		EmpVo empvo = new EmpVo();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "select employee_id, first_name, last_name, email, phone_number, hire_date, " + 
					"job_id, salary, commission_pct, manager_id, department_id from employees where employee_id = ? ";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,empid);
			ResultSet rs = stmt.executeQuery(); 		   
			if(rs.next()) {
				empvo.setEmployeeId(rs.getInt("employee_id"));
				empvo.setFirstName(rs.getString("first_name"));
				empvo.setLastName(rs.getString("last_name"));
				empvo.setEmail(rs.getString("email"));
				empvo.setPhoneNumber(rs.getString("phone_number"));
				empvo.setHireDate(rs.getDate("hire_date"));
				empvo.setJobId(rs.getString("job_id"));
				empvo.setSalary(rs.getInt("salary"));
				empvo.setCommissionPct(rs.getDouble("commission_pct"));
				empvo.setManagerId(rs.getInt("manager_id"));
				empvo.setDepartmentId(rs.getInt("department_id"));
			}else {
				empvo = null;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if(con!=null) try { con.close(); } catch(Exception e) { }
		}

		return empvo;
	}

	public List<EmpVo> getAllEmps (){
		List<EmpVo> empList = new ArrayList<>();
		Connection con = null;

		try {
			con = dataSource.getConnection();
			String sql = "select * from employees";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVo emp = new EmpVo();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getInt("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				
				empList.add(emp);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (con !=null) try {con.close();}catch (Exception e2) {}
		}

		return empList;
	}

	public void insertEmp(EmpVo emp) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		String	sql="insert into employees (employee_id, first_name, last_name, "
					+ " email, phone_number, hire_date, job_id,  salary, commission_pct, "
					+ " manager_id, department_id) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, emp.getEmployeeId());
		stmt.setString(2, emp.getFirstName());
		stmt.setString(3, emp.getLastName());
		stmt.setString(4, emp.getEmail());
		stmt.setString(5, emp.getPhoneNumber());
		stmt.setDate(6, emp.getHireDate());
		stmt.setString(7, emp.getJobId());
		stmt.setDouble(8, emp.getSalary());
		stmt.setDouble(9, emp.getCommissionPct());
		stmt.setInt(10, emp.getManagerId());
		stmt.setInt(11, emp.getDepartmentId());
		stmt.executeUpdate();
		System.out.println("데이터가 입력되었습니다.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if(con != null)try {con.close();}catch(Exception e){}
		}
	}
	
	public List<Map<String, Object>> getJobIdList(){
		List<Map<String, Object>> jobIdList = new ArrayList<Map<String, Object>>();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "select job_id, job_title from jobs order by job_title";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String jobId = rs.getString("job_id");
				String jobTitle = rs.getString("job_title");
				Map<String, Object> job = new HashMap<String, Object>();
				job.put("jobId", jobId);
				job.put("jobTitle", jobTitle);
				jobIdList.add(job);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (con !=null) try {con.close();}catch (Exception e2) {}
		}

		return jobIdList;
	}
	
	public List<Map<String, Object>> getDeptIdList(){
		List<Map<String, Object>> deptIdList = new ArrayList<Map<String, Object>>();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "select department_id, department_name from departments order by department_name";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String deptId = rs.getString("department_id");
				String deptName = rs.getString("department_name");
				Map<String, Object> dept = new HashMap<String, Object>();
				dept.put("deptId", deptId);
				dept.put("deptName", deptName);
				deptIdList.add(dept);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (con !=null) try {con.close();}catch (Exception e2) {}
		}

		return deptIdList;
	}
	
	public List<Map<String, Object>> getEmpIdList(){
		List<Map<String, Object>> empIdList = new ArrayList<Map<String, Object>>();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "select employee_id, first_name || ' ' || last_name as name from employees order by name";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String empId = rs.getString("employee_id");
				String name= rs.getString("name");
				Map<String, Object> emp= new HashMap<String, Object>();
				emp.put("empId", empId);
				emp.put("name", name);
				empIdList.add(emp);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (con !=null) try {con.close();}catch (Exception e2) {}
		}

		return empIdList;
	}
	
	public int getMaxEmpId() {
		int maxEmpId = 0;
		Connection con = null;
		try {
			con = dataSource.getConnection();
			String sql = "select MAX(employee_id) as maxEmpId from employees";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				maxEmpId= rs.getInt("maxEmpId");



			}
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}finally {
			if (con !=null) try {con.close();}catch (Exception e2) {}
		}
		return maxEmpId+1;
	}
	
	public void updateEmp(EmpVo emp) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		String	sql="update employees set  first_name=?, last_name=?, "
					+ " email=?, phone_number=?, hire_date=?, job_id=?,  salary=?, commission_pct=?, "
					+ " manager_id=?, department_id=? where employee_id=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, emp.getFirstName());
		stmt.setString(2, emp.getLastName());
		stmt.setString(3, emp.getEmail());
		stmt.setString(4, emp.getPhoneNumber());
		stmt.setDate(5, emp.getHireDate());
		stmt.setString(6, emp.getJobId());
		stmt.setDouble(7, emp.getSalary());
		stmt.setDouble(8, emp.getCommissionPct());
		stmt.setInt(9, emp.getManagerId());
		stmt.setInt(10, emp.getDepartmentId());
		stmt.setInt(11, emp.getEmployeeId());
		stmt.executeUpdate();
		System.out.println("데이터가 업데이트 되었습니다.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			if(con != null)try {con.close();}catch(Exception e){}
		}
	}
	
	public void deleteEmp(int empid, String email) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			String sql0 = "delete job_history where employee_id=?";
			PreparedStatement stmt0 = con.prepareStatement(sql0);
			stmt0.setInt(1, empid);
			stmt0.executeUpdate();
			
			String sql = "delete employees where employee_id=? and email=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, empid);
			stmt.setString(2, email);
			stmt.executeUpdate();
			
			con.commit();
			System.out.println("사원 정보가 삭제되었습니다.");
		}catch(SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally {
			try {
				con.setAutoCommit(true);
			}catch(SQLException e1){
				e1.printStackTrace();
			}
			if(con != null)try {con.close();}catch(Exception e){}
		}
	}
	
	
}