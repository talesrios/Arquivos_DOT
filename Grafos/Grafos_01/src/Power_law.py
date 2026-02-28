import matplotlib.pyplot as plt
import numpy as np
from collections import Counter
import powerlaw
import re
import sys

# ================================
# Caminho do arquivo .dot
# ================================
DOT_PATH = r"C:\Users\Tales\Desktop\Grafos\mapa_saida.dot"

with open(DOT_PATH, 'r', encoding='utf-8') as file:
    raw_lines = [l.strip() for l in file if l.strip()]

# ================================
# Parser robusto de arestas (.dot)
# - aceita: a -- b;   a -> b;
# - tolera atributos: a -- b [label=...];
# - tolera nós com aspas: "nó 1" -- "nó 2";
# ================================
EDGE_RE = re.compile(r'^\s*(".*?"|\S+)\s*(--|->)\s*(".*?"|\S+)\s*(?:\[(.*?)\])?\s*;?\s*$')

def parse_edge(line: str):
    # Ignore blocos/linhas estruturais comuns do DOT
    if line.startswith(("graph", "digraph", "{", "}", "node ", "edge ")):
        return None

    m = EDGE_RE.match(line)
    if not m:
        return None

    a, _, b, _attrs = m.groups()

    # remove aspas externas, se houver
    if a.startswith('"') and a.endswith('"'):
        a = a[1:-1]
    if b.startswith('"') and b.endswith('"'):
        b = b[1:-1]

    return a, b

# ================================
# Contar arestas (via parse_edge)
# ================================
parsed_edges_preview = []
num_arestas = 0
for l in raw_lines:
    e = parse_edge(l)
    if e:
        num_arestas += 1
        if len(parsed_edges_preview) < 5:
            parsed_edges_preview.append((l, e))

print("Tamanho do grafo (arestas):", num_arestas)
if num_arestas == 0:
    print("Não encontrei nenhuma aresta no .dot. Exemplos de linhas lidas (primeiras 20):")
    for x in raw_lines[:20]:
        print("  ", x)
    sys.exit(1)

# ================================
# Mapear nós
# ================================
node_index = {}
next_idx = 0
edges = []

for l in raw_lines:
    parsed = parse_edge(l)
    if not parsed:
        continue
    a, b = parsed

    if a not in node_index:
        node_index[a] = next_idx
        next_idx += 1

    if b not in node_index:
        node_index[b] = next_idx
        next_idx += 1

    ia = node_index[a]
    ib = node_index[b]
    edges.append((ia, ib))

num_nodes = next_idx
print("Número de nós:", num_nodes)

if not edges or num_nodes == 0:
    print("Falha ao construir o grafo: nenhuma aresta/nó foi mapeado.")
    print("Amostra de arestas parseadas:", parsed_edges_preview)
    sys.exit(1)

# ================================
# Calcular graus
# ================================
degrees = np.zeros(num_nodes, dtype=int)

for ia, ib in edges:
    degrees[ia] += 1
    degrees[ib] += 1

degrees = degrees[degrees > 0]  # remover zeros

if degrees.size == 0:
    print("Todos os graus ficaram zero após o processamento; não é possível ajustar power law.")
    sys.exit(1)

# ================================
# Ajuste Power Law
# ================================
fit = powerlaw.Fit(degrees, discrete=True)

alpha = fit.power_law.alpha
xmin = fit.power_law.xmin

print(f"Alpha (α): {alpha}")
print(f"xmin: {xmin}")

# ================================
# Histograma manual (sem cortar em xmin)
# ================================
degree_count = Counter(degrees)

graus_all = np.array(sorted(degree_count.keys()))
freq_all = np.array([degree_count[g] for g in graus_all])
pk_all = freq_all / freq_all.sum()

# Cauda (k >= xmin) só para destacar/ajuste
mask_tail = graus_all >= xmin
graus_tail = graus_all[mask_tail]
pk_tail = pk_all[mask_tail]

# ================================
# Log-binning (para ter mais “pontos” no log-log)
# ================================
def log_binned_pdf(samples: np.ndarray, bins: int = 25):
    samples = np.asarray(samples)
    samples = samples[samples > 0]
    if samples.size == 0:
        return np.array([]), np.array([])

    lo = samples.min()
    hi = samples.max()
    if lo == hi:
        return np.array([lo], dtype=float), np.array([1.0], dtype=float)

    edges = np.logspace(np.log10(lo), np.log10(hi), bins + 1)
    counts, _ = np.histogram(samples, bins=edges)
    centers = np.sqrt(edges[:-1] * edges[1:])  # centro geométrico
    pk = counts / counts.sum()

    m = counts > 0
    return centers[m], pk[m]

k_bin, pk_bin = log_binned_pdf(degrees, bins=30)

