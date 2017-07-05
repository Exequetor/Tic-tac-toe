import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
public class Cliente {
	private static Socket socket = null;
	private static int[][]tab = new int[3][3];
	private static PrintWriter salida = null;
	private static Scanner	entrada = null;
	private static int tipo,x,y;
	private static int jugador;
	private static boolean terminar = false;
	private static boolean esperando = true;
	
	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 2017);
			System.out.println("Se conecto con el servidor");
			
			salida = new PrintWriter(socket.getOutputStream());
			entrada = new Scanner(new BufferedInputStream(socket.getInputStream()));

			ActionListener listenerClient = new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == GUIGato.btn00) {
						x = 0;
						y = 0;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn01) {
						x = 0;
						y = 1;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn02) {
						x = 0;
						y = 2;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn10) {
						x = 1;
						y = 0;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn11) {
						x = 1;
						y = 1;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn12) {
						x = 1;
						y = 2;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn20) {
						x = 2;
						y = 0;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn21) {
						x = 2;
						y = 1;
						esperando = false;
					}
					if(e.getSource() == GUIGato.btn22) {
						x = 2;
						y = 2;
						esperando = false;
					}	
				}
			};
			GUIGato gato = new GUIGato (listenerClient);
			gato.setTitle("Gato!");
			gato.setVisible(true);
			GUIGato.setStatus("Esperando al oponente");
			for (int i = 0 ; i < 3 ; i++) {
				for (int j = 0 ; j < 3 ; j++) {
					GUIGato.setDisabled(i, j);
				}
			}
			jugador = entrada.nextInt();
			entrada.nextLine();
			GUIGato.setStatus("Esperando al jugador 1");
			gameLoop();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void gameLoop () {
		while(!terminar){
			Imp(tab);
			System.out.println("Inicio");
			tipo = entrada.nextInt();
			System.out.println(tipo);
			//System.out.println(entrada.nextLine());

			switch(tipo){

				case 1: //System.out.println(entrada.nextLine());
					for (int i = 0 ; i < 3 ; i ++) {
						for (int j = 0 ; j < 3 ; j++) {
								GUIGato.setDisabled(i, j);
						}
					}
					System.out.println(entrada.nextLine());
						System.out.println("Espera");
					break;
				case 0: System.out.println(entrada.nextLine());
					for (int i = 0 ; i < 3 ; i ++) {
						for (int j = 0 ; j < 3 ; j++) {
							if ((GUIGato.getTextButton(i, j).equals ("")))
								GUIGato.setEnabled(i, j);
						}
					}
					do{
						GUIGato.setStatus("Tu turno!");
						while (esperando)
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						esperando = true;
						System.out.println("Turno");
					}while(x>2 || y>2 || x<0 || y<0);
					if (jugador == 1) {
						GUIGato.setX(x, y);
					} else {
						GUIGato.setO(x, y);
					}
					salida.println(x+" "+y);
					salida.flush();
					break;
				case 3: String men = entrada.nextLine();
					System.out.print(men);
					GUIGato.setStatus(men);
					checkMatrix ();
					terminar = true;
					break;
				case 2: int idJug = entrada.nextInt();
					if (jugador == 1) {
						GUIGato.setStatus("Esperando al jugador 2");
					} else
						GUIGato.setStatus("Esperando al jugador 1");
					x = entrada.nextInt();
					y = entrada.nextInt();
					tab[x][y] = idJug;
					if (idJug == 1) {
						GUIGato.setX(x, y);
					} else {
						GUIGato.setO(x, y);
					}
					System.out.println("Actualizar");
					break;
				default: //System.out.println(entrada.nextLine());
						 break;
			}				
		}
	}

	public static void checkMatrix () {
		for (int i = 0 ; i < 3 ; i ++) {
			for (int j = 0 ; j < 3 ; j++) {
					GUIGato.setDisabled(i, j);
			}
		}
		//Para jugador 1
		if (tab[0][0] == 1 && tab[0][1] == 1 && tab[0][2] == 1) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(0, 1, Color.red);
			GUIGato.setColor(0, 2, Color.red);
			
		}
		if (tab[1][0] == 1 && tab[1][1] == 1 && tab[1][2] == 1) {
			GUIGato.setColor(1, 0, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(1, 2, Color.red);
		}
		if (tab[2][0] == 1 && tab[2][1] == 1 && tab[2][2] == 1) {
			GUIGato.setColor(2, 0, Color.red);
			GUIGato.setColor(2, 1, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][0] == 1 && tab[1][0] == 1 && tab[2][0] == 1) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(1, 0, Color.red);
			GUIGato.setColor(2, 0, Color.red);
		}
		if (tab[0][1] == 1 && tab[1][1] == 1 && tab[2][1] == 1) {
			GUIGato.setColor(0, 1, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 1, Color.red);
		}
		if (tab[0][2] == 1 && tab[1][2] == 1 && tab[2][2] == 1) {
			GUIGato.setColor(0, 2, Color.red);
			GUIGato.setColor(1, 2, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][0] == 1 && tab[1][1] == 1 && tab[2][2] == 1) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][2] == 1 && tab[1][1] == 1 && tab[2][0] == 1) {
			GUIGato.setColor(0, 2, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 0, Color.red);
		}
		//Para jugador 2
		if (tab[0][0] == 2 && tab[0][1] == 2 && tab[0][2] == 2) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(0, 1, Color.red);
			GUIGato.setColor(0, 2, Color.red);
		}
		if (tab[1][0] == 2 && tab[1][1] == 2 && tab[1][2] == 2) {
			GUIGato.setColor(1, 0, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(1, 2, Color.red);
		}
		if (tab[2][0] == 2 && tab[2][1] == 2 && tab[2][2] == 2) {
			GUIGato.setColor(2, 0, Color.red);
			GUIGato.setColor(2, 1, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][0] == 2 && tab[1][0] == 2 && tab[2][0] == 2) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(1, 0, Color.red);
			GUIGato.setColor(2, 0, Color.red);
		}
		if (tab[0][1] == 2 && tab[1][1] == 2 && tab[2][1] == 2) {
			GUIGato.setColor(0, 1, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 1, Color.red);
		}
		if (tab[0][2] == 2 && tab[1][2] == 2 && tab[2][2] == 2) {
			GUIGato.setColor(0, 2, Color.red);
			GUIGato.setColor(1, 2, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][0] == 2 && tab[1][1] == 2 && tab[2][2] == 2) {
			GUIGato.setColor(0, 0, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 2, Color.red);
		}
		if (tab[0][2] == 2 && tab[1][1] == 2 && tab[2][0] == 2) {
			GUIGato.setColor(0, 2, Color.red);
			GUIGato.setColor(1, 1, Color.red);
			GUIGato.setColor(2, 0, Color.red);
		}
	}
	
	public static void Imp(int [][]tab)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
				System.out.print(tab[i][j]+"	");
			System.out.println();
		}
	}
}
