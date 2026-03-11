// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.PrintWriter;

// public class App {
//     public static void main(String[] args) {
//         Grafo grafo = new Grafo(1000001);

//         // String caminhoEntradaCA = "roadNet-CA.txt"; //California
//         // String caminhoEntradaPA = "roadNet-PA.txt"; //Pennsylvania
//         // String caminhoEntradaTX = "roadNet-TX.txt"; //Texas
        
//         String caminhoEntradaCA = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-CA.txt";  //California
//         String caminhoEntradaPA = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-PA.txt";  //Pennsylvania
//         String caminhoEntradaTX = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-TX.txt";  //Texas
        
//         String caminhoSaida = "resultado_grafo.txt"; 
//         String caminhoEntrada = caminhoEntradaPA;

//         try 
//         {
//             // Leitura do arquivo original
//             System.out.println("Lendo o arquivo: " + caminhoEntrada + "...");
//             BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
//             String linha;
            
//             while ((linha = br.readLine()) != null) 
//             {
//                 if (linha.startsWith("#") || linha.trim().isEmpty()) continue;

//                 String[] partes = linha.split("\\s+");
//                 if (partes.length >= 2) 
//                 {
//                     int v = Integer.parseInt(partes[0]);
//                     int w = Integer.parseInt(partes[1]);
//                     grafo.addAresta(v, w);
//                 }
//             }
//             br.close(); 

//             System.out.println("Gerando arquivo de saída: " + caminhoSaida);
//             try (PrintWriter writer = new PrintWriter(caminhoSaida)) 
//             {
//                 writer.println(grafo.toString());
//                 grafo.exportarGraphviz("mapa_saida.dot");
//             }
            
//             System.out.println("Arquivo gravado com sucesso!");

//         } 
        
//         catch (Exception e) 
//         {
//             System.out.println("Erro durante o processamento: " + e.getMessage());
//         }
//     }
// }



// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.PrintWriter;

// public class App {
//     public static void main(String[] args) {
//         // Caminhos baseados na sua estrutura de pastas
//         String pastaBase = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos_01\\";
        
//         // Mapeamento: {Arquivo de Entrada, Sufixo do Nome}
//         String[][] configuracoes = {
//             {pastaBase + "roadNet-CA.txt", "CA"},
//             {pastaBase + "roadNet-PA.txt", "PA"},
//             {pastaBase + "roadNet-TX.txt", "TX"}
//         };

//         for (String[] config : configuracoes) {
//             String caminhoEntrada = config[0];
//             String sufixo = config[1];
            
//             processarEstado(caminhoEntrada, sufixo);
//         }
//     }

//     public static void processarEstado(String caminhoEntrada, String sufixo) {
//         // Instancia um novo grafo (1000001 vértices) para limpar a memória do anterior
//         Grafo grafo = new Grafo(1000001);

//         try {
//             System.out.println("\n--- Iniciando: " + sufixo + " ---");
//             System.out.println("Lendo: " + caminhoEntrada);
            
//             BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
//             String linha;
            
//             while ((linha = br.readLine()) != null) {
//                 if (linha.startsWith("#") || linha.trim().isEmpty()) continue;

//                 String[] partes = linha.split("\\s+");
//                 if (partes.length >= 2) {
//                     int v = Integer.parseInt(partes[0]);
//                     int w = Integer.parseInt(partes[1]);
//                     grafo.addAresta(v, w);
//                 }
//             }
//             br.close();

//             // Nomes de saída conforme sua solicitação
//             String nomeArquivoDot = "mapa_saida_" + sufixo + ".dot";
//             String nomeArquivoTxt = "resultado_grafo_" + sufixo + ".txt";

//             System.out.println("Salvando lista em: " + nomeArquivoTxt);
//             try (PrintWriter writer = new PrintWriter(nomeArquivoTxt)) {
//                 writer.println(grafo.toString());
//             }

//             System.out.println("Gerando visualização em: " + nomeArquivoDot);
//             grafo.exportarGraphviz(nomeArquivoDot);

//             System.out.println("Finalizado: " + sufixo);

//         } catch (Exception e) {
//             System.out.println("Erro ao processar " + sufixo + ": " + e.getMessage());
//         }
//     }
// }




// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.PrintWriter;

// public class App {
//     public static void main(String[] args) {
//         String pastaBase = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos_01\\";
        
//         // Definição das pastas de saída
//         String pastaDot = pastaBase + "mapas_dot\\";
//         String pastaTxt = pastaBase + "resultados_grafo\\";

//         // Criar as pastas caso não existam
//         new File(pastaDot).mkdirs();
//         new File(pastaTxt).mkdirs();

//         String[][] configuracoes = {
//             {pastaBase + "roadNet-CA.txt", "CA"},
//             {pastaBase + "roadNet-PA.txt", "PA"},
//             {pastaBase + "roadNet-TX.txt", "TX"}
//         };

//         for (String[] config : configuracoes) {
//             processarEstado(config[0], config[1], pastaDot, pastaTxt);
//         }
//     }

//     public static void processarEstado(String caminhoEntrada, String sufixo, String pastaDot, String pastaTxt) {
//         Grafo grafo = new Grafo(1000001);

//         try {
//             System.out.println("\n--- Processando: " + sufixo + " ---");
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

//             // Caminhos completos para os novos diretórios
//             String caminhoArquivoTxt = pastaTxt + "resultado_grafo_" + sufixo + ".txt";
//             String caminhoArquivoDot = pastaDot + "mapa_saida_" + sufixo + ".dot";

//             System.out.println("Salvando TXT em: " + caminhoArquivoTxt);
//             try (PrintWriter writer = new PrintWriter(caminhoArquivoTxt)) {
//                 writer.println(grafo.toString());
//             }

//             System.out.println("Salvando DOT em: " + caminhoArquivoDot);
//             grafo.exportarGraphviz(caminhoArquivoDot);

//             System.out.println("Finalizado: " + sufixo);

//         } catch (Exception e) {
//             System.out.println("Erro em " + sufixo + ": " + e.getMessage());
//         }
//     }
// }





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
        
        // Definição das pastas de saída fora da Grafos_01
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