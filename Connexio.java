package es.florida.avaluable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connexio {
	private MongoCollection<Document> records;
	private MongoCollection<Document> usuarios;
	private MongoCollection<Document> img;
	MongoClient mongoClient;

	public Connexio() {

		JSONObject json = conexioJson();

		if (json != null) {

			String ip = json.getString("ip");
			int port = json.getInt("port");
			String database = json.getString("database");
			String records = json.getString("collection_records");
			String usuarios = json.getString("collection_usuaris");
			String img = json.getString("collection_imatges");

			mongoClient = new MongoClient(ip, port);
			MongoDatabase db = mongoClient.getDatabase(database);
			this.records = db.getCollection(records);
			this.usuarios = db.getCollection(usuarios);
			this.img = db.getCollection(img);

		} else {
			System.out.println("Connexio no realitzada");
		}
	}

	public void CerrarConexio() {
		this.mongoClient.close();

	}

	public JSONObject conexioJson() {
		String rutaFitxer = "./src/connexio.json";
		JSONObject jsonObject = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(rutaFitxer));
			StringBuilder sb = new StringBuilder();
			String linea = br.readLine();

			while (linea != null) {
				sb.append(linea);
				linea = br.readLine();
			}

			br.close();

			jsonObject = new JSONObject(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public MongoCollection<Document> getRecords() {
		return records;
	}

	public MongoCollection<Document> getUsuarios() {
		return usuarios;
	}

	public MongoCollection<Document> getImg() {
		return img;
	}
}
