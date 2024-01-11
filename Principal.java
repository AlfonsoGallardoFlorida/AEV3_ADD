package es.florida.avaluable;

public class Principal {

	public static void main(String[] args) {
		Vista vista = new Vista();
		Model model = new Model(vista);
		Controlador controlador = new Controlador(vista, model);
	}

}
