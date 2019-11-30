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






public class main extends JFrame {
	
	// Comparador para fila de prioridade, de acordo com o algoritmo de escalonação

	Comparator<processo> comparador = new Comparator<processo>() {
        @Override
        public int compare(processo a, processo b) {
        	
        	if(algoritmoEscalonamento == "EDF") {
        	
        	
            return a.getDeadline() - b.getDeadline();
            
        	}
        	
        	
			return 0;
            
        }
    };
	
    public processo processoAtual;
    public boolean executando = false;
	public PriorityQueue<processo> filaprocessos = new PriorityQueue<processo>(comparador);
	public int contador = 0;
	public int um_segundo = 1000;
	public Timer timer = new Timer(um_segundo, null);
	public static String algoritmoEscalonamento;
	public static String algoritmoPaginacao;
	public processo[] processos = new processo[15];
	public int nProcessos = 0;
	public boolean maxProcessosAtingido = false;
	private JPanel contentPane;
	private JTable table_1;
	public int tempoExecAtual;
	
	

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
		
	
		
		timer = new Timer(um_segundo, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            	contador++;
            	
            	// Se chegou o tempo de execução de algum processo, colocá-lo na fila
            	
            	for(int i = 0; i<nProcessos; i++) {  		
            		if(processos[i].getTempoChegada() == contador) {
            			filaprocessos.add(processos[i]);
            		}	
            	}
            	
            	// Executar processo que está em sua vez
            	
            	if(executando == false) {
            		
            		if(!filaprocessos.isEmpty()) {
            			processoAtual = filaprocessos.poll();
            			executando = true;
            		}
            	}
            	
            	if(executando == true) {
            		
            		tempoExecAtual = processoAtual.getTempoExec();
            		processoAtual.setTempoExec(tempoExecAtual -1);
            		if(processoAtual.getTempoExec() == 0) {
            			executando = false;
            		}
            		
            		table_1.setValueAt("X", processoAtual.getPid(), contador);
            		
            	}
               
            }
        });

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 710);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnNovoProcesso = new JTextPane();
		txtpnNovoProcesso.setEditable(false);
		txtpnNovoProcesso.setBackground(SystemColor.menu);
		txtpnNovoProcesso.setText("Novo processo");
		txtpnNovoProcesso.setBounds(46, 16, 124, 20);
		contentPane.add(txtpnNovoProcesso);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setToolTipText("");
		spinner.setBounds(20, 47, 30, 20);
		contentPane.add(spinner);
		
		JTextPane txtpnTempochegada = new JTextPane();
		txtpnTempochegada.setBackground(SystemColor.menu);
		txtpnTempochegada.setText("Tempo de Chegada");
		txtpnTempochegada.setBounds(60, 47, 130, 20);
		contentPane.add(txtpnTempochegada);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_1.setBounds(20, 78, 30, 20);
		contentPane.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner_2.setBounds(20, 109, 30, 20);
		contentPane.add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(20, 140, 30, 20);
		contentPane.add(spinner_3);
		
		JTextPane txtpnTempoExecuo = new JTextPane();
		txtpnTempoExecuo.setBackground(SystemColor.menu);
		txtpnTempoExecuo.setText("Tempo de Execu\u00E7\u00E3o");
		txtpnTempoExecuo.setBounds(60, 78, 130, 20);
		contentPane.add(txtpnTempoExecuo);
		
		JTextPane txtpnTempo = new JTextPane();
		txtpnTempo.setBackground(SystemColor.menu);
		txtpnTempo.setText("Deadline");
		txtpnTempo.setBounds(60, 109, 84, 20);
		contentPane.add(txtpnTempo);
		
		JTextPane txtpnPrioridade = new JTextPane();
		txtpnPrioridade.setBackground(SystemColor.menu);
		txtpnPrioridade.setText("Prioridade");
		txtpnPrioridade.setBounds(60, 140, 84, 20);
		contentPane.add(txtpnPrioridade);
		
		JButton btnCriarProcesso = new JButton("Criar processo");
		btnCriarProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int aux = (Integer) spinner.getValue();
				int aux2 = (Integer) spinner_1.getValue();
				int aux3 = (Integer) spinner_2.getValue();
				int aux4 = (Integer) spinner_3.getValue();
				
				if(maxProcessosAtingido == false) {
					
					// Se atingir o máximo de processos, impede a criação de um novo
					
					if(nProcessos == 15) {
						nProcessos--;
						maxProcessosAtingido = true;
						JOptionPane.showMessageDialog(null, "Número máximo de processos atingido (15)");
					}
					
					// Senão, cria processo
					
					else {
						processos[nProcessos] = new processo(nProcessos+1, aux, aux2, aux3, aux4);
						nProcessos++;
						table_1.setValueAt(processos[nProcessos-1].getPid(),nProcessos,0);
						
						// Coloca o valor PID na tabela

                        table_1.setValueAt(nProcessos, nProcessos, 0);
					}
					
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Número máximo de processos atingido (15)");
				}
			}
		});
		btnCriarProcesso.setBounds(30, 171, 124, 23);
		contentPane.add(btnCriarProcesso);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerListModel(new String[] {"FIFO", "SJF", "RR", "EDF"}));
		spinner_4.setBounds(210, 47, 44, 20);
		contentPane.add(spinner_4);
		
		JLabel lblAlgoritmoDeEscalonamento = new JLabel("Algoritmo de escalonamento");
		lblAlgoritmoDeEscalonamento.setBounds(264, 50, 154, 14);
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
		
		JLabel lblNewLabel = new JLabel("Sobrecarga do sistema");
		lblNewLabel.setBounds(264, 143, 139, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Iniciar simula\u00E7\u00E3o");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				timer.start();
				algoritmoEscalonamento = (String)spinner_4.getValue();
				algoritmoPaginacao = (String)spinner_5.getValue();

					
				
				
			}
		});
		btnNewButton.setBounds(224, 171, 154, 23);
		contentPane.add(btnNewButton);
		
		table_1 = new JTable();
		table_1.setShowGrid(false);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"PID", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
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
		table_1.setBounds(10, 405, 980, 256);
		contentPane.add(table_1);
		
	
	}
}
