package escalonador;

public class processo {

	public processo(int tempoChegada, int tempoExec, int deadline, int prioridade) {
		super();
		this.tempoChegada = tempoChegada;
		this.tempoExec = tempoExec;
		this.deadline = deadline;
		this.prioridade = prioridade;
	}
	public int tempoChegada;
	public int tempoExec;
	public int deadline;
	public int prioridade;
	public int numeroPaginas;
}
