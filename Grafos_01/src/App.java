import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) {
        // Pasta onde estão os arquivos TXT de entrada
        String pastaEntrada = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos_01\\";
        
        // Pasta onde as novas pastas de saída serão criadas (um nível acima)
        String pastaPai = "C:\\Users\\Tales\\Desktop\\Grafos\\";
        
        // Definição das pastas de sasída fora da Grafos_01
        String pastaDot = pastaPai + "mapas_dot\\";
        String pastaTxt = pastaPai + "resultados_grafo\\";

        // Criar as pastas caso não existam
        new File(pastaDot).mkdirs();
        new File(pastaTxt).mkdirs();

        // Configuração dos arquivos de entrada
        String[][] configuracoes = {
            {pastaEntrada + "roadNet-CA.txt", "CA"},
            {pastaEntrada + "roadNet-PA.txt", "PA"},
            {pastaEntrada + "roadNet-TX.txt", "TX"}
        };

        for (String[] config : configuracoes) {
            processarEstado(config[0], config[1], pastaDot, pastaTxt);
        }
    }

    public static void processarEstado(String caminhoEntrada, String sufixo, String pastaDot, String pastaTxt) {
        // Tamanho ajustado conforme seu código original
        Grafo grafo = new Grafo(1000001);

        try {
            System.out.println("\n--- Processando: " + sufixo + " ---");
            System.out.println("Lendo de: " + caminhoEntrada);
            
            BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
            String linha;
            
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("#") || linha.trim().isEmpty()) continue;
                String[] partes = linha.split("\\s+");
                if (partes.length >= 2) {
                    grafo.addAresta(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
                }
            }
            br.close();

            // Caminhos de saída organizados
            String caminhoArquivoTxt = pastaTxt + "resultado_grafo_" + sufixo + ".txt";
            String caminhoArquivoDot = pastaDot + "mapa_saida_" + sufixo + ".dot";

            System.out.println("Salvando TXT em: " + caminhoArquivoTxt);
            try (PrintWriter writer = new PrintWriter(caminhoArquivoTxt)) {
                writer.println(grafo.toString());
            }

            System.out.println("Salvando DOT em: " + caminhoArquivoDot);
            grafo.exportarGraphviz(caminhoArquivoDot);

            System.out.println("Sucesso para " + sufixo + "!");

        } catch (Exception e) {
            System.out.println("Erro ao processar " + sufixo + ": " + e.getMessage());
        }
    }
}