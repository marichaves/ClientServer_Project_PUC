package ClientServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Servidor {

    // Lista para armazenar os nomes dos arquivos no servidor
    private static List<String> fileListServer = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Cria um servidor que aguarda conexões na porta 12345
            ServerSocket srvSocket = new ServerSocket(12345);
            System.out.println("Aguardando conexão do cliente...\n");
            System.out.println("----------------------------------------------------------------------");

            boolean bool = true;
            while (bool) {
                // Espera e cria uma conexão quando um cliente se conecta ao servidor
                Socket conexao = srvSocket.accept();

                // Cria um fluxo de entrada na conexão para receber dados do cliente
                InputStream entrada = conexao.getInputStream();

                // Cria um fluxo de entrada de dados do tipo "DataInputStream" para ler dados primitivos
                DataInputStream entradaDados = new DataInputStream(entrada);

                // Lê a opção enviada pelo cliente para indicar a ação desejada
                String option = entradaDados.readUTF();

                if (option.equals("1")) {
                    // Se a opção for "1", o cliente deseja enviar um arquivo ao servidor
                    recebeArquivoDoCliente(entradaDados);
                } else if (option.equals("2")) {
                    // Se a opção for "2", o cliente deseja listar os arquivos disponíveis no servidor
                    listarArquivosNoServidor();
                } else if (option.equals("3")) {
                    // Se a opção for "3", o cliente deseja baixar um arquivo do servidor
                    String fileName = entradaDados.readUTF();
                    baixarArquivo(fileName, conexao);
                } else if(option.equals("4")) {
                    // Se a opção for "4", o cliente deseja encerrar a conexão com o servidor
                    bool = false;
                    conexao.close();
                    System.out.println("Conexão com o cliente finalizada!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para receber um arquivo enviado pelo cliente
    public static void recebeArquivoDoCliente(DataInputStream entradaDados) {
        try {
            // Lê o nome e o tamanho do arquivo que o cliente deseja enviar
            String fileName = entradaDados.readUTF();
            int fileSize = entradaDados.readInt();

            // Cria um fluxo de saída para gravar o arquivo no servidor
            FileOutputStream fos = new FileOutputStream("ServidorArquivos\\" + fileName);

            byte[] conteudoArquivoEmBytes = new byte[(int)fileSize];
            int bytesLidos;

            // Lê os bytes do arquivo do fluxo de entrada e os escreve no arquivo no servidor
            while((bytesLidos = entradaDados.read(conteudoArquivoEmBytes)) != -1) {
                fos.write(conteudoArquivoEmBytes, 0, bytesLidos);
            }

            fos.close();
            fileListServer.add(fileName);
            System.out.println("Arquivo " + fileName + " gravado com sucesso!");
            System.out.println("----------------------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para listar os arquivos disponíveis no servidor
    public static void listarArquivosNoServidor() {
        System.out.println("LISTA DE ARQUIVOS NO SERVIDOR");
        for(String fileName: fileListServer) {
            System.out.println(fileName);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    // Função para enviar um arquivo do servidor para o cliente
    public static void baixarArquivo(String fileName, Socket conexao) {
        try {
            // Cria um fluxo de saída para enviar o arquivo para o cliente
            OutputStream saida = conexao.getOutputStream();
            DataOutputStream saidaDados = new DataOutputStream(saida);

            if(fileListServer.contains(fileName)) {
                // Se o arquivo existir no servidor, lê seu conteúdo e envia para o cliente
                File arquivo = new File("ServidorArquivos\\" + fileName);
                byte[] arquivoEmBytes = new byte[(int)arquivo.length()];
                FileInputStream fis = new FileInputStream(arquivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(arquivoEmBytes, 0, arquivoEmBytes.length);

                saidaDados.writeInt(arquivoEmBytes.length);
                saidaDados.write(arquivoEmBytes, 0, arquivoEmBytes.length);

                saidaDados.close();
                System.out.println("Arquivo " + fileName + " enviado para o cliente.");
            } else {
                // Se o arquivo não for encontrado no servidor, informa o cliente
                System.out.println("Arquivo não encontrado no servidor!");
            }
            System.out.println("----------------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}