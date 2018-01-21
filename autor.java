import java.io.Serializable;
import java.util.ArrayList;

public class autor implements Serializable
{
	private String nome;
	private String instituicao;
	ArrayList<String> publicacoes= new ArrayList<String>();
	
	public autor(String nome, String instituicao, ArrayList<String> publicacoes)
	{
		this.nome = nome;
		this.instituicao = instituicao;
		this.publicacoes = publicacoes;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getInstituicao()
	{
		return instituicao;
	}

	public void setInstituicao(String instituicao)
	{
		this.instituicao = instituicao;
	}

	public ArrayList<String> getPublicacoes()
	{
		return publicacoes;
	}

	public void setPublicacoes(ArrayList<String> publicacoes)
	{
		this.publicacoes = publicacoes;
	}
	
	
}
