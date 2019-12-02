package escalonador;

import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.util.*;
import java.awt.Font;






public class main extends JFrame {
	
	// Comparador para fila de prioridade, de acordo com o algoritmo de escalonação

	Comparator<processo> comparador = new Comparator<processo>() {
        @Override
        public int compare(processo a, processo b) {
        	
        	int aux;
        	
        	if(algoritmoEscalonamento == "EDF") {
        	
        	
        		aux = a.getDeadline() - b.getDeadline();
            
        	}
        	else if(algoritmoEscalonamento == "SJF") {
        		
        		aux =  a.getTempoExec() - b.getTempoExec();
        		
        	}
        	
        	else {
        		aux = a.tempoChegada - b.tempoChegada;
        	}
        	
        	
        	if(aux == 0) {
        		aux = b.getPrioridade() - a.getPrioridade();
        	}
        	
			return aux;
            
        }
    };
    
    Comparator<Pagina> comparadorP = new Comparator<Pagina>() {
        @Override
        public int compare(Pagina a, Pagina b) {
        	
        	
        	
        	
            return a.getTempo() - b.getTempo();
            
        	
        	

            
        }
    };
    
    
    
    public int countFim = 0;
    public double turnaroundMedioValor = 0;
    public int nProcessos = 0;
    public double somaturnarounds = 0;
    public int memoriaUsada;
	public Vector<Pagina> paginasFault = new Vector<Pagina>();
    public PriorityQueue<Pagina> paginas = new PriorityQueue<Pagina>(comparadorP);
    public boolean pageFault = false;
    public int lin_t1 = 0, lin_t2 = 0, col_t1 = 0, col_t2 = 0;
    public int cont_t1 = 1, cont_t2 = 51;
    public int quantumAtual=3;
    public int sobrecargaAtual=3;
    public int quantum=3;
    public int sobrecarga=3;
    public processo processoAtual;
    public boolean executando = false;
	public PriorityQueue<processo> filaprocessos = new PriorityQueue<processo>(comparador);
	public int contador = 0;
	public int pintador = 0;
	public int umSegundo = 250;
	public Timer timer = new Timer(umSegundo, null);
	public static String algoritmoEscalonamento;
	public static String algoritmoPaginacao;
	public processo[] processos = new processo[15];
	public boolean maxProcessosAtingido = false;
	private JPanel contentPane;
	private JTable table_1;
	public int tempoExecAtual;
	private JTable table;
	private JTable table_2;
	public int contadorP;

	
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}		
				
			}
		});
	}

	public main() {
		setTitle("Escalonador - SO");
		
	
		
		timer = new Timer(umSegundo, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	
            	
                
            	
            	
            	if(pageFault == false) {
            		
            		
            		
	            	contador++;
	            	
	            	pintador++;
	            	
	            	if(pintador > 40) {	
                		pintador = 40;
                		
                		for(int i=1; i<16; i++) {	
                			for(int j=1; j<40; j++) {		
        	            		table_1.setValueAt(table_1.getValueAt(i, j+1), i, j);
                			}	
                		}
                		
                	}
	            	
	            	
	            	// Se chegou o tempo de execução de algum processo, colocá-lo na fila
	            	
	            	for(int i = 0; i<nProcessos; i++) {  		
	            		if(processos[i].getTempoChegada() == contador) {
	            			filaprocessos.add(processos[i]);
	            		}	
	            	}
	            	
	            	
	            	
	            	if(executando == false) {
	            		
	            		if(quantumAtual == 0 && sobrecargaAtual > 0) {
	            			sobrecargaAtual--;
	            			for(int i=1; i<16; i++) {
		            			if(processoAtual.getPid() != i) {
		            				table_1.setValueAt(" ", i, pintador);
		            			}
		            			else {
		            				table_1.setValueAt("S", i, pintador);
		            			}
		            		}
	            			
	            			
	            		}
	            		else if(sobrecargaAtual == 0) {
	            			sobrecargaAtual = sobrecarga;
	            			quantumAtual = quantum;
	            			filaprocessos.add(processoAtual);
	            		}
	            		
	            		if(!filaprocessos.isEmpty() && quantumAtual > 0) {
	            			
	            			processoAtual = filaprocessos.poll();
	            			
	            			executando = true;
	            			
	            			// Pagina o processo que vai ser executado
	            			
	            			Pagina[] paginasAtual = processoAtual.getPaginas();
	            			
	            			for(int i=0; i<processoAtual.getNumeroPaginas(); i++) {
	            				
	            				if(algoritmoPaginacao == "MRU") {
		            				
		            				Pagina paginaAux = paginasAtual[i];
		            				
		            				if(paginas.remove(paginaAux)) {
		            					paginaAux.setTempo(contador);
		            					paginas.add(paginaAux);
		            				}
		            				
		            				else {
		            					
		            					pageFault = true;
		            					
		            					executando = false;
		            					
		            					contadorP = 0;
		            					
		            					
		            					paginasFault.add(paginaAux);
		            					
		            				}
		            				
		            			
	            				}
	            				else {
	            					
	            					
		            				Pagina paginaAux = paginasAtual[i];
		            				
		            				
	            					if(paginas.contains(paginaAux) == false) {
	            						
	            						pageFault = true;
	            						executando = false;
	            						
	            						paginasFault.add(paginaAux);
	            						contadorP = 0;
	            						
	            					}
	            					
	            					
	            				}
	            				
	            			}
	            			
	            		}
	            		
	            	}
	            	
	            	// Executar processo que está em sua vez
	            	
	            	if(executando == true) {
	            		
	            		tempoExecAtual = processoAtual.getTempoExec();
	            		processoAtual.setTempoExec(tempoExecAtual -1);
	            		
	            		processoAtual.setDeadline(processoAtual.getDeadline()-1);
	            		
	            		if(algoritmoEscalonamento == "RR" || algoritmoEscalonamento == "EDF") {
	            			quantumAtual--;
	            		}
	            		
	            		if(processoAtual.getTempoExec() == 0) {
	            			processoAtual.setTurnaround(contador+1 - processoAtual.getTempoChegada());
	            			somaturnarounds += processoAtual.getTurnaround();
	            			countFim++;
	            			executando = false;
	            			quantumAtual = quantum;
	            			sobrecargaAtual = sobrecarga;
	            			
	            			
	            			if(countFim == nProcessos) {
	            				
	            				turnaroundMedioValor = somaturnarounds / nProcessos;
	            				
	            				for(int i=1; i<16; i++) {
			            			if(processoAtual.getPid() != i) {
			            				table_1.setValueAt(" ", i, pintador);
			            			}
			            			else {
			            				table_1.setValueAt("X", i, pintador);
			            			}
			            		}
	            				JOptionPane.showMessageDialog(null, "Todos processos finalizaram!" + " Turnaround m\u00E9dio = " + somaturnarounds + "/" + String.valueOf(nProcessos) + " = " + String.valueOf(turnaroundMedioValor));
	            				
	            				timer.stop();
	            				
	            			}
	            			
	            		}
	            		
	            		else if(quantumAtual == 0 && quantum > 0) {
	            			
	            			executando = false;
	            			processoAtual.setTempoChegada(contador);
	            			
	            			           			
	            		}
	            		
	            		
	            		
	            		for(int i=1; i<16; i++) {
	            			if(processoAtual.getPid() != i) {
	            				table_1.setValueAt(" ", i, pintador);
	            			}
	            			else {
	            				table_1.setValueAt("X", i, pintador);
	            			}
	            		}
	            		
	            		
	            		
	            	}
	               
	            }
            	else {
            		
            		if(!paginasFault.isEmpty()) {
            			
            			Pagina paginaFaultAtual = paginasFault.get(0);
            			paginasFault.remove(0);
            			paginaFaultAtual.setTempo(contador);
            			int i = paginas.size()/10;
            			int j = paginas.size()%10;
            			
            			if(paginas.size() < 50) {
            				
            				
            				paginas.add(paginaFaultAtual);
            				
            				
            				table.setValueAt(paginaFaultAtual.getId(), j, i);
            				
            				
            				
            				
            			}
            			
            			else {
            				
            				Pagina paginaAux = paginas.poll();
            				paginas.add(paginaFaultAtual);
            				
            				for(i=0; i<5; i++) {
            					for(j=0; j<10; j++) {
            						if(table.getValueAt(j, i) == paginaAux.getId()) {
            							table.setValueAt(paginaFaultAtual.getId(), j, i);
            						}
            					}
            				}
            				
            				
            			}
            		
            		contadorP++;
            		}
            		else {
            			
            			executando = true;
            			pageFault = false;
            			if(pintador < 40) {
            				pintador--;
            			}
            		}
            		
            		
            	}
            	
            	
            	
            	
            }
        });

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1334, 805);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnNovoProcesso = new JTextPane();
		txtpnNovoProcesso.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtpnNovoProcesso.setEditable(false);
		txtpnNovoProcesso.setBackground(SystemColor.menu);
		txtpnNovoProcesso.setText("Escalonador de Processos");
		txtpnNovoProcesso.setBounds(79, 11, 299, 25);
		contentPane.add(txtpnNovoProcesso);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setToolTipText("");
		spinner.setBounds(20, 47, 30, 20);
		contentPane.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_1.setBounds(20, 78, 30, 20);
		contentPane.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner_2.setBounds(20, 109, 30, 20);
		contentPane.add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinner_3.setBounds(20, 140, 30, 20);
		contentPane.add(spinner_3);
		
		JSpinner spinner_8 = new JSpinner();
		JLabel lblNewLabel = new JLabel("Sobrecarga do sistema");
		lblNewLabel.setBounds(264, 143, 139, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnCriarProcesso = new JButton("Criar processo");
		btnCriarProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int aux = (Integer) spinner.getValue();
				int aux2 = (Integer) spinner_1.getValue();
				int aux3 = (Integer) spinner_2.getValue();
				int aux4 = (Integer) spinner_3.getValue();
				int aux5 = (Integer) spinner_8.getValue();
				memoriaUsada += aux4;
				
				if(maxProcessosAtingido == false) {
					
					// Se atingir o máximo de processos, impede a criação de um novo
					
					if(nProcessos == 15) {
						nProcessos--;
						maxProcessosAtingido = true;
						JOptionPane.showMessageDialog(null, "Número máximo de processos atingido (15)");
					}
					
					else if(memoriaUsada > 100) {
						
						JOptionPane.showMessageDialog(null, "Memória virtual máxima atingida!");
						
					}
					
					// Senão, cria processo
					
					else {
						processos[nProcessos] = new processo(nProcessos+1, aux, aux2, aux3, aux4, aux5);
						nProcessos++;
				
						
						// Coloca o valor de PID na tabela
						
						table_1.setValueAt(processos[nProcessos-1].getPid(),nProcessos,0);
						
						Pagina[] paginas = new Pagina[aux4];
						
						
						
						for(int i=0; i<aux4; i++) {
							
							
							String auxId = "P"+String.valueOf(processos[nProcessos-1].getPid())+"_"+String.valueOf(i);
							paginas[i] = new Pagina(auxId);
							
						}
					
						
						
						processos[nProcessos-1].setPaginas(paginas);
						
							
						for (int i = 0; i < processos[nProcessos-1].getNumeroPaginas(); i++) {
								
							table_2.setValueAt(paginas[i].getId() , lin_t2, col_t2);
								
								
							if(lin_t1 == 9) {
								lin_t1 = 0;
								col_t1++;
							}
								
							else {
								lin_t1++;
							}
								
							if (lin_t2 == 19) {
								lin_t2 = 0;
								col_t2++;
							}
								
							else {
								lin_t2++;
							}
						}
							
							
							
							
						
						
					
					}
					
					
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Número máximo de processos atingido (15)");
				}
			}
		});
		btnCriarProcesso.setBounds(30, 202, 124, 23);
		contentPane.add(btnCriarProcesso);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerListModel(new String[] {"FIFO", "SJF", "RR", "EDF"}));
		spinner_4.setBounds(210, 47, 44, 20);
		contentPane.add(spinner_4);
		
		JLabel lblAlgoritmoDeEscalonamento = new JLabel("Algoritmo de escalonamento");
		lblAlgoritmoDeEscalonamento.setBounds(264, 50, 188, 14);
		contentPane.add(lblAlgoritmoDeEscalonamento);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerListModel(new String[] {"FIFO", "MRU"}));
		spinner_5.setBounds(210, 78, 44, 20);
		contentPane.add(spinner_5);
		
		JLabel lblAlgoritmoDePaginao = new JLabel("Algoritmo de pagina\u00E7\u00E3o");
		lblAlgoritmoDePaginao.setBounds(264, 81, 139, 14);
		contentPane.add(lblAlgoritmoDePaginao);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_6.setBounds(224, 109, 30, 20);
		contentPane.add(spinner_6);
		
		JLabel lblQuantumDoSistema = new JLabel("Quantum do sistema");
		lblQuantumDoSistema.setBounds(264, 112, 154, 14);
		contentPane.add(lblQuantumDoSistema);
		
		JSpinner spinner_7 = new JSpinner();
		spinner_7.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_7.setBounds(224, 140, 30, 20);
		contentPane.add(spinner_7);
		
		
		
		JButton btnNewButton = new JButton("Iniciar simula\u00E7\u00E3o");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				timer.start();
				algoritmoEscalonamento = (String)spinner_4.getValue();
				algoritmoPaginacao = (String)spinner_5.getValue();
				
				if(algoritmoEscalonamento == "EDF" || algoritmoEscalonamento == "RR") {
					
					quantum = (Integer) spinner_6.getValue();
					sobrecarga = (Integer) spinner_7.getValue();
					quantumAtual = quantum;
					sobrecargaAtual = sobrecarga;
					
				}

					
				
				
			}
		});
		btnNewButton.setBounds(224, 202, 154, 23);
		contentPane.add(btnNewButton);
		
		table_1 = new JTable();
		table_1.setShowGrid(false);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"PID", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		table_1.getColumnModel().getColumn(0).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(6).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(9).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(10).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(11).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(12).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(13).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(14).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(15).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(16).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(17).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(18).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(19).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(20).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(21).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(22).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(23).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(24).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(25).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(26).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(27).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(28).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(29).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(30).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(31).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(32).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(32).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(33).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(34).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(35).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(36).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(37).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(38).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(39).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(40).setPreferredWidth(15);
		table_1.setBounds(10, 489, 1289, 256);
		contentPane.add(table_1);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		table.setBounds(438, 81, 321, 160);
		contentPane.add(table);
		
		table_2 = new JTable();
		table_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		table_2.setBounds(803, 81, 496, 320);
		contentPane.add(table_2);
		
		JTextPane txtpnMemriaPrincipalram = new JTextPane();
		txtpnMemriaPrincipalram.setText("Mem\u00F3ria Principal (RAM)");
		txtpnMemriaPrincipalram.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtpnMemriaPrincipalram.setEditable(false);
		txtpnMemriaPrincipalram.setBackground(SystemColor.menu);
		txtpnMemriaPrincipalram.setBounds(475, 39, 238, 25);
		contentPane.add(txtpnMemriaPrincipalram);
		
		JTextPane txtpnSwap = new JTextPane();
		txtpnSwap.setText("Mem\u00F3ria Secund\u00E1ria");
		txtpnSwap.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtpnSwap.setEditable(false);
		txtpnSwap.setBackground(SystemColor.menu);
		txtpnSwap.setBounds(950, 39, 238, 25);
		contentPane.add(txtpnSwap);
		
		
		spinner_8.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinner_8.setBounds(20, 171, 30, 20);
		contentPane.add(spinner_8);
		
		JLabel lblTempoDeChegada = new JLabel("Tempo de chegada");
		lblTempoDeChegada.setBounds(60, 50, 106, 14);
		contentPane.add(lblTempoDeChegada);
		
		JLabel lblTempoDeExecuo = new JLabel("Tempo de execu\u00E7\u00E3o");
		lblTempoDeExecuo.setBounds(60, 81, 124, 14);
		contentPane.add(lblTempoDeExecuo);
		
		JLabel lblNewLabel_1 = new JLabel("Deadline");
		lblNewLabel_1.setBounds(60, 112, 95, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNmeroDePginas = new JLabel("N\u00FAmero de p\u00E1ginas");
		lblNmeroDePginas.setBounds(60, 143, 106, 14);
		contentPane.add(lblNmeroDePginas);
		
		JLabel lblPrioridade = new JLabel("Prioridade");
		lblPrioridade.setBounds(60, 174, 106, 14);
		contentPane.add(lblPrioridade);
	
	
	}
}
