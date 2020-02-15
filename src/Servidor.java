import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    static ServerSocket socketServidor;
    static Socket socketNou;
    static InetSocketAddress direccion;
    static DataInputStream entrada;
    static DataOutputStream sortida;

    public static void main(String[] args) throws IOException {
        try {
            conecta("127.0.0.1", 8564);

            // Inicialización de un socket cliente que haya hecho una petición al servidor
            socketNou = socketServidor.accept();
            entrada = new DataInputStream(socketNou.getInputStream());
            sortida = new DataOutputStream(socketNou.getOutputStream());

            String entradaConsola = ""; // Entrada usuari
            while (true) {
                entradaConsola = entrada.readUTF();
                String pregunta = ""; // On guardarem els bytes que componen la pregunta
                String resposta = ""; //Resposta Servidor

                for (Character character : entradaConsola.toCharArray()) {
                    if (character == '?') {
                        //Aturam si ja tenim sa pregunta
                        pregunta = pregunta + character;
                        break;
                    } else {
                        //Sino construim string
                        pregunta = pregunta + character;
                    }
                }

                switch (pregunta.toUpperCase()) {
                    case "COM ET DIUS?": {
                        resposta = "Em dic Toni Ballador.";
                        break;
                    }
                    case "QUANTES LINIES DE CODI TENS?": {
                        resposta = "Tenc 100 linies.";
                        break;
                    }
                    case "ADEU":
                        tanca();
                        break;
                    default: {
                        resposta = "No he entès la pregunta.";
                    }
                }
                sortida.writeUTF(resposta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            tanca();
        }
    }

    public static void conecta(String ip, int puerto) throws IOException {
        socketServidor = new ServerSocket(); // Se inicializa el socket del servidor
        direccion = new InetSocketAddress(ip, puerto); // inicializacion de dirreción donde el socketServidor escuchará
        socketServidor.bind(direccion); // El socketServidor empieza a escuchar por la direccion de la linea anterior
        System.out.println("Servidor Inicialitzat " + socketServidor.getLocalSocketAddress());
    }

    public static void tanca() throws IOException {
        sortida.writeUTF("Adeu!");
        entrada.close();
        sortida.close();
        socketNou.close();
        socketServidor.close();
        System.out.println("Servidor Aturat");
    }
}
