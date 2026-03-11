public class Grafo 
{
    private final int V;                // Número de vértices
    private int E;                      // Contador de arestas
    private Bolsa<Integer>[] adj;       // Array de bolsas
    
    public Grafo(int V)
    {
        this.V = V;
        this.E = 0;

        adj = (Bolsa<Integer>[]) new Bolsa[V];

        for (int i = 0; i < V;i++)
        {
            // Instancia cada Bolsa individualmente
            adj[i] = new Bolsa<Integer>();
        }
    }

        public void addAresta (int v, int e)
        {
            if(v <= 1000000 && e <= 1000000)
            {
                adj[v].add(e);
                E++;
            }

        }

        public Iterable<Integer> adj(int v) 
        {
            return adj[v];
        }

        @Override
        public String toString() 
        {
            StringBuilder s = new StringBuilder();
            s.append(V + " vertices, " + E + " edges\n");
            for (int v = 0; v < V; v++) 
            {
                s.append(v + ": ");
                for (int w : adj[v]) 
                {
                    s.append(w + " ");
                }
                s.append("\n");
            }
            return s.toString();
        }


        //Exportar arquivo para .dot
        public void exportarGraphviz(String nomeArquivo) 
        {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(nomeArquivo)) 
            {
                writer.println("digraph G {");
                writer.println("  rankdir=LR; // Direção da esquerda para a direita");
                writer.println("  node [shape=circle, fontname=\"Arial\"];");

                for (int v = 0; v < 1000000; v++) 
                {
                    for (int w : adj[v]) 
                    {
                        writer.println("  " + v + " -> " + w + ";");
                    }
                }
                writer.println("}");
                System.out.println("Arquivo .dot gerado com sucesso!");
            } 

            catch (java.io.IOException e) 
            {
                System.out.println("Erro ao exportar: " + e.getMessage());
            }
        }
}
        
