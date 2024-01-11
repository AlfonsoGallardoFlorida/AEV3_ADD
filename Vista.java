package es.florida.avaluable;

import static com.mongodb.client.model.Filters.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.bson.conversions.Bson;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Vista {

	Card carta1 = null;
	Card carta2 = null;
	Model model;

	private JFrame frmA;

	// PANELS I TAULES
	private JPanel panelInicial;
	private JPanel panelIniSessio;
	private JPanel panelRegistre;
	private JPanel panel2x4;
	private JPanel panel4x4;
	private JTable tableRecords4;
	private JTable tableRecords8;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	// BOTONS
	private JButton btnJugar;
	private JButton btnIniSes;
	private JButton btnRegistrarse;
	private JButton btnHallOfFame;
	private JButton btnEixir;
	private JButton btnValidar;
	private JButton btnTornar;
	private JButton btnValidarRegistre;
	private JButton btnTornarRegistre;

	// BOTONS CARDS
	private JToggleButton btnCard1;
	private JToggleButton btnCard2;
	private JToggleButton btnCard3;
	private JToggleButton btnCard4;
	private JToggleButton btnCard5;
	private JToggleButton btnCard6;
	private JToggleButton btnCard7;
	private JToggleButton btnCard8;
	private JToggleButton btnCard9;
	private JToggleButton btnCard10;
	private JToggleButton btnCard11;
	private JToggleButton btnCard12;
	private JToggleButton btnCard14;
	private JToggleButton btnCard13;
	private JToggleButton btnCard15;
	private JToggleButton btnCard16;

	// LABELS
	private JLabel lblMemory;
	private JLabel lblNomUsuari;
	private JLabel lblContrasenya;
	private JLabel lblRegistre;
	private JLabel lblNomRegistre;
	private JLabel lblIniSessio;
	private JLabel lblPassRegistre;
	private JTextField txtNomRegistre;
	private JLabel lblUserTxt;
	private JLabel lblUserChange;
	private JLabel lblTemps;
	private JLabel lblTempsChange;

	// TEXTFIELDS
	private JTextField txtNomUsu;
	private JPasswordField ptxtContrasenya;
	private JPasswordField ptxtPassRegistre;

	// LLISTES
	public List<Card> llistaCartes = new ArrayList<Card>();

	// CONSTRUCTOR
	public Vista() {
		this.model = new Model(this);
		initialize();
		ocultar2x4();
		ocultar4x4();
		ocultarPanelIniSessio();
		ocultarPanelRegistre();
		ocultarRecords4();
		ocultarRecords8();
		ocultarTemps();
	}

	private void initialize() {

		// -----------//
		// --CREACIÓ--//
		// -----------//

		// FORMULARI
		frmA = new JFrame();
		frmA.setTitle("Memory_Game");
		frmA.setBounds(100, 100, 1180, 398);
		frmA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmA.getContentPane().setLayout(null);

		panelInicial = new JPanel();
		panelInicial.setBounds(29, 39, 278, 312);
		frmA.getContentPane().add(panelInicial);
		panelInicial.setLayout(null);

		// PANELS I TAULES
		panelRegistre = new JPanel();
		panelRegistre.setBounds(29, 39, 278, 312);
		frmA.getContentPane().add(panelRegistre);
		panelRegistre.setLayout(null);

		panelIniSessio = new JPanel();
		panelIniSessio.setBounds(29, 39, 278, 312);
		frmA.getContentPane().add(panelIniSessio);
		panelIniSessio.setLayout(null);

		panel2x4 = new JPanel();
		panel2x4.setBounds(350, 39, 403, 310);
		frmA.getContentPane().add(panel2x4);
		panel2x4.setBackground(new Color(0, 128, 0));
		panel2x4.setLayout(null);

		panel4x4 = new JPanel();
		panel4x4.setLayout(null);
		panel4x4.setBackground(new Color(0, 128, 0));
		panel4x4.setBounds(734, 39, 419, 312);
		frmA.getContentPane().add(panel4x4);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(350, 39, 384, 311);
		frmA.getContentPane().add(scrollPane);

		tableRecords4 = new JTable();
		scrollPane.setViewportView(tableRecords4);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(755, 39, 398, 313);
		frmA.getContentPane().add(scrollPane_1);

		tableRecords8 = new JTable();
		scrollPane_1.setViewportView(tableRecords8);

		// LABELS
		lblMemory = new JLabel("Memory");
		lblMemory.setBounds(91, 5, 96, 33);
		panelInicial.add(lblMemory);
		lblMemory.setFont(new Font("Tahoma", Font.PLAIN, 27));

		lblRegistre = new JLabel("Registre");
		lblRegistre.setBounds(90, 5, 98, 33);
		lblRegistre.setFont(new Font("Tahoma", Font.PLAIN, 27));
		panelRegistre.add(lblRegistre);

		lblNomRegistre = new JLabel("Nom Usuari:");
		lblNomRegistre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomRegistre.setBounds(10, 57, 110, 25);
		panelRegistre.add(lblNomRegistre);

		lblPassRegistre = new JLabel("Contrasenya:");
		lblPassRegistre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassRegistre.setBounds(10, 126, 124, 25);
		panelRegistre.add(lblPassRegistre);

		lblIniSessio = new JLabel("Iniciar Sessió");
		lblIniSessio.setBounds(50, 0, 158, 33);
		panelIniSessio.add(lblIniSessio);
		lblIniSessio.setFont(new Font("Tahoma", Font.PLAIN, 27));

		lblNomUsuari = new JLabel("Nom Usuari:");
		lblNomUsuari.setBounds(10, 57, 110, 25);
		panelIniSessio.add(lblNomUsuari);
		lblNomUsuari.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblContrasenya.setBounds(10, 127, 124, 25);
		panelIniSessio.add(lblContrasenya);

		lblUserTxt = new JLabel("Usuari:");
		lblUserTxt.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblUserTxt.setBounds(29, 2, 83, 26);
		frmA.getContentPane().add(lblUserTxt);

		lblUserChange = new JLabel("");
		lblUserChange.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblUserChange.setBounds(111, -6, 240, 35);
		frmA.getContentPane().add(lblUserChange);

		lblTemps = new JLabel("Temps:");
		lblTemps.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTemps.setBounds(370, -1, 129, 34);
		frmA.getContentPane().add(lblTemps);

		lblTempsChange = new JLabel("");
		lblTempsChange.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTempsChange.setBounds(459, 2, 227, 33);
		frmA.getContentPane().add(lblTempsChange);

		// BOTONS
		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(0, 53, 278, 42);
		panelInicial.add(btnJugar);
		btnJugar.setFont(new Font("Tahoma", Font.PLAIN, 17));

		btnIniSes = new JButton("Iniciar Sessió");
		btnIniSes.setBounds(0, 105, 278, 42);
		panelInicial.add(btnIniSes);
		btnIniSes.setFont(new Font("Tahoma", Font.PLAIN, 17));

		btnValidar = new JButton("Iniciar Sessió");
		btnValidar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnValidar.setBounds(50, 242, 172, 25);
		panelIniSessio.add(btnValidar);

		btnTornar = new JButton("Tornar arrere");
		btnTornar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTornar.setBounds(50, 277, 172, 25);
		panelIniSessio.add(btnTornar);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(0, 157, 278, 42);
		panelInicial.add(btnRegistrarse);
		btnRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 17));

		btnValidarRegistre = new JButton("Registrar");
		btnValidarRegistre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnValidarRegistre.setBounds(51, 240, 172, 25);
		panelRegistre.add(btnValidarRegistre);

		btnTornarRegistre = new JButton("Tornar arrere");
		btnTornarRegistre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTornarRegistre.setBounds(51, 277, 172, 25);
		panelRegistre.add(btnTornarRegistre);

		btnHallOfFame = new JButton("Hall Of Fame");
		btnHallOfFame.setBounds(0, 209, 278, 42);
		panelInicial.add(btnHallOfFame);
		btnHallOfFame.setFont(new Font("Tahoma", Font.PLAIN, 17));

		btnEixir = new JButton("Eixir");
		btnEixir.setBounds(0, 261, 278, 42);
		panelInicial.add(btnEixir);
		btnEixir.setFont(new Font("Tahoma", Font.PLAIN, 17));

		// BOTONS CARDS
		btnCard1 = new JToggleButton("");
		btnCard1.setBounds(20, 39, 85, 108);
		panel2x4.add(btnCard1);

		btnCard2 = new JToggleButton("");
		btnCard2.setBounds(20, 167, 85, 108);
		panel2x4.add(btnCard2);

		btnCard3 = new JToggleButton("");
		btnCard3.setBounds(115, 39, 85, 108);
		panel2x4.add(btnCard3);

		btnCard4 = new JToggleButton("");
		btnCard4.setBounds(115, 167, 85, 108);
		panel2x4.add(btnCard4);

		btnCard5 = new JToggleButton("");
		btnCard5.setBounds(210, 39, 85, 108);
		panel2x4.add(btnCard5);

		btnCard6 = new JToggleButton("");
		btnCard6.setBounds(210, 167, 85, 108);
		panel2x4.add(btnCard6);

		btnCard7 = new JToggleButton("");
		btnCard7.setBounds(305, 39, 85, 108);
		panel2x4.add(btnCard7);

		btnCard8 = new JToggleButton("");
		btnCard8.setBounds(305, 167, 85, 108);
		panel2x4.add(btnCard8);

		btnCard9 = new JToggleButton("");
		btnCard9.setBounds(28, 39, 85, 108);
		panel4x4.add(btnCard9);

		btnCard10 = new JToggleButton("");
		btnCard10.setBounds(28, 167, 85, 108);
		panel4x4.add(btnCard10);

		btnCard11 = new JToggleButton("");
		btnCard11.setBounds(122, 39, 85, 108);
		panel4x4.add(btnCard11);

		btnCard12 = new JToggleButton("");
		btnCard12.setBounds(122, 167, 85, 108);
		panel4x4.add(btnCard12);

		btnCard13 = new JToggleButton("");
		btnCard13.setBounds(218, 39, 85, 108);
		panel4x4.add(btnCard13);

		btnCard14 = new JToggleButton("");
		btnCard14.setBounds(217, 167, 85, 108);
		panel4x4.add(btnCard14);

		btnCard15 = new JToggleButton("");
		btnCard15.setBounds(312, 39, 85, 108);
		panel4x4.add(btnCard15);

		btnCard16 = new JToggleButton("");
		btnCard16.setBounds(312, 167, 85, 108);
		panel4x4.add(btnCard16);

		// TEXTFIELDS
		txtNomRegistre = new JTextField();
		txtNomRegistre.setText("");
		txtNomRegistre.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtNomRegistre.setColumns(10);
		txtNomRegistre.setBounds(10, 92, 158, 25);
		panelRegistre.add(txtNomRegistre);

		ptxtPassRegistre = new JPasswordField();
		ptxtPassRegistre.setText("");
		ptxtPassRegistre.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ptxtPassRegistre.setBounds(10, 161, 158, 25);
		panelRegistre.add(ptxtPassRegistre);

		txtNomUsu = new JTextField();
		txtNomUsu.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtNomUsu.setBounds(10, 92, 158, 25);
		panelIniSessio.add(txtNomUsu);
		txtNomUsu.setColumns(10);

		ptxtContrasenya = new JPasswordField();
		ptxtContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ptxtContrasenya.setBounds(10, 162, 158, 25);
		panelIniSessio.add(ptxtContrasenya);

		this.frmA.setVisible(true);

	};

	// -------------------//
	// --GETTERS-SETTERS--//
	// -------------------//

	// LABELS
	public JLabel getLblUser() {
		return lblUserChange;
	}

	// PANELS I TAULES
	public JPanel getPanelInicial() {
		return panelInicial;
	}

	public JPanel getPanelIniciSessio() {
		return panelIniSessio;
	}

	public JTable getTableRecords4() {
		return tableRecords4;
	}

	public JTable getTableRecords8() {
		return tableRecords8;
	}

	// BOTONS
	public JButton getBtnIniciarSessio() {
		return btnIniSes;
	}

	public JButton getBtnRegistre() {
		return btnRegistrarse;
	}

	public JButton getBtnHOF() {
		return btnHallOfFame;
	}

	public JButton getBtnJugar() {
		return btnJugar;
	}

	public JButton getBtnEixir() {
		return btnEixir;
	}

	public JButton getBtnValidar() {
		return btnValidar;
	}

	public JButton getBtnValidarRegistre() {
		return btnValidarRegistre;
	}

	public JButton getBtnArrere() {
		return btnTornar;
	}

	public JButton getBtnArrereRegistre() {
		return btnTornarRegistre;
	}

	// TEXTFIELDS
	public String getTxtUsuIni() {
		return txtNomUsu.getText();
	}

	@SuppressWarnings("deprecation")
	public String getTxPassIni() {
		return ptxtContrasenya.getText();
	}

	public String getTxtUsuReg() {
		return txtNomRegistre.getText();
	}

	@SuppressWarnings("deprecation")
	public String getTxtPassReg() {
		return ptxtPassRegistre.getText();
	}

	// TOGGLEBUTTONS CARDS
	public JToggleButton getCard1() {
		return btnCard1;
	}

	public JToggleButton getCard2() {
		return btnCard2;
	}

	public JToggleButton getCard3() {
		return btnCard3;
	}

	public JToggleButton getCard4() {
		return btnCard4;
	}

	public JToggleButton getCard5() {
		return btnCard5;
	}

	public JToggleButton getCard6() {
		return btnCard6;
	}

	public JToggleButton getCard7() {
		return btnCard7;
	}

	public JToggleButton getCard8() {
		return btnCard8;
	}

	public JToggleButton getCard9() {
		return btnCard9;
	}

	public JToggleButton getCard10() {
		return btnCard10;
	}

	public JToggleButton getCard11() {
		return btnCard11;
	}

	public JToggleButton getCard12() {
		return btnCard12;
	}

	public JToggleButton getCard13() {
		return btnCard13;
	}

	public JToggleButton getCard14() {
		return btnCard14;
	}

	public JToggleButton getCard15() {
		return btnCard15;
	}

	public JToggleButton getCard16() {
		return btnCard16;
	}

	// LLISTES
	public List<Card> getListaCard() {
		return llistaCartes;
	}

	// -------------------//
	// --FUNCIONALITATS--//
	// -----------------//

	// OCULTAR
	@SuppressWarnings("deprecation")
	public void ocultarPanelInicial() {
		panelInicial.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarPanelIniSessio() {
		txtNomUsu.setText("");
		ptxtContrasenya.setText("");
		panelIniSessio.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarPanelRegistre() {
		txtNomRegistre.setText("");
		ptxtPassRegistre.setText("");
		panelRegistre.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultar2x4() {
		panel2x4.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultar4x4() {
		panel4x4.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarRecords4() {
		tableRecords4.hide();
		scrollPane.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarRecords8() {
		tableRecords8.hide();
		scrollPane_1.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarLblTemps() {
		lblTemps.hide();
	}

	@SuppressWarnings("deprecation")
	public void ocultarTemps() {
		lblTemps.hide();
	}

	// MOSTRAR
	@SuppressWarnings("deprecation")
	public void mostrarPanelInicial() {
		panelInicial.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrarPanelIniSessio() {
		panelIniSessio.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrarPanelRegistre() {
		panelRegistre.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrarRecords4() {
		tableRecords4.show();
		scrollPane.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrarRecords8() {
		tableRecords8.show();
		scrollPane_1.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrar2x4() {
		panel2x4.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrar4x4() {
		panel4x4.show();
	}

	@SuppressWarnings("deprecation")
	public void mostrarTemps() {
		lblTemps.show();
	}

	// EIXIR
	public void eixir() {
		frmA.dispose();
	}

	/**
	 * Assigna imatges aleatòries a una llista de botons.
	 *
	 * @param botons       La llista de botons als quals s'assignaran les imatges.
	 * @param rutasImatges La llista de rutes d'imatges disponibles.
	 */
	public void asignarImatge(List<JToggleButton> botons, List<String> rutasImatges) {
		List<String> rutasDuplicadas = new ArrayList<>(rutasImatges);
		Collections.shuffle(rutasDuplicadas);

		for (int i = 0; i < botons.size(); i++) {
			String rutaImatge = rutasDuplicadas.get(i);

			JToggleButton boton = botons.get(i);
			boton.setIcon(new ImageIcon(rutaImatge));

			boton.setName(rutaImatge);

			Card card = new Card(this, boton, new ImageIcon(rutaImatge));
			card.identificador = rutaImatge;
			llistaCartes.add(card);
		}
	}

	public void compararCartes() {
		if (carta1 != null && carta2 != null && carta1.getIdentificador().equals(carta2.getIdentificador())) {
			carta1.deshabilitar();
			carta2.deshabilitar();
			carta1 = null;
			carta2 = null;
		} else {
			if (carta1 != null) {
				carta1.girarCarta();
				carta1 = null;
			}
			if (carta2 != null) {
				carta2.girarCarta();
				carta2 = null;
			}
		}
		Card.numGirades = 0;
	}

	/**
	 * Compara les dues cartes actualment seleccionades. Si les cartes són iguals,
	 * les deshabilita; si no, les gira novament. Reinicia el comptador de girades.
	 */
	public void comprobarSiTotesLesCartesEstanRevelades() {
		boolean totesLesCartesEstanRevelades = true;

		for (Card c : llistaCartes) {
			if (c.boton.isEnabled()) {
				totesLesCartesEstanRevelades = false;
				break;
			}
		}
		if (totesLesCartesEstanRevelades) {
			Model.detenerTimer();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String timestamp = dateFormat.format(new Date());

			int option = JOptionPane.showConfirmDialog(null,
					"¡Enhorabona, has revelat totes les cartes!\n¿Vols guardar el récord?", "Fin de la partida",
					JOptionPane.YES_NO_OPTION);
			getBtnHOF().setEnabled(true);

			if (option == JOptionPane.YES_OPTION) {
				String usuario = getLblUser().getText();
				int dificultad = Model.dificultat;
				long duracionEnSegundos = getTiempoDeJuego();

				model.guardarRecord(usuario, dificultad, timestamp, duracionEnSegundos);

				Bson filter = and(eq("dificultad", dificultad), lt("duracion", duracionEnSegundos));
				long count = model.records.countDocuments(filter);
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "Enhorabona, has batut el record de la categoria!");
				}

				JOptionPane.showMessageDialog(null, "Record guardat exitosament.");
			}
		}
	}

	/**
	 * Obté el temps de joc actual com un valor numèric en milisegons.
	 *
	 * @return El temps de joc en milisegons.
	 */
	public long getTiempoDeJuego() {
		try {
			String tiempoTexto = lblTempsChange.getText().trim();

			// Extrae solo la parte numérica del texto
			String[] partes = tiempoTexto.split(" ");
			if (partes.length > 0) {
				return Long.parseLong(partes[0]);
			} else {
				System.err.println("El tiempo de juego está vacío.");
			}
		} catch (NumberFormatException e) {
			System.err.println("Error al convertir el tiempo de juego a número: " + e.getMessage());
		}
		return 0;
	}

	/**
	 * Actualitza el text de l'etiqueta de temps amb el text proporcionat.
	 *
	 * @param texto El nou text per a l'etiqueta de temps.
	 */
	public void actualizarLabelTiempo(String texto) {
		lblTempsChange.setText(texto);
	}

}