# ================================
# Curva teórica (no espaço original; plota em log-log)
# ================================
k_vals = np.logspace(np.log10(max(xmin, 1)), np.log10(max(graus_all)), 300)
C = (alpha - 1) * xmin ** (alpha - 1)  # aproximação contínua (ok para visualização)
p_vals = C * (k_vals ** (-alpha))

# ==========================================
# Geração do Histograma de Barras 
# ==========================================
# plt.figure(figsize=(8, 6)) # Mantém uma boa proporção

# # Calculamos os limites das barras (bins) para que o número fique no centro
# # Isso faz com que o grau 1, 2, 3 etc. fiquem no meio da barra
# bins = np.arange(degrees.min(), degrees.max() + 2) - 0.5

# plt.hist(
#     degrees, 
#     bins=bins, 
#     color='#1f77b4',     # Azul padrão da imagem
#     edgecolor='black',    # Contorno preto definido
#     linewidth=1.2,
#     alpha=1.0            # Cores sólidas
# )

# # Títulos e legendas
# plt.title('Histograma do Grau dos Nós', fontsize=14)
# plt.xlabel('Grau dos Nós', fontsize=12)
# plt.ylabel('Frequência', fontsize=12)

# # Configuração dos números no eixo X
# plt.xticks(range(int(degrees.min()), int(degrees.max()) + 1))

# # Adiciona apenas a grade horizontal (cinza claro) como na foto
# plt.grid(axis='y', linestyle='-', alpha=0.7)

# # Garante que as barras fiquem por cima da grade
# plt.gca().set_axisbelow(True)

# plt.tight_layout()

# # Salva o arquivo com o novo formato
# plt.savefig("histograma_grau_barras.png", dpi=200)
# print("Histograma de barras salvo como: histograma_grau_barras.png")
# plt.show()

# ==========================================
# Geração do Histograma de Barras (Contagem Absoluta)
# ==========================================
from matplotlib.ticker import ScalarFormatter

plt.figure(figsize=(10, 6))

# 1. Contagem exata de cada grau usando o Counter que já calculamos
# unique_degrees = graus_all, freq_all = frequências (número de nós)
# Note: graus_all e freq_all foram definidos antes no seu código
unique_degrees = graus_all
frequencias = freq_all

# 2. Criar o gráfico de barras
plt.bar(
    unique_degrees, 
    frequencias, 
    color='#1f77b4', 
    edgecolor='black', 
    linewidth=0.8,
    alpha=1.0,
    width=0.8  # Ajuste a largura das barras conforme necessário
)

# 3. Formatação do Eixo Y (Crucial para mostrar "50000")
plt.ylabel('Número de Nós (Contagem)', fontsize=12)
plt.gca().yaxis.set_major_formatter(ScalarFormatter())
plt.ticklabel_format(style='plain', axis='y') # Remove o 1e4, 1e5, etc.

# 4. Títulos e Eixo X
plt.title('Distribuição de Frequência dos Graus', fontsize=14)
plt.xlabel('Grau (k)', fontsize=12)

# Ajuste dinâmico do Eixo X para evitar sobreposição de números
if len(unique_degrees) > 30:
    # Se houver muitos graus diferentes, mostra apenas alguns ticks
    plt.xticks(np.linspace(min(unique_degrees), max(unique_degrees), 15, dtype=int))
else:
    plt.xticks(unique_degrees)

# 5. Grade e Estética
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.gca().set_axisbelow(True)

plt.tight_layout()
plt.savefig("histograma_grau_barras_contagem.png", dpi=200)
print(f"Histograma salvo. Maior contagem detectada: {max(frequencias)} nós.")
# ================================
# Plot (use loglog diretamente)
# ================================
plt.figure(figsize=(8, 5))

# distribuição completa (um ponto por grau distinto)
plt.loglog(graus_all, pk_all, "o", markersize=4, alpha=0.5, label="P(k) (todos os k)")

# cauda (k >= xmin)
plt.loglog(graus_tail, pk_tail, "o", markersize=5, alpha=0.9, label=f"Cauda (k ≥ xmin={xmin})")

# log-binned (mais “pontos” visíveis)
if k_bin.size:
    plt.loglog(k_bin, pk_bin, "-", linewidth=2, label="P(k) (log-binned)")

# curva ajustada
plt.loglog(k_vals, p_vals, "--", label=f"Ajuste ~ k^-α (α={alpha:.2f})")

plt.xlabel("k")
plt.ylabel("P(k)")
plt.title("Distribuição de Grau (log-log)")
plt.legend()
plt.grid(True, which="both", ls="--", alpha=0.4)

plt.tight_layout()
plt.savefig("distribuicao_grau_powerlaw_loglog.png", dpi=200)
alpha = fit.alpha
xmin = fit.xmin

R, p = fit.distribution_compare('power_law', 'lognormal')

print("alpha:", alpha)
print("xmin:", xmin)
print("log-likelihood ratio:", R)
print("p-value:", p)
if p <0.1:
    print("A distribuição power law não é possível")
else:    print("A distribuição power law é possível")

plt.show()

