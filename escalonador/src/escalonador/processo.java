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
	public int getNumeroPaginas() {
		return numeroPaginas;
	}
	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	public processo(int pid, int tempoChegada, int tempoExec, int deadline, int numeroPaginas, int prioridade) {
		super();
		this.pid = pid;
		this.tempoChegada = tempoChegada;
		this.tempoExec = tempoExec;
		this.deadline = deadline;
		this.numeroPaginas = numeroPaginas;
		this.prioridade = prioridade;
	}
	public int pid;
	public int tempoChegada;
	public int tempoExec;
	public int deadline;
	public int numeroPaginas;
	public Pagina[] paginas;
	public int turnaround;
	public int prioridade;
	
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public int getTurnaround() {
		return turnaround;
	}
	public void setTurnaround(int turnaround) {
		this.turnaround = turnaround;
	}
	public Pagina[] getPaginas() {
		return paginas;
	}
	public void setPaginas(Pagina[] paginas) {
		this.paginas = paginas;
	}

	

	
	
}
