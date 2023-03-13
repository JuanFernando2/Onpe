package svl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class svlParticipacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public svlParticipacion() {
        super();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		dao.OnpeDAO daoOnpe = new dao.OnpeDAO();
		
		// "?id=nacional,AMAZINAS,BAGUA,BAGUA"
		
		String id = request.getParameter("id");
		String sDPD = null, sAmbito = null;
		Object data = null, totales = null;
		
		if ( id != null ) {
			String aID[]= id.split(",");
			if ( aID.length == 1) sDPD = aID[0].equals("nacional") ? "DEPARTAMENTO" : "CONTINENTE";
			if ( aID.length == 2) sDPD = aID[0].equals("nacional") ? "PROVINCIA" : "PAIS";
			if ( aID.length == 3) sDPD = aID[0].equals("nacional") ? "DISTRITO" : "CIUDAD";
			//session.setAttribute("DPD", sDPD);
			
			if ( aID.length == 1 ) data = daoOnpe.getVotos( id.equals("nacional") ? 1 : 26, id.equals("nacional") ? 25 : 30 );
			else if ( aID.length == 2 ) data = daoOnpe.getVotosDepartamento(  aID[1] );
			else if ( aID.length == 3 ) data = daoOnpe.getVotosProvincia(  aID[2] );
			
			sAmbito = "Ámbito : " + aID[0];
			if ( aID.length > 1 ) sAmbito += "<br/>" + ( aID[0].equals("nacional") ? "departamento" : "continente" ) + " : " + aID[1];
			else if ( aID.length > 2 ) sAmbito += "<br/>" + ( aID[0].equals("nacional") ? "departamento" : "continente" ) + " : " + aID[1];

		}
		
		session.setAttribute("id", id);
		session.setAttribute("DPD", sDPD);
		session.setAttribute("ambito", sAmbito);
		session.setAttribute("data", data);
		session.setAttribute("totales", totales);
		
		response.sendRedirect("participacion.jsp");
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
