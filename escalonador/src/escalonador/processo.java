package escalonador;

public class processo {

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getTempoChegada() {
		return tempoChegada;
	}
	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}
	public int getTempoExec() {
		return tempoExec;
	}
	public void setTempoExec(int tempoExec) {
		this.tempoExec = tempoExec;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public processo(int pid, int tempoChegada, int tempoExec, int deadline, int prioridade) {
		super();
		this.pid = pid;
		this.tempoChegada = tempoChegada;
		this.tempoExec = tempoExec;
		this.deadline = deadline;
		this.prioridade = prioridade;
	}
	public int pid;
	public int tempoChegada;
	public int tempoExec;
	public int deadline;
	public int prioridade;
	public int numeroPaginas;
}
