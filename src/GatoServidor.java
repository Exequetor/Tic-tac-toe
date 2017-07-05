import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GatoServidor {
    private static ServerSocket servidor = null; //Socket por donde escucha el servidor
    private static Socket j1 = null; //Socket para comunicarse con jugador 1
    private static Socket j2 = null; //Socker para comunicarse con jugador 2
    private static int contPartidas = 0;
    private static final int DEFAULT_PORT = 2017; //Puerto por defecto
    
    public static void main(String[] args){
        //Se crea el socket en el puerto indicado o default
        try{
            servidor = args.length!=0? new ServerSocket(Integer.parseInt(args[0])):new ServerSocket(DEFAULT_PORT);
        }
        catch(IOException e){
            System.err.println(e);
        }
        //Ciclo infinito para mantenerse escuchando
        while(true){
            if(servidor!=null){
                System.out.println("Servidor esperando clientes...");
                try{
                    j1 = servidor.accept(); //Se acepta la conexión del jugador 1
                    System.out.println("Se conecto J1, esperando a J2...");
                    j2 = servidor.accept(); //Se acepta la conexión del jugador 2
                    System.out.println("Se conecto J2, iniciando partida...");
                    if(j1!=null && j2!=null){
                        contPartidas++;
                        //Se crea un hilo para atender la partida
                        Thread hiloPartida = new Thread(new HiloServidor(j1,j2,contPartidas));
                        //El hilo se coloca en el estado Listo
                        hiloPartida.start();
                        
                    }
                }
                catch(IOException e){
                    System.err.println(e);
                }
            }
        }
    }
}

class HiloServidor implements Runnable{
    private final Socket j1;
    private final Socket j2;
    private final int idPart;
    public HiloServidor(Socket socketJ1,Socket socketJ2,int idPart){
        this.j1 = socketJ1;
        this.j2 = socketJ2;
        this.idPart = idPart;
    }
    
    @Override
    public void run(){
        Scanner entradaJ1 = null; //Flujo de entrada jugador 1
        Scanner entradaJ2 = null; //Flujo de entrada jugador 2
        PrintWriter salidaJ1 = null; //Flujo de salida jugador 1
        PrintWriter salidaJ2 = null; //Flujo de salida jugador 2
        Gato partida = new Gato(); //Juego de gato
        try{
            System.out.println("Partida "+idPart+" creada");
            entradaJ1 = new Scanner(new BufferedInputStream(j1.getInputStream()));
            entradaJ2 = new Scanner(new BufferedInputStream(j2.getInputStream()));
            salidaJ1 = new PrintWriter(j1.getOutputStream());
            salidaJ2 = new PrintWriter(j2.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e);
        }
        
        if(entradaJ1!=null && entradaJ2!=null && salidaJ1!=null && salidaJ2!=null){
            salidaJ1.println("1 Bienvenido eres el jugador 1");
            salidaJ1.flush();
            salidaJ2.println("2 Bienvenido eres el jugador 2");
            salidaJ2.flush();
            partida.jugar(idPart,entradaJ1,salidaJ1,entradaJ2,salidaJ2); //Se inicia el juego
            try{
                j1.close();
                j2.close();
            }catch(IOException e){
                System.err.println(e);
            }
        }
    }
}

class Gato{
    private final int ID_J1 = 1;
    private final int ID_J2 = 2;
    private int tablero[][];
    
    public void jugar(int idPart,Scanner entradaJ1, PrintWriter salidaJ1, Scanner entradaJ2, PrintWriter salidaJ2){
        int estadoTablero;
        int jugada[] = new int[2];
        boolean correcto;        
        //Se inicia el tablero de gato;
        this.tablero = new int[3][3];
        do{
            System.out.println("Partida "+idPart+" - Turno jugador "+ID_J1);
            ordenaEsperar(salidaJ2);
            do{
                pideJugada(salidaJ1);
                correcto = obtieneJugada(entradaJ1,ID_J1,jugada);
                System.out.println("Partida "+idPart+" - Movimiento "+ID_J1+"= "+jugada[0]+" "+jugada[1]);
            }while(!correcto);
            ordenaActualizar(ID_J1,jugada[0],jugada[1],salidaJ1,salidaJ2);
            estadoTablero = revisaTablero(ID_J1);
            System.out.println("Partida "+idPart+" - Estado de tablero = "+estadoTablero);
            if(estadoTablero!=0)
                break;
            System.out.println("Partida "+idPart+" - Turno jugador "+ID_J2);
            ordenaEsperar(salidaJ1);
            do{
                pideJugada(salidaJ2);
                correcto = obtieneJugada(entradaJ2,ID_J2,jugada);
                System.out.println("Partida "+idPart+" - Movimiento = "+jugada[0]+" "+jugada[1]);
            }while(!correcto);
            ordenaActualizar(ID_J2,jugada[0],jugada[1],salidaJ1,salidaJ2);
            estadoTablero = revisaTablero(ID_J2);
            System.out.println("Partida "+idPart+" - Estado de tablero = "+estadoTablero);
            if(estadoTablero!=0)
                break;
        }while(true);
        switch (estadoTablero){
            case 1:
                victoria(salidaJ1,salidaJ2);
                break;
            case 2:
                victoria(salidaJ2,salidaJ1);
                break;
            default:
                empate(salidaJ1,salidaJ2);
                break;
        }
    }
    
