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
			if (nome.equalsIgnoreCase(autores.get(i).getNome())) // não é necessário verificar a instituição, pois o
																	// nome é ÚNICO
			{
				existe = true;
				resposta = "false"; // autor já existe na colecção
				break;
			}
		}

		if (!existe)
		{
			autor novo_autor = new autor(nome, instituicao, new ArrayList<String>());
			autores.add(novo_autor); // adiciona o novo autor à colecção
			resposta = "true";
		}

		return resposta;
	}

	public String adicionaPublicacao(String titulo, String[] nomes) throws RemoteException
	{
		boolean existe = false;
		String resposta = new String();

		// Verificar se a publicação já existe
		for (int i = 0; i < autores.size(); i++)
		{
			for (int k = 0; k < autores.get(i).getPublicacoes().size(); k++)
			{
				if (titulo.equalsIgnoreCase(autores.get(i).getPublicacoes().get(k)))
				{
					resposta = "Publicação já existe!!!";
					existe = true;
					break;
				}
			}
			if (existe)
				break;
		}

		// Se a publicação não existe, é necessário verificar se os autores já foram adicionados à colecção
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
					resposta = "Autor não pertence à colecção!!!";
					break;
				}
			}

			if (existeAuto) // Se os autores já estiverem na colecção, Adicionar a publicação
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

		// Verificar se o autor em questão possui publicacoes
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
					resposta = "Não é possivel eliminar!";
					break;
				}
			}

		}

		if (!existe)
			resposta = "Autor não pertence à colecção";

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
			resposta = "Artigo não existe na coleção!!!";

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

		resposta = "Número total de autores da coleção: " + (autores.size()) + "\n";

		for (int i = 0; i < autores.size(); i++)
		{
			total_publicacoes = total_publicacoes + autores.get(i).getPublicacoes().size();
			// se houver uma publicacao com mais que um autor,conta-a n vezes (=numero de autores) mas é
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

		resposta = resposta + "Total de publicações: " + pubs.size() + "\n" + "Número médio de publicações por autor: "
				+ medio_publicacoes + "\n" + "Número médio de autores por publicação: " + 1.0 * med / pubs.size();

		return resposta;
	}
}
