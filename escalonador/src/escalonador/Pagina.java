package escalonador;

public class Pagina {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Pagina(String id) {
		super();
		this.id = id;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	public String id;
	public int tempo;
	
	
}