    private void ordenaEsperar(PrintWriter jugador){
        //Esperando movimiento del oponente...";
        jugador.println("1 Esperando movimiento del oponente...");
        jugador.flush();
    }
    
    private void pideJugada(PrintWriter jugador){
        jugador.println("0 Introduzca X Y: ");
        jugador.flush();
    }
    
    private void victoria(PrintWriter ganador, PrintWriter perdedor){
        ganador.println("3 Felicidades has ganado!");
        ganador.flush();
        perdedor.println("3 Lastima has perdido");
        perdedor.flush();
    }
    
    private void empate(PrintWriter ganador, PrintWriter perdedor){
        ganador.println("3 Empate!");
        ganador.flush();
        perdedor.println("3 Empate!");
        perdedor.flush();
    }
    //METODO QUE ORDENA A LOS CLIENTES ACTUALIZAR LOS TABLEROS
    //Recibe el identificador de jugador que realizo
    //el ultimo movimiento y ambos flujos de salida
    private void ordenaActualizar(int idj,int x,int y,PrintWriter j1, PrintWriter j2){
          j1.println("2 "+idj+" "+x+" "+y);
          j1.flush();
          j2.println("2 "+idj+" "+x+" "+y);
          j2.flush();
    }
    //METODO QUE OBTIENE LA JUGADA ENVIADA POR UN CLIENTE
    //Recibe como parametro el identificador y Scanner asociado al flujo de 
    //entrada del jugador que tiro y un arreglo para guardar la jugada
    private boolean obtieneJugada(Scanner juega,int idJug,int []jugada){
        int fila,columna;
        fila = juega.nextInt();
        columna = juega.nextInt();
        if(coloca(fila,columna,idJug)){
            jugada[0] = fila;
            jugada[1] = columna;
            return true;
        }
        return false;
    }
    //METODO QUE VERIFICA SI ES MOVIMIENTO VALIDO
    //Si el movimiento es valido realiza los cambios y devuelve true
    //Sino devuelve false
    private boolean coloca(int fila, int columna,int simbolo){
        if(tablero[fila][columna]==0){
            tablero[fila][columna] = simbolo;
            return true;
        }
        return false;
    }
    //METODO QUE VERIFICA SI EXISTE UNA COMBINACION GANADORA
    //Retorna 0 si no existe, 1 si gana J1, 2 si gana J2, 3 si es empate  
    private int revisaTablero(int idJug){
        boolean clmn = true, fila = true, diag1 = true, diag2 = true;    
        for(int i=0;i<3;i++){
            if(this.tablero[i][0]!=idJug && clmn)//Comprueba columna
                clmn = false;
            if(this.tablero[0][i]!=idJug && fila)//Comprueba fila
                fila = false;
            if(this.tablero[i][i]!=idJug && diag1)//Comprueba diagonal principal
                diag1 = false;
            if(this.tablero[i][2-i]!=idJug && diag2)//Comprueba diagonal restante
                diag2 = false;
            //Si todas las combinaciones no son ganadoras
            if(!clmn && !fila && !diag1 && !diag2)
                break;
        }
        if(clmn || fila || diag1 || diag2)//Si alguna combinacion es ganadora
            return idJug; //Devuelve el numero de jugador
        for(int i=0;i<3;i++) //Verifica si es empate
            for(int j=0;j<3;j++)
                if(this.tablero[i][j]==0)//Si hay una casilla vacía no es empate
                    return 0;//Continua el juego
        return 3; //Empate
    }
}
