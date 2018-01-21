import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Ischolar extends Remote
{
	public String adicionaAutor(String autor, String instituicao) throws RemoteException;

	public String adicionaPublicacao(String publicacao, String[] nomes) throws RemoteException;

	public String removeAutor(String nome) throws RemoteException;

	public String removePublicacao(String titulo_remove) throws RemoteException;

	public String listaAutores() throws RemoteException;

	public String listaAutoresInstituicao(String instituicao) throws RemoteException;

	public String mostraEstatistica() throws RemoteException;

}
