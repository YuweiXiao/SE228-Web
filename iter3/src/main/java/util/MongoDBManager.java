package util;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBManager {
	private MongoClient mongo;
	private String hostname;
	private String database;
	private int port;
	
	public MongoClient getMongoClient(){
		if(mongo == null) {
			mongo = new MongoClient(hostname, port);
		}
		return mongo;
	}

	public DB getDB() {
		return getMongoClient().getDB(database);
	}
	
		/*getters and setters*/
	
	public MongoClient getMongo() {
		return mongo;
	}

	public void setMongo(MongoClient mongo) {
		this.mongo = mongo;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
