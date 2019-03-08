package es.studium;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class PracticaMongo {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        int opcion;
        do {
        	//Creamos las opciones para el usuario
        	 System.out.println("1. Mostrar humanos ");
             System.out.println("2. Mostrar personajes que nacieron antes de 1979 ");
             System.out.println("3. Mostrar varitas holly ");
             System.out.println("4. Mostrar personajes vivos y de Hogwards ");
             System.out.println("5. Salir");
             opcion = sc.nextInt();
             switch (opcion) {
                case 1: {
                	mostrarHumanos();
                    break;
                }
                case 2: {
                	anterior1979();
                    break;
                }
                case 3: {
                	varitaHolly();
                    break;
                }
                case 4: {
                	hogwartsVivos();
                    break;
                }
                case 5: {
                    System.out.println("Programa Cerrado");
                    break;
                }
            }
        } while (opcion != 5);
    }

    private static void mostrarHumanos() {
        MongoClient conexion = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = conexion.getDatabase("harrypotter");
        MongoCollection<?> harryp = database.getCollection("harryp");
        FindIterable<?> res = harryp.find(eq("species", "human")).
        		projection(fields(include("name", "species"),excludeId()));
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
        }
        conexion.close();
    }
    private static void anterior1979() {
        MongoClient conexion = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = conexion.getDatabase("harrypotter");
        MongoCollection<?> harryp = database.getCollection("harryp");
        FindIterable<?> res = harryp.find(lt("yearOfBirth", 1979))
                .projection(fields(include("name", "yearOfBirth"), excludeId()));
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
        }
        conexion.close();
    }
    private static void varitaHolly() {
        MongoClient conexion = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = conexion.getDatabase("harrypotter");
        MongoCollection<?> harryp = database.getCollection("harryp");
        FindIterable<?> res = harryp.find(eq("wand.wood", "holly"))
                .projection(fields(include("name", "wand.wood"), excludeId()));
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
        }
        conexion.close();

    }
    private static void hogwartsVivos() {
        MongoClient conexion = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = conexion.getDatabase("harrypotter");
        MongoCollection<?> harryp = database.getCollection("harryp");
        FindIterable<?> res = harryp.find(and(eq("alive", true),eq("hogwartsStudent", true)))
                .projection(fields(include("name", "alive", "hogwartsStudent"), excludeId()));
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
        }
        conexion.close();
    }
}

