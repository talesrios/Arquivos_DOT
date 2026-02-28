import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(1000001);

        // String caminhoEntradaCA = "roadNet-CA.txt"; //California
        // String caminhoEntradaPA = "roadNet-PA.txt"; //Pennsylvania
        // String caminhoEntradaTX = "roadNet-TX.txt"; //Texas
        
        String caminhoEntradaCA = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-CA.txt";  //California
        String caminhoEntradaPA = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-PA.txt";  //Pennsylvania
        String caminhoEntradaTX = "C:\\Users\\Tales\\Desktop\\Grafos\\Grafos\\Grafos_01\\roadNet-TX.txt";  //Texas
        
        String caminhoSaida = "resultado_grafo.txt"; 
        String caminhoEntrada = caminhoEntradaPA;

        try 
        {
            // Leitura do arquivo original
            System.out.println("Lendo o arquivo: " + caminhoEntrada + "...");
            BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
            String linha;
            
            while ((linha = br.readLine()) != null) 
            {
                if (linha.startsWith("#") || linha.trim().isEmpty()) continue;

                String[] partes = linha.split("\\s+");
                if (partes.length >= 2) 
                {
                    int v = Integer.parseInt(partes[0]);
                    int w = Integer.parseInt(partes[1]);
                    grafo.addAresta(v, w);
                }
            }
            br.close(); 

            System.out.println("Gerando arquivo de saída: " + caminhoSaida);
            try (PrintWriter writer = new PrintWriter(caminhoSaida)) 
            {
                writer.println(grafo.toString());
                grafo.exportarGraphviz("mapa_saida.dot");
            }
            
            System.out.println("Arquivo gravado com sucesso!");

        } 
        
        catch (Exception e) 
        {
            System.out.println("Erro durante o processamento: " + e.getMessage());
        }
    }
}