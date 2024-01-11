package es.florida.avaluable;

import static com.mongodb.client.model.Filters.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * La classe Model representa el model de l'aplicació, encarregat de gestionar
 * les col·leccions de MongoDB, la lògica de negoci i l'estat global de
 * l'aplicació.
 */
public class Model {

	// Col·leccions de MongoDB per a usuaris, registres i imatges
	MongoCollection<Document> users;
	MongoCollection<Document> records;
	MongoCollection<Document> imatges;

	private static Timer temporitzador;

	static Vista vista;
	static String usuari;
	static boolean sessioIniciada = false;
	static int dificultat = 0;

	private List<String> imatgesPerAJugar;
	private long tempsInici;

	/**
	 * Constructor del Model.
	 */
	public Model(Vista vista) {
		Model.vista = vista;
		Connexio c = new Connexio();
		users = c.getUsuarios();
		records = c.getRecords();
		imatges = c.getImg();
	}

	/**
	 * Calcula la suma de verificació SHA-256 d'una cadena d'entrada.
	 *
	 * @param input La cadena d'entrada per a la qual es calcularà la suma de
	 *              verificació SHA-256.
	 * @return La suma de verificació SHA-256 en format hexadecimal o null si hi ha
	 *         problemes amb l'algorisme.
	 */
	public static String calculateSHA256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Intenta iniciar una sessió de l'usuari verificant les credencials
	 * proporcionades. Mostra missatges d'error o èxit mitjançant finestres
	 * emergents.
	 */
	public void iniciarSessio() {
		if (!sessioIniciada) {
			String nomUsuari = vista.getTxtUsuIni();
			String pass = vista.getTxPassIni();
			String hashedPass = calculateSHA256(pass);

			Bson queryUser = eq("user", nomUsuari);
			FindIterable<Document> iterable = users.find(queryUser);
			Document document = iterable.first();
			if (document != null) {
				String userFromDB = document.getString("user");
				String passFromDB = document.getString("pass");
				if (nomUsuari.equals(userFromDB)) {
					if (hashedPass.equals(passFromDB)) {
						sessioIniciada = true;
						usuari = userFromDB;
						JOptionPane.showMessageDialog(null, "Inici de sessió exitós.");
						vista.getLblUser().setText(userFromDB);
						vista.ocultarPanelIniSessio();
						vista.ocultarPanelRegistre();
						vista.mostrarPanelInicial();
					} else {
						JOptionPane.showMessageDialog(null, "Contrasenya incorrecta.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "L'usuari no existeix.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error al iniciar sessió\nJa ni ha una sessió iniciada");
		}
	}

	/**
	 * Intenta registrar un nou usuari amb el nom d'usuari i la contrasenya
	 * proporcionats. Mostra missatges d'error o èxit mitjançant finestres
	 * emergents.
	 */
	public void registrarUsuari() {
		String nomUsuari = vista.getTxtUsuReg();
		String pass = vista.getTxtPassReg();

		if (nomUsuari.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nom d'usuari i la contrasenya no poden estar buides.");
			return;
		}

		String hashedPass = calculateSHA256(pass);

		Document doc = new Document();

		Bson queryUser = eq("user", nomUsuari);
		FindIterable<Document> iterable = users.find(queryUser);
		Document document = iterable.first();
		if (document != null) {
			String userFromDB = document.getString("user");
			if (nomUsuari.equals(userFromDB)) {
				JOptionPane.showMessageDialog(null, "Ja existeix un usuari amb eixe nom.");
			}
		} else {
			doc.append("user", nomUsuari);
			doc.append("pass", hashedPass);
			users.insertOne(doc);
			JOptionPane.showMessageDialog(null, "Usuari registrat amb éxit.");
			vista.ocultarPanelIniSessio();
			vista.ocultarPanelRegistre();
			vista.mostrarPanelInicial();
		}
	}

	/**
	 * Obté els registres de partides i actualitza les taules de records de la
	 * interfície. Mostra un missatge d'error si no s'ha iniciat sessió.
	 */
	public void agarrarRecords() {
		if (sessioIniciada) {
			DefaultTableModel tableModel4 = new DefaultTableModel(new Object[][] {},
					new String[] { "Usuari", "Dificultat", "Data", "Temps" });
			DefaultTableModel tableModel8 = new DefaultTableModel(new Object[][] {},
					new String[] { "Usuari", "Dificultat", "Data", "Temps" });

			try (MongoCursor<Document> cursor = records.find().iterator()) {
				while (cursor.hasNext()) {
					Document document = cursor.next();

					Object duracionObj = document.get("duracion");
					long duracion = 0;
					if (duracionObj instanceof Number) {
						duracion = ((Number) duracionObj).longValue();
					}

					int dificultad = document.getInteger("dificultad");
					if (dificultad == 8) {
						tableModel4.addRow(new Object[] { document.getString("usuario"), dificultad,
								document.getString("timestamp"), duracion });
					} else if (dificultad == 16) {
						tableModel8.addRow(new Object[] { document.getString("usuario"), dificultad,
								document.getString("timestamp"), duracion });
					}
				}
			}

			vista.getTableRecords4().setModel(tableModel4);
			vista.getTableRecords8().setModel(tableModel8);

			configurarTabla(vista.getTableRecords4(), tableModel4);
			configurarTabla(vista.getTableRecords8(), tableModel8);
		} else {
			JOptionPane.showMessageDialog(null, "Deus iniciar sessió per a vore els records");
		}
	}

	/**
	 * Configura l'aparença i el comportament de la taula especificada amb el model
	 * donat.
	 *
	 * @param tabla  La taula a configurar.
	 * @param modelo El model de dades de la taula.
	 */
	public void configurarTabla(JTable tabla, DefaultTableModel modelo) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (c instanceof JComponent) {
					JComponent jc = (JComponent) c;
					jc.setToolTipText(
							value == null ? null : "<html><p width=\"200\">" + value.toString() + "</p></html>");
				}
				return c;
			}
		};
		for (int i = 0; i < tabla.getColumnCount(); i++) {
			tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);

		tabla.setRowSorter(sorter);

		sorter.setComparator(3, new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return o1.compareTo(o2);
			}
		});

		sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(3, SortOrder.ASCENDING)));
	}

	/**
	 * Guarda un nou rècord de partida amb les dades proporcionades a la col·lecció
	 * de registres.
	 *
	 * @param usuari    El nom de l'usuari associat al rècord.
	 * @param dificultat La dificultat de la partida (nombre de caselles del
	 *                   taulell).
	 * @param timestamp  El moment en què es va completar la partida (en format de
	 *                   data/hora).
	 * @param duracio          La duració de la partida en milisegons.
	 */
	public void guardarRecord(String usuari, int dificultat, String timestamp, long duracio) {

		Document nouRecord = new Document("usuario", usuari).append("dificultad", dificultat)
				.append("timestamp", timestamp).append("duracion", duracio);

		records.insertOne(nouRecord);
	}

	/**
	 * Guarda una imatge a partir de la seva representació Base64 en una ruta
	 * específica.
	 *
	 * @param base64Image La representació Base64 de la imatge.
	 * @param path        La ruta en la qual es guardarà la imatge.
	 */
	public void guardarImagen(String base64Image, String path) {
		try {

			byte[] imageBytes = Base64.decodeBase64(base64Image);

			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

			File dir = new File("img");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			ImageIO.write(bufferedImage, "jpg", new File(dir, path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obté una llista de rutes a imatges úniques a partir de la col·lecció
	 * d'imatges MongoDB.
	 *
	 * @param cantidad La quantitat d'imatges a obtenir.
	 * @return Una llista de rutes a imatges úniques.
	 */
	public List<String> obtindreImatges(int cantidad) {
		List<String> imatgesUniques = new ArrayList<>();

		MongoCursor<Document> cursor = imatges.find().limit(cantidad).iterator();

		int i = 0;
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			String string64 = obj.getString("base64");

			String path = "imagen" + i++ + ".jpg";
			guardarImagen(string64, path);

			imatgesUniques.add("img/" + path);
			imatgesUniques.add("img/" + path);
		}

		Collections.shuffle(imatgesUniques);

		return imatgesUniques;
	}

	/**
	 * Aquesta funció permet a l'usuari jugar un joc de memòria.
	 * 
	 * Si la sessió està iniciada (`sessioIniciada` és veritat), l'usuari pot triar
	 * la dificultat del joc (2x4 o 4x4) a través d'un quadre de diàleg.
	 * 
	 * Si l'usuari tria 2x4, es seleccionen 4 imatges aleatòries i es mostren 8
	 * targetes. Si l'usuari tria 4x4, es seleccionen 8 imatges aleatòries i es
	 * mostren 16 targetes.
	 * 
	 * Un cop l'usuari ha triat la dificultat, comença un temporitzador que
	 * actualitza el temps transcorregut cada segon.
	 * 
	 * Si la sessió no està iniciada, es mostra un missatge que indica que l'usuari
	 * ha d'iniciar sessió per a poder jugar.
	 */
	public void jugar() {
		if (sessioIniciada) {

			vista.getListaCard().clear();

			int dialogResult = JOptionPane.showOptionDialog(null,
					"Jugant com a l'usuari " + usuari + "\nElegix dificultat", "Jugar", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new String[] { "2x4", "4x4" }, "2x4");

			if (dialogResult == JOptionPane.YES_OPTION) {
				dificultat = 8;
				vista.mostrarTemps();
				vista.getBtnHOF().setEnabled(false);
				vista.getBtnIniciarSessio().setEnabled(false);
				vista.getBtnRegistre().setEnabled(false);
				vista.getBtnJugar().setEnabled(false);
				imatgesPerAJugar = obtindreImatges(4);
				vista.mostrar2x4();
				vista.ocultar4x4();
				vista.ocultarRecords4();
				vista.ocultarRecords8();

				vista.asignarImatge(
						Arrays.asList(vista.getCard1(), vista.getCard2(), vista.getCard3(), vista.getCard4(),
								vista.getCard5(), vista.getCard6(), vista.getCard7(), vista.getCard8()),
						imatgesPerAJugar);
				tempsInici = System.currentTimeMillis();
				temporitzador = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						long tempsTranscorregut = System.currentTimeMillis() - tempsInici;
						vista.actualizarLabelTiempo(tempsTranscorregut / 1000 + " segons");
					}
				});
				temporitzador.start();
			} else if (dialogResult == JOptionPane.NO_OPTION) {
				dificultat = 16;
				vista.mostrarTemps();
				vista.getBtnJugar().setEnabled(false);
				vista.getBtnHOF().setEnabled(false);
				vista.getBtnIniciarSessio().setEnabled(false);
				vista.getBtnRegistre().setEnabled(false);
				vista.mostrar2x4();
				vista.mostrar4x4();
				imatgesPerAJugar = obtindreImatges(8);

				vista.asignarImatge(
						Arrays.asList(vista.getCard1(), vista.getCard2(), vista.getCard3(), vista.getCard4(),
								vista.getCard5(), vista.getCard6(), vista.getCard7(), vista.getCard8(),
								vista.getCard9(), vista.getCard10(), vista.getCard11(), vista.getCard12(),
								vista.getCard13(), vista.getCard14(), vista.getCard15(), vista.getCard16()),
						imatgesPerAJugar);
				tempsInici = System.currentTimeMillis();
				temporitzador = new Timer(1000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						long tempsTranscorregut = System.currentTimeMillis() - tempsInici;
						vista.actualizarLabelTiempo(tempsTranscorregut / 1000 + " segons");
					}

				});
				temporitzador.start();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Deus iniciar sessió per a poder jugar");
		}
	}

	/**
	 * Deté el temporitzador si està en execució.
	 */
	public static void detenerTimer() {
		if (temporitzador != null) {
			temporitzador.stop();
		}
	}

	/**
	 * Comprova si el temps actual de la partida és millor que el millor temps
	 * registrat i mostra un missatge si és així.
	 *
	 * @param temps El temps actual de la partida.
	 */
	public static void getMillorTemps(long temps) {
		long millorTemps = vista.getTiempoDeJuego();
		if (millorTemps < temps) {
			JOptionPane.showMessageDialog(null, "Enhorabona, has batut el record de la categoria");
		}
	}
}