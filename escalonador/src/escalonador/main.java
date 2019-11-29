package escalonador;
testzinho;
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
	

public class main extends JFrame {
	
	private JPanel contentPane;

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
		spinner.setBounds(20, 47, 30, 20);
		contentPane.add(spinner);
		
		JTextPane txtpnTempochegada = new JTextPane();
		txtpnTempochegada.setBackground(SystemColor.menu);
		txtpnTempochegada.setText("Tempo de Chegada");
		txtpnTempochegada.setBounds(60, 47, 130, 20);
		contentPane.add(txtpnTempochegada);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(20, 78, 30, 20);
		contentPane.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
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
			}
		});
		btnCriarProcesso.setBounds(34, 171, 124, 23);
		contentPane.add(btnCriarProcesso);
	}
}
