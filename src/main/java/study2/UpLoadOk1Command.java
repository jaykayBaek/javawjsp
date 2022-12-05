package study2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UpLoadOk1Command implements StudyInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String fName = request.getParameter("fName") == null ? "" : request.getParameter("fName");
//		System.out.println("pdstest에서 넘어온 파일명? " + fName);
		
		// COS 라이브러리에서 제공해주는 객체... MultipartRequest() /DefaultFileRenamePolicy() - 중복방지 클래스
		// MultipartRequest(저장소명(request), "서버에 저장될 파일의 경로", "서버에 저장될 파일의 최대 용량", "코드변환방식(인코딩방식)", 기타옵션(중복방지를 위한 클래스))

		//		ServletRequest application= request.getServletContext();
//		String realPath = application.getRealPath(null);
		
//		request.getRealPath(null);
		
		//서버에 저장될 파일의 경로
		String realPath = request.getServletContext().getRealPath("/data/pdstest");
		System.out.println("realPath : " + realPath);
		int maxSize = 1024 * 1024 * 10; //서버에 저장할 최대 용량? 10MB로 하겠다.(1회 업로드 할 때)
		String encoding = "UTF-8";
		
		//파일 업로드 처리 끝...
		MultipartRequest multipartRequest = new MultipartRequest(request, realPath, maxSize, encoding, new DefaultFileRenamePolicy());
		
		//업로드된 file의 정보를 추출해보자...
		String fName = request.getParameter("fName"); //이건 url 전송 방식에서 받는 방법임.
		String originalFileName = multipartRequest.getOriginalFileName("fName"); //업로드시킬 때 파일명
		//서버에 저장되는 실제 파일명을 구해보자. 업로드한 파일과 저장된 파일의 이름이 같으면 나중에 들어오는 파일이 이름이 추가되기 때문
		String fileSystemName = multipartRequest.getFilesystemName("fName"); //서버에 저장된 파일명
		
		System.out.println("원본 파일명(url 전송 방식)?"+fName);
		System.out.println("원본 파일명?"+originalFileName);
		System.out.println("파일이 저장될 서버의 실제 경로명?"+realPath);
		System.out.println("서버에 저장되는 실제 파일명 : " + fileSystemName);
		
		if(fileSystemName == null) {
			request.setAttribute("msg", "upLoad1No");
		}
		else {
			request.setAttribute("msg", "upLoad1Ok");
		}
		
		request.setAttribute("url", request.getContextPath()+"/upLoad1.st");
	}

}