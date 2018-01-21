import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MathClient
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int contador = 0;

		while (true)
		{
			try
			{
				// Returns a reference to the remote object Registry on the specified host and port.
				Registry registry = LocateRegistry.getRegistry("localhost", 8000);

				// 'lookup' returns the remote reference bound to the specified name in this registry.
				Ischolar myschol = (Ischolar) registry.lookup("scholar");

				// let's execute our remote operation and keep the return value in 'result'!

				System.out.println("Introduza agora o que pretende fazer:");
				System.out.println("1:Adicionar autor");
				System.out.println("2:Remover autor");
				System.out.println("3:Adicionar publicacao");
				System.out.println("4:Remover publicacao");
				System.out.println("5: Lista de autores e respetivas publicações");
				System.out.println("6: Lista de autores de uma dada instituição");
				System.out.println("7: Estatísticas gerais");
				System.out.println("8: Terminar a aplicação");

				String operacao = scan.nextLine(); // Operação decidida pelo utilizador

				String autentificacao = new String();

				if (operacao.equals("1")) // Adicionar autor
				{
					System.out.print("Introduza o nome do autor: ");
					String autor = scan.nextLine();

					System.out.print("Introduza a instituição a que o autor pertence: ");
					String instituicao = scan.nextLine();

					autentificacao = myschol.adicionaAutor(autor, instituicao);
				}
				else if (operacao.equals("2")) // Remover autor
				{
					System.out.print("Introduza o nome do autor que pertende eliminar: ");
					String nome = scan.nextLine();

					autentificacao = myschol.removeAutor(nome);

				}
				else if (operacao.equals("3")) // Adicionar publicação
				{
					System.out.print("Introduza o título da publicação: ");
					String titulo = scan.nextLine();

					System.out.print("Quantos autores estão associados ?");
					int numero = scan.nextInt();
					scan.nextLine();

					String[] nomes = new String[numero];
					for (int i = 0; i < numero; i++)
					{
						System.out.print("Introduza um nome: ");
						nomes[i] = scan.nextLine();
					}

					autentificacao = myschol.adicionaPublicacao(titulo, nomes);
				}
				else if (operacao.equals("4")) // Remover Publicação
				{
					System.out.print("Introduza o titulo da publicação que pretende eliminar: ");
					String titulo_remove = scan.nextLine();

					autentificacao = myschol.removePublicacao(titulo_remove);
				}

				else if (operacao.equals("5")) // Lista de autores e respetivas publicações
				{
					autentificacao = myschol.listaAutores();
				}

				else if (operacao.equals("6")) // Lista de autores de uma dada instituição
				{
					System.out.print("Introduza o nome da instituiçao sobre a qual pretende adquirir informações: ");
					String instituicao = scan.nextLine();

					autentificacao = myschol.listaAutoresInstituicao(instituicao);

				}

				else if (operacao.equals("7")) // Estatísticas gerais
				{
					autentificacao = myschol.mostraEstatistica();
				}

				else if (operacao.equalsIgnoreCase("8")) // Terminar a aplicação
				{
					break;
				}
				System.out.println("**********************************************************");
				System.out.println(autentificacao);
				System.out.println("**********************************************************");

			}
			catch (Exception e) // catching Exception means that we are handling all errors in the same block
			{ // usually it is advisable to use multiple catch blocks and perform different error handling actions
				// depending on the specific exception type caught

				System.err.println("Ocorreu um erro: ");
				e.printStackTrace(); // prints detailed information about the exception
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e1)
				{
					// do nothing
				}

				contador++;
				int N = 5; // número de tentativas
				if (contador == N)
					break;

			}
		}
	}
}
