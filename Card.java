package es.florida.avaluable;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.Timer;

/**
 * Classe que representa una carta en el joc de memory.
 */
public class Card {

	Vista vista;
	static boolean esperant = false;
	boolean isGirada = true;
	static int numGirades = 0;
	JToggleButton boton;
	ImageIcon imagenActual;
	ImageIcon imagenVolteada;
	ImageIcon imagenBack;
	String identificador;

	/**
	 * Constructor de la classe Card.
	 *
	 * @param vista          L'objecte Vista associat.
	 * @param boton          El botó que representa la carta.
	 * @param imagenVolteada La imatge de la carta quan està girada.
	 */
	public Card(Vista vista, JToggleButton boton, ImageIcon imagenVolteada) {
		this.vista = vista;
		this.boton = boton;
		this.imagenVolteada = imagenVolteada;
		boton.addActionListener(actionListenerGirarCarta);
		imagenBack = resizeAndSetIcon(boton, "C:\\Users\\Pompo\\Downloads\\magic.jpg");
		girarCarta();
		numGirades = 0;
	}

	ActionListener actionListenerGirarCarta = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!esperant && !isGirada) {
				girarCarta();
				boton.setSelected(isGirada);

				if (numGirades >= 2) {
					esperant = true;

					Timer timer = new Timer(500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							for (Card c : vista.getListaCard()) {
								if (c.isGirada()) {
									if (vista.carta1 == null) {
										vista.carta1 = c;
									} else if (vista.carta2 == null) {
										vista.carta2 = c;
									}
								}
							}
							vista.compararCartes();
							for (Card c : vista.getListaCard()) {
								if (c.isGirada()) {
									c.girarCarta();
								}
							}
							vista.comprobarSiTotesLesCartesEstanRevelades();
							numGirades = 0;
							esperant = false;
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
			}
		}
	};

	/**
	 * Obté l'identificador de la carta.
	 *
	 * @return L'identificador de la carta.
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Deshabilita la carta.
	 */
	public void deshabilitar() {
		boton.setEnabled(false);
	}

	/**
	 * Gira la carta.
	 */
	public void girarCarta() {
		isGirada = !isGirada;
		if (!isGirada) {
			imagenActual = imagenBack;
		} else {
			imagenActual = imagenVolteada;
		}
		boton.setIcon(imagenActual);
		numGirades++;
	}

	/**
	 * Comprova si la carta està girada.
	 *
	 * @return Cert si la carta està girada, fals altrament.
	 */
	public boolean isGirada() {
		return isGirada;
	}

	/**
	 * Redimensiona i estableix la icona per al botó.
	 *
	 * @param boto La instància del botó.
	 * @param ruta La ruta de la imatge.
	 * @return La icona redimensionada.
	 */
	private ImageIcon resizeAndSetIcon(JToggleButton boto, String ruta) {
		ImageIcon iconaOriginal = new ImageIcon(ruta);

		boto.setPreferredSize(new Dimension(85, 108));

		Image imatgeEscalada = iconaOriginal.getImage().getScaledInstance(boto.getPreferredSize().width,
				boto.getPreferredSize().height, Image.SCALE_SMOOTH);
		ImageIcon iconaEscalada = new ImageIcon(imatgeEscalada);

		return iconaEscalada;
	}

}
