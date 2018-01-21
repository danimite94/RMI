import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MathImpl extends UnicastRemoteObject implements Ischolar
{
	public MathImpl() throws RemoteException
	{
		// empty
	}

	ArrayList<autor> autores = new ArrayList<autor>();

	public String adicionaAutor(String nome, String instituicao) throws RemoteException
	{
		boolean existe = false;
		String resposta = new String();

		for (int i = 0; i < autores.size(); i++)
		{
			if (nome.equalsIgnoreCase(autores.get(i).getNome())) // n�o � necess�rio verificar a institui��o, pois o
																	// nome � �NICO
			{
				existe = true;
				resposta = "false"; // autor j� existe na colec��o
				break;
			}
		}

		if (!existe)
		{
			autor novo_autor = new autor(nome, instituicao, new ArrayList<String>());
			autores.add(novo_autor); // adiciona o novo autor � colec��o
			resposta = "true";
		}

		return resposta;
	}

	public String adicionaPublicacao(String titulo, String[] nomes) throws RemoteException
	{
		boolean existe = false;
		String resposta = new String();

		// Verificar se a publica��o j� existe
		for (int i = 0; i < autores.size(); i++)
		{
			for (int k = 0; k < autores.get(i).getPublicacoes().size(); k++)
			{
				if (titulo.equalsIgnoreCase(autores.get(i).getPublicacoes().get(k)))
				{
					resposta = "Publica��o j� existe!!!";
					existe = true;
					break;
				}
			}
			if (existe)
				break;
		}

		// Se a publica��o n�o existe, � necess�rio verificar se os autores j� foram adicionados � colec��o
		if (!existe)
		{
			boolean existeAuto = false;
			int[] indices = new int[nomes.length];

			for (int j = 0; j < nomes.length; j++)
			{
				for (int i = 0; i < autores.size(); i++)
				{
					existeAuto = false;

					if (nomes[j].equalsIgnoreCase(autores.get(i).getNome()))
					{
						indices[j] = i;
						existeAuto = true;
						break;
					}

				}

				if (!existeAuto)
				{
					resposta = "Autor n�o pertence � colec��o!!!";
					break;
				}
			}

			if (existeAuto) // Se os autores j� estiverem na colec��o, Adicionar a publica��o
			{
				for (int k = 0; k < indices.length; k++)
				{
					autores.get(indices[k]).getPublicacoes().add(titulo);
				}

				resposta = "true";
			}

		}

		return resposta;
	}

	public String removeAutor(String nome) throws RemoteException
	{
		String resposta = new String();
		boolean existe = false;

		// Verificar se o autor em quest�o possui publicacoes
		for (int i = 0; i < autores.size(); i++)
		{
			if (nome.equalsIgnoreCase(autores.get(i).getNome()))
			{
				existe = true;
				if (autores.get(i).getPublicacoes().isEmpty())
				{
					autores.remove(i);
					resposta = "true";
					break;
				}
				else
				{
					resposta = "N�o � possivel eliminar!";
					break;
				}
			}

		}

		if (!existe)
			resposta = "Autor n�o pertence � colec��o";

		return resposta;
	}

	public String removePublicacao(String titulo_remove) throws RemoteException
	{
		String resposta = new String();
		boolean existe = false;

		for (int i = 0; i < autores.size(); i++)
		{
			for (int k = 0; k < autores.get(i).getPublicacoes().size(); k++)
			{
				if (titulo_remove.equalsIgnoreCase(autores.get(i).getPublicacoes().get(k)))
				{
					autores.get(i).getPublicacoes().remove(k);
					existe = true;
					resposta = "true";
					break;
				}
			}
		}

		if (!existe)
			resposta = "Artigo n�o existe na cole��o!!!";

		return resposta;
	}

	public String listaAutores() throws RemoteException
	{
		String resposta = new String();

		for (int i = 0; i < autores.size(); i++)
		{
			resposta = resposta + "\n Autor: " + autores.get(i).getNome();

			for (int j = 0; j < autores.get(i).getPublicacoes().size(); j++)
			{
				resposta = resposta + "\n" + autores.get(i).getPublicacoes().get(j);
			}
		}

		return resposta;
	}

	public String listaAutoresInstituicao(String instituicao) throws RemoteException
	{
		String resposta = new String();
		resposta = "Autores de " + instituicao;

		for (int i = 0; i < autores.size(); i++)
		{
			if (instituicao.equalsIgnoreCase(autores.get(i).getInstituicao()))
			{
				resposta = resposta + "\n" + autores.get(i).getNome();
			}

		}

		return resposta;
	}

	public String mostraEstatistica() throws RemoteException
	{
		String resposta = new String();
		int total_publicacoes = 0;
		double medio_publicacoes = 0;

		resposta = "N�mero total de autores da cole��o: " + (autores.size()) + "\n";

		for (int i = 0; i < autores.size(); i++)
		{
			total_publicacoes = total_publicacoes + autores.get(i).getPublicacoes().size();
			// se houver uma publicacao com mais que um autor,conta-a n vezes (=numero de autores) mas �
			// apenas para a contagem do numero medio de artigos por autor
		}

		medio_publicacoes = 1.0 * total_publicacoes / autores.size();

		ArrayList<String> pubs = new ArrayList<String>();

		for (int j = 0; j < autores.size(); j++)
		{
			for (int k = 0; k < autores.get(j).getPublicacoes().size(); k++)
			{
				if (!(pubs.contains(autores.get(j).getPublicacoes().get(k))))
				{
					pubs.add(autores.get(j).getPublicacoes().get(k));
				}
			}
		}
		int[] med_auto = new int[pubs.size()];

		for (int k = 0; k < pubs.size(); k++)
		{
			for (int i = 0; i < autores.size(); i++)
			{
				for (int j = 0; j < autores.get(i).getPublicacoes().size(); j++)
				{
					if (pubs.get(k).equals(autores.get(i).getPublicacoes().get(j)))
						med_auto[k]++;
				}
			}
		}

		int med = 0;
		for (int k = 0; k < pubs.size(); k++)
		{
			med = med + med_auto[k];
		}

		resposta = resposta + "Total de publica��es: " + pubs.size() + "\n" + "N�mero m�dio de publica��es por autor: "
				+ medio_publicacoes + "\n" + "N�mero m�dio de autores por publica��o: " + 1.0 * med / pubs.size();

		return resposta;
	}
}
