package data;

public class AtletaEnDisciplina {
	
	private String idDisc;
	private String idDepo;
	
	public AtletaEnDisciplina(String idDepo, String idDisc) {
		this.idDisc = idDisc;
		this.idDepo = idDepo;
	}
	
	public String getidDisc() {
		return idDisc;
	}
	
	public String getidDepo() {
		return idDepo;
	}
	
	public void setidDepo(String idD) {
		this.idDepo = idD;
	}
	
	public void setidDisc(String idDi) {
		this.idDepo = idDi;
	}

}
