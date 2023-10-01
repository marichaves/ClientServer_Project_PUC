package ClientServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        // Preparando os arquivos que queremos enviar ao servidor
        File arquivo = new File("EnvioDeArq\\teste.txt");
        enviarArquivoAoServidor(arquivo);

        arquivo = new File("EnvioDeArq\\japanese-1696201250560-6478.jpg");
        enviarArquivoAoServidor(arquivo);

        arquivo = new File("EnvioDeArq\\cat-png-40354.png");
        enviarArquivoAoServidor(arquivo);

        arquivo = new File("EnvioDeArq\\Tabela-completa-5-algarismos-v11.pdf");
        enviarArquivoAoServidor(arquivo);

        // Listando arquivos disponíveis no servidor
        listarArquivosDoServidor();

        // Baixando arquivos do servidor
        baixarArquivoDoServidor("teste.txt");
        baixarArquivoDoServidor("japanese-1696201250560-6478.jpg");
        baixarArquivoDoServidor("cat-png-40354.png");
        baixarArquivoDoServidor("Tabela-completa-5-algarismos-v11.pdf");

        // Finalizando a comunicação com o servidor
        fecharServidor();
    }

    // Função para enviar um arquivo ao servidor
    public static void enviarArquivoAoServidor(File arquivo) {
        try {
            // Estabelecendo conexão com o servidor
            Socket conexao = new Socket("localhost", 12345);

            // Criando fluxos para leitura do arquivo
            FileInputStream fis = new FileInputStream(arquivo);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // Lendo o arquivo e armazenando em um array de bytes
            byte[] arquivoEmBytes = new byte[(int) arquivo.length()];
            bis.read(arquivoEmBytes, 0, arquivoEmBytes.length);
            fis.close();

            // Obtendo fluxo de saída para enviar dados ao servidor
            OutputStream saida = conexao.getOutputStream();
            DataOutputStream saidaDados = new DataOutputStream(saida);

            // Enviando informações sobre o arquivo (nome e tamanho)
            saidaDados.writeUTF("1"); // Enviar arquivo (opção 1)
            saidaDados.writeUTF(arquivo.getName()); // Nome do arquivo
            saidaDados.writeInt(arquivoEmBytes.length); // Tamanho em bytes do arquivo

            // Enviando o conteúdo do arquivo
            saidaDados.write(arquivoEmBytes, 0, arquivoEmBytes.length);

            // Fechando a conexão
            conexao.close();
            System.out.println("Arquivo " + arquivo.getName() + " enviado com sucesso");

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para listar arquivos disponíveis no servidor
    public static void listarArquivosDoServidor() {
        try {
            // Estabelecendo conexão com o servidor
            Socket conexao = new Socket("localhost", 12345);

            // Obtendo fluxo de saída para enviar solicitação ao servidor
            OutputStream saida = conexao.getOutputStream();
            DataOutputStream saidaDados = new DataOutputStream(saida);

            // Enviando a solicitação para listar arquivos (opção 2)
            saidaDados.writeUTF("2");

            // Fechando a conexão
            conexao.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para baixar um arquivo do servidor
    public static void baixarArquivoDoServidor(String fileName) {
        try {
            // Estabelecendo conexão com o servidor
            Socket conexao = new Socket("localhost", 12345);

            // Obtendo fluxo de saída para enviar solicitação ao servidor
            OutputStream saida = conexao.getOutputStream();
            DataOutputStream saidaDados = new DataOutputStream(saida);

            // Enviando a solicitação para baixar arquivo (opção 3) e especificando o nome do arquivo
            saidaDados.writeUTF("3");
            saidaDados.writeUTF(fileName);

            // Obtendo fluxo de entrada para receber o arquivo do servidor
            InputStream entrada = conexao.getInputStream();
            DataInputStream entradaDados = new DataInputStream(entrada);

            // Recebendo informações sobre o arquivo (tamanho)
            int fileSize = entradaDados.readInt();

            // Criando arquivo local para armazenar o arquivo recebido
            FileOutputStream fos = new FileOutputStream("Baixados\\" + fileName);

            // Lendo o conteúdo do arquivo e escrevendo no arquivo local
            byte[] conteudoArquivoEmBytes = new byte[fileSize];
            int bytesLidos;
            while ((bytesLidos = entradaDados.read(conteudoArquivoEmBytes)) != -1) {
                fos.write(conteudoArquivoEmBytes, 0, bytesLidos);
            }
            fos.close();

            // Fechando a conexão
            conexao.close();

            System.out.println("Arquivo " + fileName + " baixado com sucesso!");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Função para fechar a comunicação com o servidor
    public static void fecharServidor() {
        try {
            // Estabelecendo conexão com o servidor
            Socket conexao = new Socket("localhost", 12345);

            // Obtendo fluxo de saída para enviar solicitação ao servidor (opção 4 para fechar)
            OutputStream saida = conexao.getOutputStream();
            DataOutputStream saidaDados = new DataOutputStream(saida);

            saidaDados.writeUTF("4"); // Solicitação para fechar o servidor

            // Fechando a conexão
            conexao.close();
            System.out.println("Conexão com o servidor finalizada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}