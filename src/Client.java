import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {

    static Socket socket;
    static InetSocketAddress direccion;
    static DataInputStream entrada;
    static DataOutputStream sortida;

    public static void main(String[] args) throws IOException {


        try{
            conecta("127.0.0.1", 8564);
            // Inicialización de un socket cliente que haya hecho una petición al servidor
            entrada = new DataInputStream(socket.getInputStream());
            sortida = new DataOutputStream(socket.getOutputStream());

            String resposta = "";
            System.out.println("----------------------");
            while(!resposta.toUpperCase().equalsIgnoreCase("ADEU")){

                System.out.println("ENVIAR SERVIDOR");
                System.out.println("----------------------");
                System.out.print("+CLIENT: ");
                Scanner sc = new Scanner(System.in); // Scanner el cual permite personalizar el texto enviado al servidor
                resposta = sc.nextLine();
                sortida.writeUTF(resposta);
                System.out.println("-SERVIDOR: "+entrada.readUTF());
                System.out.println("----------------------\n");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            entrada.close();
            sortida.close();
            socket.close();
            System.out.println("Conexió Client finalitzada");
        }
    }

    private static void conecta(String ip, int puerto) throws IOException {
        socket = new Socket(); // Inicializamos socket cliente
        direccion = new InetSocketAddress(ip, puerto); // inicializacion de dirreción donde el socket será abierto
        socket.connect(direccion); // Se abre el socket a la dirrección especificada en la anterior linea
        System.out.println("Connexió Client Establerta: " + socket.getLocalSocketAddress());
    }

}