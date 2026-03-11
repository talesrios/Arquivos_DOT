# Projeto de Processamento de Malhas Rodoviárias (Grafos)

Este projeto foi desenvolvido para a disciplina de Teoria dos Grafos. O objetivo é processar grandes conjuntos de dados (datasets) de redes rodoviárias dos estados da Califórnia (CA), Pensilvânia (PA) e Texas (TX), utilizando uma implementação eficiente de listas de adjacência em Java.

## 📂 Estrutura do Repositório

O repositório está organizado para separar o código-fonte dos arquivos de saída (gerados automaticamente):

* **`Grafos_01/`**: Contém os arquivos de código-fonte (`.java`) e deve abrigar os arquivos de entrada (`roadNet-CA.txt`, etc.).
* **`mapas_dot/`**: Pasta que armazena os arquivos gerados no formato `.dot` para visualização via Graphviz.
* **`resultados_grafo/`**: Pasta que armazena as listas de adjacência processadas em formato `.txt`.
* **`PDF_MAPA/`**: Contém as visualizações finais dos grafos exportadas em PDF.

## 💻 Estruturas de Dados Implementadas

Para lidar com o grande volume de dados (aprox. 1 milhão de vértices por estado), foram implementadas estruturas clássicas:

1.  **Bolsa (Bag)**: Uma lista encadeada genérica que permite inserção $O(1)$ e iteração eficiente.
2.  **Grafo**: Representado por uma Lista de Adjacência, utilizando um array de objetos `Bolsa`.
3.  **Exportador DOT**: Método que traduz a estrutura em memória para a linguagem descritiva Graphviz.


## 🧪 Notebook de Análise: `power_law.ipynb`
Após a geração dos arquivos pelo Java, os dados são processados em um ambiente Google Colab para análise estatística e visualização. O script realiza o download automático dos artefatos armazenados no presente repositótio do git: https://github.com/talesrios/Arquivos_DOT.git .

O projeto inclui o notebook **`power_law.ipynb`** (localizado na pasta `Grafos_01/`), que automatiza a análise estatística e visualização dos dados processados pelo Java. As principais funcionalidades implementadas no Google Colab são:

### 1. Visualização de Grafos (Amostragem de 10k nós)
Devido à densidade das malhas rodoviárias, o notebook renderiza e exibe os arquivos PDF gerados para subconjuntos de **10.000 nós**. Isso permite a inspeção visual da topologia local sem comprometer o desempenho do ambiente.
* **Arquivos consultados:** `mapa_10k_CA.pdf`, `mapa_10k_PA.pdf` e `mapa_10k_TX.pdf`.

### 2. Histograma de Distribuição de Graus
Geração de histograma de frequência para identificar a quantidade exata de nós que possuem determinado grau (número de conexões). 
* **Análise:** Permite observar a predominância de nós com baixo grau, característica típica de redes de infraestrutura física.

### 3. Análise de Power Law (Log-Log Plot)
Implementação de gráficos em escala logarítmica (**Log-Log**) para verificar a aderência dos dados à Lei de Potência.
* **Objetivo:** Determinar o coeficiente $\gamma$ da distribuição e comparar o comportamento das malhas rodoviárias reais com modelos teóricos de redes de "mundo pequeno" ou redes aleatórias.
