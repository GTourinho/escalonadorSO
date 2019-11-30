package escalonador;

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


public class main extends JFrame {
	
	public processo[] processos = new processo[15];
	public int nProcessos = 0;
	public boolean maxProcessosAtingido = false;
	
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public main() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 710);
		contentPane = new JPanel();
		contentPane.setToolTipText("Tempo de Chegada");
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
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setToolTipText("");
		spinner.setBounds(20, 47, 30, 20);
		contentPane.add(spinner);
		
		JTextPane txtpnTempochegada = new JTextPane();
		txtpnTempochegada.setBackground(SystemColor.menu);
		txtpnTempochegada.setText("Tempo de Chegada");
		txtpnTempochegada.setBounds(60, 47, 130, 20);
		contentPane.add(txtpnTempochegada);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
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
		spinner_7.setBounds(224, 140, 30, 20);
		contentPane.add(spinner_7);
		
		JLabel lblNewLabel = new JLabel("Sobrecarga do sistema");
		lblNewLabel.setBounds(264, 143, 139, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Iniciar simula\u00E7\u00E3o");
		btnNewButton.setBounds(224, 171, 154, 23);
		contentPane.add(btnNewButton);
		
	
	}
}
