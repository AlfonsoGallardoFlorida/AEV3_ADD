package es.florida.avaluable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Controlador que gestiona les interaccions entre la vista i el model.
 */
public class Controlador {
	private Model model;
	private Vista vista;
	static int numGiradas = 0;

	private ActionListener actionListenerBtnIniciarSesio;
	private ActionListener actionListenerBtnRegistrarse;
	private ActionListener actionListenerBtnValidarRegistre;
	private ActionListener actionListenerBtnHallOfFame;
	private ActionListener actionListenerBtnValidarIniciSessio;
	private ActionListener actionListenerBtnEixir;
	private ActionListener actionListenerBtnTornar;
	private ActionListener actionListenerBtnJugar;

	Controlador(Vista vista, Model model) {
		this.vista = vista;
		this.model = model;
		control();
	}

	/**
	 * Configura els ActionListeners per als diferents botons de la interfície.
	 */
	public void control() {
		/**
		 * ActionListener per al botó d'iniciar sessió. Si ja hi ha una sessió iniciada,
		 * desactiva el botó; sinó, oculta el panell inicial i mostra el panell d'inici
		 * de sessió.
		 */
		actionListenerBtnIniciarSesio = new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Model.sessioIniciada) {
					vista.getBtnIniciarSessio().disable();
				} else {
					vista.ocultarPanelInicial();
					vista.mostrarPanelIniSessio();
				}
			}
		};
		/**
		 * ActionListener per al botó de registre. Si ja hi ha una sessió iniciada,
		 * desactiva el botó; sinó, oculta el panell inicial i mostra el panell de
		 * registre.
		 */
		actionListenerBtnRegistrarse = new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Model.sessioIniciada) {
					vista.getBtnIniciarSessio().disable();
				} else {
					vista.ocultarPanelInicial();
					vista.mostrarPanelRegistre();
				}
			}
		};
		/**
		 * ActionListener per al botó de validar inici de sessió. Inicia la sessió
		 * cridant el mètode "iniciarSessio" del model.
		 */
		actionListenerBtnValidarIniciSessio = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				model.iniciarSessio();
			}
		};
		/**
		 * ActionListener per al botó de validar registre. Registra un usuari cridant el
		 * mètode "registrarUsuari" del model.
		 */
		actionListenerBtnValidarRegistre = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.registrarUsuari();
			}
		};
		/**
		 * ActionListener per al botó de jugar. Inicia una partida cridant el mètode
		 * "jugar" del model.
		 */
		actionListenerBtnJugar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.jugar();
			}
		};
		/**
		 * ActionListener per al botó de tornar. Oculta els panells d'inici de sessió i
		 * registre i mostra el panell inicial.
		 */
		actionListenerBtnTornar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vista.ocultarPanelIniSessio();
				vista.ocultarPanelRegistre();
				vista.mostrarPanelInicial();
			}
		};
		/**
		 * ActionListener per al botó de sortir. Mostra un diàleg de confirmació i, si
		 * es confirma, crida al mètode "eixir" de la vista.
		 */
		actionListenerBtnEixir = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Vols eixir?");
				if (dialogResult == JOptionPane.YES_OPTION) {
					vista.eixir();
				}
			}
		};
		/**
		 * ActionListener per al botó de Hall of Fame. Oculta els panells de 2x4 i 4x4,
		 * mostra els panells de rècords (records4 i records8) i crida al mètode
		 * "agarrarRecords" del model.
		 */
		actionListenerBtnHallOfFame = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vista.ocultar2x4();
				vista.ocultar4x4();
				vista.mostrarRecords4();
				vista.mostrarRecords8();
				model.agarrarRecords();
			}
		};

// Assignar ActionListeners als botons de la interfície
		vista.getBtnIniciarSessio().addActionListener(actionListenerBtnIniciarSesio);
		vista.getBtnRegistre().addActionListener(actionListenerBtnRegistrarse);
		vista.getBtnArrere().addActionListener(actionListenerBtnTornar);
		vista.getBtnArrereRegistre().addActionListener(actionListenerBtnTornar);
		vista.getBtnEixir().addActionListener(actionListenerBtnEixir);
		vista.getBtnValidar().addActionListener(actionListenerBtnValidarIniciSessio);
		vista.getBtnValidarRegistre().addActionListener(actionListenerBtnValidarRegistre);
		vista.getBtnJugar().addActionListener(actionListenerBtnJugar);
		vista.getBtnHOF().addActionListener(actionListenerBtnHallOfFame);
	}
}