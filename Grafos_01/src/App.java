// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.PrintWriter;

// public class App {
//     public static void main(String[] args) {
//         // Pasta onde estão os arquivos TXT de entrada
//         String pastaEntrada = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos_01\\";
        
//         // Pasta onde as novas pastas de saída serão criadas (um nível acima)
//         String pastaPai = "C:\\Users\\Tales\\Desktop\\Grafos\\";
        
//         // Definição das pastas de sasída fora da Grafos_01
//         String pastaDot = pastaPai + "mapas_dot\\";
//         String pastaTxt = pastaPai + "resultados_grafo\\";

//         // Criar as pastas caso não existam
//         new File(pastaDot).mkdirs();
//         new File(pastaTxt).mkdirs();

//         // Configuração dos arquivos de entrada
//         String[][] configuracoes = {
//             {pastaEntrada + "roadNet-CA.txt", "CA"},
//             {pastaEntrada + "roadNet-PA.txt", "PA"},
//             {pastaEntrada + "roadNet-TX.txt", "TX"}
//         };

//         for (String[] config : configuracoes) {
//             processarEstado(config[0], config[1], pastaDot, pastaTxt);
//         }
//     }

//     public static void processarEstado(String caminhoEntrada, String sufixo, String pastaDot, String pastaTxt) {
//         // Tamanho ajustado conforme seu código original
//         Grafo grafo = new Grafo(1000001);

//         try {
//             System.out.println("\n--- Processando: " + sufixo + " ---");
//             System.out.println("Lendo de: " + caminhoEntrada);
            
//             BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
//             String linha;
            
//             while ((linha = br.readLine()) != null) {
//                 if (linha.startsWith("#") || linha.trim().isEmpty()) continue;
//                 String[] partes = linha.split("\\s+");
//                 if (partes.length >= 2) {
//                     grafo.addAresta(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
//                 }
//             }
//             br.close();

//             // Caminhos de saída organizados
//             String caminhoArquivoTxt = pastaTxt + "resultado_grafo_" + sufixo + ".txt";
//             String caminhoArquivoDot = pastaDot + "mapa_saida_" + sufixo + ".dot";

//             System.out.println("Salvando TXT em: " + caminhoArquivoTxt);
//             try (PrintWriter writer = new PrintWriter(caminhoArquivoTxt)) {
//                 writer.println(grafo.toString());
//             }

//             System.out.println("Salvando DOT em: " + caminhoArquivoDot);
//             grafo.exportarGraphviz(caminhoArquivoDot);

//             System.out.println("Sucesso para " + sufixo + "!");

//         } catch (Exception e) {
//             System.out.println("Erro ao processar " + sufixo + ": " + e.getMessage());
//         }
//     }
// }

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) {
        // Pastas de saída - serão criadas na raiz de onde o programa rodar
        String pastaDot = "mapas_dot" + File.separator;
        String pastaTxt = "resultados_grafo" + File.separator;

        new File(pastaDot).mkdirs();
        new File(pastaTxt).mkdirs();

        // Nomes dos arquivos .txt conforme sua estrutura
        String[] arquivos = {"roadNet-CA.txt", "roadNet-PA.txt", "roadNet-TX.txt"};
        String[] sufixos = {"CA", "PA", "TX"};

        for (int i = 0; i < arquivos.length; i++) {
            File arquivoFinal = localizarArquivo(arquivos[i]);
            
            if (arquivoFinal != null) {
                processarEstado(arquivoFinal.getAbsolutePath(), sufixos[i], pastaDot, pastaTxt);
            } else {
                System.out.println("ERRO: Não encontrei o arquivo " + arquivos[i]);
                // Esse log ajuda a entender de onde o VS Code está partindo
                System.out.println("O Java está procurando a partir de: " + System.getProperty("user.dir"));
            }
        }
    }

    private static File localizarArquivo(String nome) {
        // Lista de caminhos baseada na sua descrição: "está dentro de grafos01"
        String[] caminhosPossiveis = {
            "Grafos_01" + File.separator + nome, // Se você abrir a pasta pai "Grafos"
            nome,                               // Se você abrir direto a pasta "Grafos_01"
            ".." + File.separator + nome        // Caso o executável esteja numa subpasta profunda
        };

        for (String path : caminhosPossiveis) {
            File f = new File(path);
            if (f.exists()) return f;
        }
        return null;
    }

    public static void processarEstado(String caminhoEntrada, String sufixo, String pastaDot, String pastaTxt) {
        // Tamanho 1000001 mantido conforme solicitado
        Grafo grafo = new Grafo(1000001); 

        try {
            System.out.println("\nLendo arquivo em: " + caminhoEntrada);
            BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("#") || linha.trim().isEmpty()) continue;
                String[] partes = linha.split("\\s+");
                if (partes.length >= 2) {
                    // Adiciona a aresta no grafo
                    grafo.addAresta(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
                }
            }
            br.close();

            // Caminhos de saída
            String txtOut = pastaTxt + "resultado_grafo_" + sufixo + ".txt";
            String dotOut = pastaDot + "mapa_saida_" + sufixo + ".dot";

            try (PrintWriter writer = new PrintWriter(txtOut)) {
                writer.println(grafo.toString());
            }
            
            grafo.exportarGraphviz(dotOut);
            System.out.println("Sucesso: " + sufixo + " finalizado.");

        } catch (Exception e) {
            System.out.println("Erro ao processar " + sufixo + ": " + e.getMessage());
        }
    }
}